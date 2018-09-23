package com.duanjiefei.github.liveplayer.api;

import com.duanjiefei.github.liveplayer.model.Site;
import com.google.gson.annotations.Expose;

public class ErrorInfo {
    public static final int ERROR_TYPE_HTTP = 1;
    public static final int ERROR_TYPE_URL = 2;
    public static final int ERROR_TYPE_FATAL = 3;
    public static final int ERROR_TYPE_DATA_CONVERT = 4;
    public static final int ERROR_TYPE_PARSE_JSON = 5;


    //@Expose 默认有两个属性：serialize 和 deserialize，默认值都为 true，
    // 如果你给字段设置了 @Expose 注解，但是没有设置serialize 和 deserialize，
    // 那 model 的字段都将会输出。

    @Expose
    int type;  //错误类型

    @Expose
    String tag; //错误标记
    @Expose
    String functionName;  //函数名字

    //类名
    @Expose
    String className;

    // 错误 url
    @Expose
    String url;
    // 错误信息
    @Expose
    String errorException;
    // 错误原因
    @Expose
    String reason;
    // 网站信息
    @Expose
    Site site;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorException() {
        return errorException;
    }

    public void setErrorException(String errorException) {
        this.errorException = errorException;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
