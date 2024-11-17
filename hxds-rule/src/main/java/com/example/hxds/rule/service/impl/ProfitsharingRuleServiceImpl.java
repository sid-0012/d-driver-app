package com.example.hxds.rule.service.impl;

import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.rule.db.dao.OrderCommentDao;
import com.example.hxds.rule.db.dao.OrderDao;
import com.example.hxds.rule.db.dao.ProfitsharingRuleDao;
import com.example.hxds.rule.db.pojo.ProfitsharingRuleEntity;
import com.example.hxds.rule.service.ProfitsharingRuleService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class ProfitsharingRuleServiceImpl implements ProfitsharingRuleService {
    @Resource
    private ProfitsharingRuleDao profitsharingRuleDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderCommentDao orderCommentDao;

    @Override
    public HashMap calculateProfitsharing(long orderId, String amount,String key) {
        //查询司机接单当天完成订单数和取消订单数
        long cancelNum = orderDao.searchCancelCountInDay(orderId);
        long finishNum = orderDao.searchFinishCountInDay(orderId);
        //查询司机接单当天的差评数
        long negativeNum = orderCommentDao.searchNegativeCountInDay(orderId);

        ProfitsharingRuleEntity entity = profitsharingRuleDao.searchCurrentRule(key);
        String rule = entity.getRule();
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("cancelNum", cancelNum);
        context.put("negativeNum", negativeNum);
        context.put("finishNum", finishNum);
        context.put("amount", amount);
        try {
            HashMap map = (HashMap) runner.execute(rule, context, null, true, false);
            map.put("ruleId", entity.getId().toString());
            return map;
        } catch (Exception e) {
            log.error("计算订单分账失败", e);
            throw new HxdsException("计算订单分账失败");
        }
    }

    @Override
    public HashMap searchProfitsharingRuleById(long ruleId) {
        HashMap map = profitsharingRuleDao.searchProfitsharingRuleById(ruleId);
        return map;
    }
}
