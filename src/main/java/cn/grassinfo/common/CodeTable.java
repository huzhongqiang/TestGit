/*
 * Project: util-root
 * 
 * File Created at 2014-10-08
 
 * Copyright 2012 Greenline.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Greenline Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Greenline.com.
 */
package cn.grassinfo.common;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author rui.mao
 * @Type CodeTable
 * @Desc
 * @date 2016/6/29
 */
public enum CodeTable implements Serializable {

    //服务 [000]-[099]
    SUCCESS(0, "成功!"),
    EXCEPTION(1, "失败"),
    ERROR(2, "参数错误."),
    DATA_HAS_EXIST(3, "数据已存在!"),
    DATA_NONE_EXIST(4, "数据不存在"),
    CONNECT_ERROR(5, "连接失败"),
    ILLEGAL(6, "非法请求"),
    MEMBER_HAS_EXIST(70, "用户已注册"),
    IS_RESERVATION(71, "车辆已经被预约"),
    MEMBER_NOT_EXIST(72, "用户不存在"),
    DEVICETYPE_NOT_EXIST(73, "设备类型为空"),
    ;

    /**
     * 存放CODE -> Enum的Map
     */
    private static final ConcurrentMap<Integer, CodeTable> CODE_MAP = new ConcurrentHashMap<Integer, CodeTable>(
            CodeTable.values().length);

    /**
     * 填充CODE --> Enum的Map
     */
    static {
        for (CodeTable codeTable : CodeTable.values()) {
            CODE_MAP.put(codeTable.code, codeTable);
        }
    }

    /**
     * 响应码
     */
    private final int code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code    响应码
     * @param message 响应消息
     */
    CodeTable(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeTable fromCode(Integer code) {
        return CODE_MAP.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
