package com.example.hxds.workflow.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.workflow.db.dao.OrderCommentDao;
import com.example.hxds.workflow.service.OrderCommentService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private OrderCommentDao orderCommentDao;

    @Override
    @Transactional
    @LcnTransaction
    public void startCommentWorkflow(Map args) {
        //查询该评价是否开启了工作流实例
        long orderId = MapUtil.getLong(args, "orderId");
        HashMap param = new HashMap() {{
            put("orderId", orderId);
        }};
        HashMap map = orderCommentDao.searchCommentIdAndInstanceId(param);
        if (map == null || map.isEmpty()) {
            throw new HxdsException("没有找到评价记录");
        }
        String temp = MapUtil.getStr(map, "instanceId");
        long commentId = MapUtil.getLong(map, "id");

        if (temp != null) {
            throw new HxdsException("该订单评价已经存在工作流实例");
        }
        args.put("commentId",commentId+"");
        //启动工作流
        String instanceId = runtimeService.startProcessInstanceByKey("comment", args).getProcessInstanceId();

        param = new HashMap() {{
            put("commentId", commentId);
            put("instanceId", instanceId);
        }};
        //更新数据表的instance_id字段
        int rows = orderCommentDao.startWorkflow(param);
        if (rows != 1) {
            throw new HxdsException("更新订单评价instance_id字段失败");
        }
    }

    @Override
    @Transactional
    @LcnTransaction
    public void acceptCommentAppeal(Map param) {

        //查找工作流ID
        HashMap map = orderCommentDao.searchCommentIdAndInstanceId(param);
        if (map == null || map.isEmpty()) {
            throw new HxdsException("没有找到评价记录");
        }
        String instanceId = MapUtil.getStr(map, "instanceId");
        if (instanceId == null) {
            throw new HxdsException("没有找到工作流实例");
        }
        Task task = taskService.createTaskQuery().processInstanceId(instanceId)
                .includeProcessVariables().includeTaskLocalVariables().taskName("人工审核").singleResult();

        String userId = MapUtil.getStr(param, "userId");
        //领取任务
        taskService.claim(task.getId(), userId);
        int rows = orderCommentDao.acceptAppeal(param);
        if (rows != 1) {
            throw new HxdsException("更新订单评价的user_id字段失败");
        }
    }

    @Override
    @Transactional
    @LcnTransaction
    public void handleCommentAppeal(Map param) {
        String instanceId = MapUtil.getStr(param, "instanceId");
        String userId = MapUtil.getStr(param, "userId");
        //查找任务
        Task task = taskService.createTaskQuery().processInstanceId(instanceId)
                .includeProcessVariables().includeTaskLocalVariables().taskName("人工审核").taskAssignee(userId)
                .singleResult();
        if (task == null) {
            throw new HxdsException("没有找到审批任务");
        }
        String result = MapUtil.getStr(param, "result");
        String reason = MapUtil.getStr(param, "reason");
        String note = MapUtil.getStr(param, "note");
        taskService.setVariable(task.getId(), "result", result);
        taskService.setVariable(task.getId(), "note", note);

        param.remove("instanceId");
        int status = "同意".equals(result) ? 4 : 3;
        param.put("status", status);
        //更新评价状态
        int rows = orderCommentDao.updateStatus(param);
        if (rows != 1) {
            throw new HxdsException("更新评价状态失败");
        }
        //完成任务
        taskService.complete(task.getId());
    }

    @Override
    public HashMap searchAppealContent(String instanceId, boolean isEnd) {
        if (!isEnd) {
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).includeProcessVariables();
            ProcessInstance instance = query.singleResult();
            if (instance == null) {
                throw new HxdsException("没有找到工作流实例");
            }
            Map variables = instance.getProcessVariables();
            String reason = MapUtil.getStr(variables, "reason");
            String note = MapUtil.getStr(variables, "note");
            HashMap map = new HashMap() {{
                put("reason", reason);
                put("note", note);
            }};
            return map;
        } else {
            HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).includeProcessVariables();
            HistoricProcessInstance instance = query.singleResult();
            if (instance == null) {
                throw new HxdsException("没有找到工作流实例");
            }
            Map variables = instance.getProcessVariables();
            String reason = MapUtil.getStr(variables, "reason");
            String note = MapUtil.getStr(variables, "note");
            HashMap map = new HashMap() {{
                put("reason", reason);
                put("note", note);
            }};
            return map;
        }
    }


}
