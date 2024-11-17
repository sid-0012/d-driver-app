package com.example.hxds.rule.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "计算司机取消订单的表单")
public class CalculateDriverCancelOrderForm {

    @NotBlank(message = "status不能为空")
    @Pattern(regexp = "^未到达代驾点$||^到达代驾点$||^开始代驾$")
    @Schema(description = "状态")
    private String status;

    @NotNull(message = "cancelNum不能为空")
    @Min(value = 0, message = "cancelNum不能小于0")
    @Schema(description = "司机当天取消订单次数")
    private Short cancelNum;

    @NotNull(message = "acceptMinute不能为空")
    @Min(value = 0, message = "acceptMinute不能小于0")
    @Schema(description = "接单口到当前的分钟数")
    private Short acceptMinute;

    @NotNull(message = "waitingMinute不能为空")
    @Min(value = 0, message = "waitingMinute不能小于0")
    @Schema(description = "司机等时分钟数")
    private Short waitingMinute;
}
