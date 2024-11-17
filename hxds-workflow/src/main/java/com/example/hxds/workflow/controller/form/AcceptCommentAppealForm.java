package com.example.hxds.workflow.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "受理订单评价申诉的表单")
public class AcceptCommentAppealForm {
    @NotNull(message = "userId不能为空")
    @Min(value = 1, message = "userId不能小于1")
    @Schema(description = "用户ID")
    private Integer userId;

    @NotBlank(message = "userName不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "userName内容不正确")
    @Schema(description = "用户姓名")
    private String userName;

    @NotNull(message = "commentId不能为空")
    @Min(value = 1, message = "commentId不能小于1")
    @Schema(description = "评价ID")
    private Long commentId;
}
