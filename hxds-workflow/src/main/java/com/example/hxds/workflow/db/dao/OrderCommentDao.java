package com.example.hxds.workflow.db.dao;

import java.util.HashMap;
import java.util.Map;

public interface OrderCommentDao {
    public HashMap searchCommentIdAndInstanceId(Map param);

    public int startWorkflow(Map param);

    public int acceptAppeal(Map param);

    public int updateRateAndRemark(Map param);

    public int updateStatus(Map param);
}




