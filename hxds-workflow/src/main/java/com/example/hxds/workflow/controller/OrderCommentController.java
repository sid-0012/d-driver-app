package com.example.hxds.workflow.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.common.util.R;
import com.example.hxds.workflow.controller.form.AcceptCommentAppealForm;
import com.example.hxds.workflow.controller.form.HandleCommentAppealForm;
import com.example.hxds.workflow.controller.form.SearchAppealContentForm;
import com.example.hxds.workflow.controller.form.StartCommentWorkflowForm;
import com.example.hxds.workflow.service.OrderCommentService;
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
import java.util.Map;

@RestController
@RequestMapping("/comment")
@Tag(name = "OrderController", description = "订单评价工作流Web接口")
public class OrderCommentController {


    @Resource
    private OrderCommentService orderCommentService;

    @PostMapping("/startCommentWorkflow")
    @Operation(summary = "开启评价申诉工作流")
    public R startCommentWorkflow(@RequestBody @Valid StartCommentWorkflowForm form) {
        HashMap args = new HashMap() {{
            put("orderId", form.getOrderId().toString());
            put("driverId", form.getDriverId().toString());
            put("customerId", form.getCustomerId().toString());
            put("reason", form.getReason());
        }};

        orderCommentService.startCommentWorkflow(args);
        return R.ok();
    }

    @PostMapping("/acceptCommentAppeal")
    @Operation(summary = "受理评价申诉")
    public R acceptCommentAppeal(@RequestBody @Valid AcceptCommentAppealForm form) {
        Map param = BeanUtil.beanToMap(form);
        orderCommentService.acceptCommentAppeal(param);
        return R.ok();
    }

    @PostMapping("/handleCommentAppeal")
    @Operation(summary = "处理评价申诉")
    public R handleCommentAppeal(@RequestBody @Valid HandleCommentAppealForm form) {
        Map param = BeanUtil.beanToMap(form);
        orderCommentService.handleCommentAppeal(param);
        return R.ok();
    }

    @PostMapping("/searchAppealContent")
    @Operation(summary = "查询审批工作流内容")
    public R searchAppealContent(@RequestBody @Valid SearchAppealContentForm form) {
        HashMap map = orderCommentService.searchAppealContent(form.getInstanceId(), form.getIsEnd());
        return R.ok().put("result", map);
    }
}
