package com.example.hxds.workflow.bpmn.comment;

import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.workflow.db.dao.OrderCommentDao;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class UpdateDataService implements JavaDelegate {

    @Resource
    private OrderCommentDao orderCommentDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(DelegateExecution execution) {
        long commentId = Long.parseLong(execution.getVariable("commentId", String.class));
        HashMap param = new HashMap() {{
            put("rate", 5);
            put("remark", null);
            put("commentId", commentId);
        }};
        int rows = orderCommentDao.updateRateAndRemark(param);
        if (rows != 1) {
            throw new HxdsException("更新订单评价分数和文字内容失败");
        }

        //TODO 更新当天差评数量
        //TODO 如果有罚款需要删除
        //TODO 如果有限制接单需要删除
        System.out.println("更新数据");
    }
}
