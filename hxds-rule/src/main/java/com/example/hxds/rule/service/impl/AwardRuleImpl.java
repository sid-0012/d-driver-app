package com.example.hxds.rule.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.rule.db.dao.AwardRuleDao;
import com.example.hxds.rule.db.dao.OrderDao;
import com.example.hxds.rule.db.pojo.AwardRuleEntity;
import com.example.hxds.rule.service.AwardRuleService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class AwardRuleImpl implements AwardRuleService {
    @Resource
    private AwardRuleDao awardRuleDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public String calculateIncentiveFee(long driverId, String acceptTime,String key) {
        //获得当前时间段接单的数量
        AwardRuleEntity entity = awardRuleDao.searchCurrentRule(key);
        String rule = entity.getRule();
        ExpressRunner runner = new ExpressRunner();
        //查询时间段
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("flag", false);
        context.put("acceptTime", acceptTime);
        try {
            HashMap map = (HashMap) runner.execute(rule, context, null, true, false);
            map.put("cancelRuleId", entity.getId());

            if (map.size() == 1) {
                String start = MapUtil.getStr(map, "start");
                String end = MapUtil.getStr(map, "end");
                //查询该时间段接单总数
                HashMap param = new HashMap() {{
                    put("driverId", driverId);
                    put("start", start);
                    put("end", end);
                }};
                long count = orderDao.searchFinishCountInRange(param); //该时间段接单数
                if (count == 0) {
                    //该时间段接单总数为0，奖励金额为0.00
                    return "0.00";
                } else {
                    context.replace("flag", true);
                    context.put("real", count);
                    //计算系统奖励金额
                    String incentiveFee = (String) runner.execute(rule, context, null, true, false);
                    return incentiveFee;
                }
            } else {
                //该时间段没有奖励，返回奖励金额为0.00
                return "0.00";
            }
        } catch (Exception e) {
            log.error("计算系统奖励失败", e);
            throw new HxdsException("计算系统奖励失败");
        }
    }
}
