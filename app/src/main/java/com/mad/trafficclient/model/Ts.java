package com.mad.trafficclient.model;

import com.google.gson.annotations.SerializedName;

public class Ts {

        @SerializedName("pm2.5")
        private int _$Pm25184; // FIXME check this code
        private int co2;
        private int LightIntensity;
        private int humidity;
        private int temperature;

        public int get_$Pm25184() {
            return _$Pm25184;
        }

        public void set_$Pm25184(int _$Pm25184) {
            this._$Pm25184 = _$Pm25184;
        }

        public int getCo2() {
            return co2;
        }

        public void setCo2(int co2) {
            this.co2 = co2;
        }

        public int getLightIntensity() {
            return LightIntensity;
        }

        public void setLightIntensity(int LightIntensity) {
            this.LightIntensity = LightIntensity;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

}
