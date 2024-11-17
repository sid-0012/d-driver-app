package com.example.hxds.workflow.bpmn.comment;

import com.example.hxds.workflow.controller.form.SendPrivateMessageForm;
import com.example.hxds.workflow.feign.SnmServiceApi;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NotifyDriverService implements JavaDelegate {

    @Resource
    private SnmServiceApi snmServiceApi;

    @Override
    public void execute(DelegateExecution execution) {
        long driverId = Long.parseLong(execution.getVariable("driverId", String.class));
        long orderId = Long.parseLong(execution.getVariable("orderId", String.class));
        String result = execution.getVariable("result", String.class);
        String note = execution.getVariable("note", String.class);

        SendPrivateMessageForm messageForm = new SendPrivateMessageForm();
        messageForm.setReceiverIdentity("message");
        messageForm.setReceiverId(driverId);
        messageForm.setTtl(3 * 24 * 3600 * 1000);
        messageForm.setSenderId(0L);
        messageForm.setSenderIdentity("system");
        messageForm.setSenderName("华夏代驾");

        String msg = "订单号:" + orderId + "，该订单的差评申诉";
        if ("同意".equals(result)) {
            msg += "成功";
        } else {
            msg += "失败";
            if (note != null) {
                msg += "。" + note;
            }
        }
        messageForm.setMsg(msg);
        snmServiceApi.sendPrivateMessageSync(messageForm);
    }
}
