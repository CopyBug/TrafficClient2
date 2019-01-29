package com.mad.trafficclient.model;

import com.google.gson.annotations.SerializedName;

public class Huanjin  extends BaseBean {


    /**
     * temperature : 31
     * RESULT : S
     */

    private String temperature;
    private String RESULT;
    /**
     * humidity : 83
     */

    private String humidity;
    /**
     * co2 : 5400
     */

    private String co2;
    @SerializedName("pm2.5")
    private String _$Pm25295; // FIXME check this code

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String get_$Pm25295() {
        return _$Pm25295;
    }

    public void set_$Pm25295(String _$Pm25295) {
        this._$Pm25295 = _$Pm25295;
    }
}
