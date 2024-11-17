package com.example.hxds.workflow.feign;

import com.example.hxds.common.util.R;
import com.example.hxds.workflow.controller.form.SendPrivateMessageForm;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-snm")
public interface SnmServiceApi {
    @PostMapping("/message/sendPrivateMessage")
    @Operation(summary = "同步发送私有消息")
    public R sendPrivateMessage(SendPrivateMessageForm form);

    @PostMapping("/message/sendPrivateMessageSync")
    @Operation(summary = "异步发送私有消息")
    public R sendPrivateMessageSync(SendPrivateMessageForm form);
}
