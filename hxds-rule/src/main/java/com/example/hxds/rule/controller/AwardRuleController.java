package com.example.hxds.rule.controller;


import com.example.hxds.rule.controller.form.CalculateIncentiveFeeForm;
import com.example.hxds.rule.service.AwardRuleService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/award")
@Tag(name = "AwardRuleController", description = "系统奖励规则Web接口")
public class AwardRuleController{
    @Resource
    private AwardRuleService awardRuleService;


    @PostMapping("/calculateIncentiveFee")
    @Operation(summary = "计算系统奖励")
    public R calculateIncentiveFee(@RequestBody @Valid CalculateIncentiveFeeForm form) {
        String incentiveFee = awardRuleService.calculateIncentiveFee(form.getDriverId(), form.getAcceptTime(), "Chinese");
        return R.ok().put("result", incentiveFee);
    }


}
