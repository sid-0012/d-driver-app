package com.example.hxds.rule.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 司机分账表
 * @TableName tb_profitsharing_rule
 */
@Data
public class ProfitsharingRuleEntity implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则代码
     */
    private String rule;

    /**
     * 状态代码，1有效，2关闭
     */
    private Integer status;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}