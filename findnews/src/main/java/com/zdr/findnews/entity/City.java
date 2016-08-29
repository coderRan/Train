package com.zdr.findnews.entity;

/**
 * Created by zdr on 16-8-25.
 */
public class City {

    /**
     * errNum : 0
     * retMsg : success
     * retData : {"cityName":"烟台","provinceName":"山东","cityCode":"101120501","zipCode":"264000","telAreaCode":"0535"}
     */

    private int errNum;
    private String retMsg;
    /**
     * cityName : 烟台
     * provinceName : 山东
     * cityCode : 101120501
     * zipCode : 264000
     * telAreaCode : 0535
     */

    private RetDataBean retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String cityName;
        private String provinceName;
        private String cityCode;
        private String zipCode;
        private String telAreaCode;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getTelAreaCode() {
            return telAreaCode;
        }

        public void setTelAreaCode(String telAreaCode) {
            this.telAreaCode = telAreaCode;
        }

        @Override
        public String toString() {
            return "RetDataBean{" +
                    "cityName='" + cityName + '\'' +
                    ", provinceName='" + provinceName + '\'' +
                    ", cityCode='" + cityCode + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    ", telAreaCode='" + telAreaCode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "City{" +
                "errNum=" + errNum +
                ", retMsg='" + retMsg + '\'' +
                ", retData=" + retData.toString() +
                '}';
    }
}
