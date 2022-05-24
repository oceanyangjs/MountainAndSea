package com.mountain.sea.core.exception;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:12
 */
public enum ExceptionEnum {
    AUTHENTICATION_EXCEPTION("401", "用户名密码不正确"),
    USER_IDENTITY_EXCEPTION("402", "用户身份异常"),
    SERVICE_ERROR_EXCEPTION("400", "服务丢失"),
    GATEWAY_LOST_EXCEPTION("400", "Gateway丢失"),
    AUTHENTICATION_ENTRYPOINT_EXCEPTION("401", "操作失败，请联系管理员"),
    HANDLER_NOT_FOUND("404", "Api不存在"),
    REQUEST_NOT_ACCESS("403", "无权访问"),
    HANDLER_SYSTEM_EXCEPTION("500", "系统异常"),
    HANDLER_METHOD_NOT_SUPPORT_EXCEPTION("500", "不支持此method"),
    MQTT_CLIENT_EXCEPTION("9001", ""),
    PARAMETER_VALIDATION_EXCEPTION("2001", ""),
    URL_PARAMETER_MESSING_EXCEPTION("2002", "URL缺失变量{0}，类型{1}"),
    URL_PARAMETER_MISMATCH_EXCEPTION("2003", "参数类型不匹配"),
    URL_HTTPMESSAGE_READABLE_EXCEPTION("2004", "消息解析不正确"),
    DATA_PARSE_EXCEPTION("2005", "数据解析异常"),
    FILE_NOT_SUPPORT("2006", "不支持此类型文件"),
    ACTIVITI_NOTFOUND_PROCESS_DEFINITION("3001", "未找到流程定义"),
    ACTIVITI_PROCESS_DEFINITION_ACTIVED("3002", "流程定义已经是激活状态"),
    ACTIVITI_PROCESS_DEFINITION_SUSPENDED("3003", "流程定义已经是挂起状态"),
    ACTIVITI_NOTFOUND_PROCESS_INSTANCE("3004", "流程实例不存在"),
    ACTIVITI_NOTFOUND_TASK("3005", "任务不存在"),
    CTIVITI_READ_FILE_FAILUER("3006", "读取文件失败"),
    DATA_UPDATE_EXCEPTION("1001", "数据更新失败"),
    DATA_ACCESS_EXCEPTION("1002", "数据处理异常"),
    DATA_NOT_FOUND_EXCEPTION("1003", "未找到相关记录"),
    DATA_SELECT_EXCEPTION("1004", "查询数据异常"),
    DATA_INSERT_EXCEPTION("1005", "保存数据异常"),
    DATA_DELETE_EXCEPTION("1006", "删除数据异常"),
    DATA_DUPLICATE_EXCEPTION("1007", ""),
    DATA_USED_EXCEPTION("1008", "数据使用异常"),
    DATA_OPERATION_EXCEPTION("1009", "数据操作异常"),
    DATA_SELECTANDCHECK_EXCEPTION("1010", "存在关联数据,请解除关联关系时后再进行操作");

    private String errorCode;
    private String errorMessage;

    private ExceptionEnum() {
    }

    private ExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
