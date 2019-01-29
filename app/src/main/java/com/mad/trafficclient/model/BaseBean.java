package com.mad.trafficclient.model;

import com.google.gson.annotations.SerializedName;

public class BaseBean {


    /**
     * RESULT : S
     * ERRMSG : 成功
     */

    //转到练习设备时注释下行代码
    @SerializedName("result")
    private String RESULT;
    private String ERRMSG;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getERRMSG() {
        return ERRMSG == null ? "连接服务器失败" : ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }
}
