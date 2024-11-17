package com.example.hxds.rule.controller;

import com.example.hxds.common.util.R;
import com.example.hxds.rule.controller.form.CalculateDriverCancelOrderForm;
import com.example.hxds.rule.controller.form.SearchCancelRuleByIdForm;
import com.example.hxds.rule.service.CancelRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/cancel")
@Tag(name = "CancelRuleController", description = "订单取消的Web接口")
public class CancelRuleController{
    @Resource
    private CancelRuleService cancelRuleService;

    @PostMapping("/calculateDriverCancelOrder")
    @Operation(summary = "计算司机取消订单")
    public R calculateDriverCancelOrder(@RequestBody @Valid CalculateDriverCancelOrderForm form) {
        String status = form.getStatus();
        int cancelNum = form.getCancelNum();
        int acceptMinute = form.getAcceptMinute();
        int waitingMinute = form.getWaitingMinute();
        HashMap map = cancelRuleService.calculateDriverCancelOrder(status, cancelNum, acceptMinute, waitingMinute,"Chinese");
        return R.ok().put("result", map);
    }

    @PostMapping("/searchCancelRuleById")
    @Operation(summary = "根据ID查询取消规则")
    public R searchCancelRuleById(@RequestBody @Valid SearchCancelRuleByIdForm form) {
        HashMap map = cancelRuleService.searchCancelRuleById(form.getRuleId());
        return R.ok().put("result", map);
    }
}
