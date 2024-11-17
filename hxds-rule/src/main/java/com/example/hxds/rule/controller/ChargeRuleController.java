package com.example.hxds.rule.controller;

import com.example.hxds.common.util.R;
import com.example.hxds.rule.controller.form.CalculateOrderChargeForm;
import com.example.hxds.rule.controller.form.EstimateOrderChargeForm;
import com.example.hxds.rule.controller.form.SearchChargeRuleByIdForm;
import com.example.hxds.rule.service.ChargeRuleService;
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
@RequestMapping("/charge")
@Tag(name = "ChargeRuleController", description = "代驾费用的Web接口")
public class ChargeRuleController{
    @Resource
    private ChargeRuleService chargeRuleService;

    @PostMapping("/estimateOrderCharge")
    public R estimateOrderCharge(@RequestBody @Valid EstimateOrderChargeForm form) {
        HashMap map = chargeRuleService.calculateOrderCharge(form.getMileage(), form.getTime(), 0, "Chinese");
        return R.ok().put("result", map);
    }


    @PostMapping("/calculateOrderCharge")
    @Operation(summary = "计算代驾费用")
    public R calculateOrderCharge(@RequestBody @Valid CalculateOrderChargeForm form) {
        HashMap map = chargeRuleService.calculateOrderCharge(form.getMileage(), form.getTime(), form.getMinute(), "Chinese");
        return R.ok().put("result", map);
    }

    @PostMapping("/searchChargeRuleById")
    @Operation(summary = "根据ID查询费用规则")
    public R searchChargeRuleById(@RequestBody @Valid SearchChargeRuleByIdForm form) {
        HashMap map = chargeRuleService.searchChargeRuleById(form.getRuleId());
        return R.ok().put("result", map);
    }
}
