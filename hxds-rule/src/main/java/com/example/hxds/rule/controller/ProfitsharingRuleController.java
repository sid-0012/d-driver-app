package com.example.hxds.rule.controller;

import com.example.hxds.common.util.R;
import com.example.hxds.rule.controller.form.CalculateProfitsharingForm;
import com.example.hxds.rule.controller.form.SearchProfitsharingRuleByIdForm;
import com.example.hxds.rule.service.ProfitsharingRuleService;
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
@RequestMapping("/profitsharing")
@Tag(name = "ProfitsharingRuleController", description = "分账规则Web接口")
public class ProfitsharingRuleController{

    @Resource
    private ProfitsharingRuleService profitsharingRuleService;

    @PostMapping("/calculateProfitsharing")
    @Operation(summary = "计算分账规则")
    public R calculateProfitsharing(@RequestBody @Valid CalculateProfitsharingForm form) {
        HashMap map = profitsharingRuleService.calculateProfitsharing(form.getOrderId(), form.getAmount(),"Chinese");
        return R.ok().put("result", map);
    }

    @PostMapping("/searchProfitsharingRuleById")
    @Operation(summary = "根据ID查询分账规则")
    public R searchProfitsharingRuleById(@RequestBody @Valid SearchProfitsharingRuleByIdForm form) {
        HashMap map = profitsharingRuleService.searchProfitsharingRuleById(form.getRuleId());
        return R.ok().put("result", map);
    }
}
