package com.example.hxds.bff.driver.feign;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.bff.driver.controller.form.*;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "hxds-odr")
public interface OdrServiceApi {
    @PostMapping("/order/searchDriverTodayBusinessData")
    public R searchDriverTodayBusinessData(SearchDriverTodayBusinessDataForm form);

    @PostMapping("/order/acceptNewOrder")
    public R acceptNewOrder(AcceptNewOrderForm form);

    @PostMapping("/order/searchDriverExecuteOrder")
    public R searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);

    @PostMapping("/order/searchDriverCurrentOrder")
    public R searchDriverCurrentOrder(SearchDriverCurrentOrderForm form);

    @PostMapping("/order/searchOrderForMoveById")
    public R searchOrderForMoveById(SearchOrderForMoveByIdForm form);

    @PostMapping("/order/arriveStartPlace")
    public R arriveStartPlace(ArriveStartPlaceForm form);

    @PostMapping("/order/startDriving")
    public R startDriving(StartDrivingForm form);

    @PostMapping("/order/updateOrderStatus")
    public R updateOrderStatus(UpdateOrderStatusForm form);

    @PostMapping("/order/validDriverOwnOrder")
    public R validDriverOwnOrder(ValidDriverOwnOrderForm form);

    @PostMapping("/order/searchSettlementNeedData")
    public R searchSettlementNeedData(SearchSettlementNeedDataForm form);

    @PostMapping("/bill/updateBillFee")
    public R updateBillFee(UpdateBillFeeForm form);

    @PostMapping("/bill/searchReviewDriverOrderBill")
    public R searchReviewDriverOrderBill(SearchReviewDriverOrderBillForm form);

    @PostMapping("/order/searchOrderStatus")
    public R searchOrderStatus(SearchOrderStatusForm form);

    @PostMapping("/order/updateOrderAboutPayment")
    public R updateOrderAboutPayment(UpdateOrderAboutPaymentForm form);

    @PostMapping("/order/searchOrderById")
    public R searchOrderById(SearchOrderByIdForm form);

    @PostMapping("/comment/searchCommentByOrderId")
    public R searchCommentByOrderId(SearchCommentByOrderIdForm form);

    @PostMapping("/order/searchDriverOrderByPage")
    public R searchDriverOrderByPage(SearchDriverOrderByPageForm form);


}
