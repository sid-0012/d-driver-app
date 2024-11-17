package com.example.hxds.rule.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.rule.db.dao.ChargeRuleDao;
import com.example.hxds.rule.db.pojo.ChargeRuleEntity;
import com.example.hxds.rule.service.ChargeRuleService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class ChargeRuleServiceImpl implements ChargeRuleService {
    @Resource
    private ChargeRuleDao chargeRuleDao;

    @Override
    public HashMap calculateOrderCharge(String mileage, String time, int minute,String key) {
        ChargeRuleEntity entity = chargeRuleDao.searchCurrentRule(key);
        String rule = entity.getRule();
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("mileage", mileage);
        context.put("time", time);
        context.put("minute", minute);
        try {
            HashMap map = (HashMap) runner.execute(rule, context, null, true, false);
            map.put("chargeRuleId", entity.getId());
            return map;
        } catch (Exception e) {
            log.error("计算代驾费用失败", e);
            throw new HxdsException("计算代驾费用失败");
        }
    }

    @Override
    @Transactional
    @LcnTransaction
    public int insert(ChargeRuleEntity entity) {
        int rows = chargeRuleDao.insert(entity);
        return rows;
    }

    @Override
    public HashMap searchChargeRuleById(long ruleId) {
        HashMap map = chargeRuleDao.searchChargeRuleById(ruleId);
        return map;
    }
}
