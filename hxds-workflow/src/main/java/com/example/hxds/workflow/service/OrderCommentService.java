package com.example.hxds.workflow.service;

import java.util.HashMap;
import java.util.Map;

public interface OrderCommentService {
    public void startCommentWorkflow(Map args);

    public void acceptCommentAppeal(Map param);

    public void handleCommentAppeal(Map param);

    public HashMap searchAppealContent(String instanceId, boolean isEnd);
}
