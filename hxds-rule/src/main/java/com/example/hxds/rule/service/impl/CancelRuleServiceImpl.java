package com.example.hxds.rule.service.impl;

import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.rule.db.dao.CancelRuleDao;
import com.example.hxds.rule.db.pojo.CancelRuleEntity;
import com.example.hxds.rule.service.CancelRuleService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class CancelRuleServiceImpl implements CancelRuleService {
    @Resource
    private CancelRuleDao cancelRuleDao;

    @Override
    public HashMap calculateDriverCancelOrder(String status, int cancelNum, int acceptMinute, int waitingMinute,String key) {
        HashMap param=new HashMap(){{
            put("type",1);
            put("key",key);
        }};
        CancelRuleEntity entity = cancelRuleDao.searchCurrentRule(param);
        String rule = entity.getRule();
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("status", status);
        context.put("cancelNum", cancelNum);
        context.put("acceptMinute", acceptMinute);
        context.put("waitingMinute", waitingMinute);

        try {
            HashMap map = (HashMap) runner.execute(rule, context, null, true, false);
            map.put("cancelRuleId", entity.getId());
            return map;
        } catch (Exception e) {
            log.error("计算取消订单失败", e);
            throw new HxdsException("计算取消订单失败");
        }
    }

    @Override
    public HashMap searchCancelRuleById(long ruleId) {
        HashMap map = cancelRuleDao.searchCancelRuleById(ruleId);
        return map;
    }
}
