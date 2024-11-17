package com.example.hxds.rule.db.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单取消规则表
 *
 * @TableName tb_charge_rule
 */
@Data
public class CancelRuleEntity implements Serializable {
    /**
     * 主键
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
     * 规则类型，1司机取消规则，2乘客取消规则
     */
    private Byte type;

    /**
     * 状态代码，1有效，2关闭
     */
    private Byte status;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}