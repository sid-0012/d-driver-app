package com.example.hxds.workflow;

import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.workflow.db.dao.OrderCommentDao;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
public class Test {
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

    @org.junit.jupiter.api.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void aaa() {
//        long orderId = 721156060337385472L;
//        long commentId = 740724633309261824L;
//        long driverId = 740724633309261824L;
//        long customerId = 740724633309261824L;
//
//        HashMap param = new HashMap() {{
//            put("orderId", orderId + "");
//            put("commentId", commentId + "");
//            put("driverId", driverId + "");
//            put("customerId", customerId + "");
//            put("reason", "申诉原因");
//        }};
//        String instanceId = runtimeService.startProcessInstanceByKey("comment", param).getProcessInstanceId(); //启动工作流
//        //工作流实例更新到数据表
//        System.out.println(instanceId);
//
//        Task task = taskService.createTaskQuery().processInstanceId("226eabd2-ebe1-11ec-b426-4851c57e4015")
//                .includeProcessVariables().includeTaskLocalVariables().taskName("人工审核").singleResult();
//
//        taskService.claim(task.getId(), "1234");
//        //更新instance_id字段
//        param = new HashMap() {{
//            put("commentId", commentId);
//            put("instanceId", instanceId);
//        }};
//        int rows = orderCommentDao.updateInstanceIdById(param);
//        if (rows != 1) {
//            throw new HxdsException("更新订单评价instance_id字段失败");
//        }
//
//        //执行审批
//        String result = "同意";
//        taskService.setVariable(task.getId(), "result", result);
//        taskService.setVariable(task.getId(), "note", "你当天确实有闯红灯的现象");
//        //更新评价状态
//        param.remove("instanceId");
//        int status = "同意".equals(result) ? 3 : 4;
//        param.put("status", status);
//        orderCommentDao.updateStatus(param);
//
//        taskService.complete(task.getId());
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceId("f0520e53-ebff-11ec-95b5-4851c57e4015").includeProcessVariables();
        ProcessInstance instance = query.singleResult();
        System.out.println(instance.getProcessVariables().get("reason"));

    }
}
