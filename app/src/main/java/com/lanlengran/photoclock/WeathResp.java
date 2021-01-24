package com.lanlengran.photoclock;

import java.util.List;

public class WeathResp {

    /**
     * data : {"yesterday":{"date":"16日星期六","high":"高温 0℃","fx":"西北风","low":"低温 -8℃","fl":"<![CDATA[3级]]>","type":"晴"},"city":"北京","forecast":[{"date":"17日星期天","high":"高温 4℃","fengli":"<![CDATA[2级]]>","low":"低温 -7℃","fengxiang":"西风","type":"晴"},{"date":"18日星期一","high":"高温 4℃","fengli":"<![CDATA[2级]]>","low":"低温 -7℃","fengxiang":"东北风","type":"多云"},{"date":"19日星期二","high":"高温 0℃","fengli":"<![CDATA[2级]]>","low":"低温 -8℃","fengxiang":"东北风","type":"多云"},{"date":"20日星期三","high":"高温 2℃","fengli":"<![CDATA[1级]]>","low":"低温 -7℃","fengxiang":"北风","type":"晴"},{"date":"21日星期四","high":"高温 5℃","fengli":"<![CDATA[1级]]>","low":"低温 -7℃","fengxiang":"北风","type":"晴"}],"ganmao":"感冒高发期，尽量避免外出，外出戴口罩防护。","wendu":"1"}
     * status : 1000
     * desc : OK
     */

    public DataBean data;
    public int status;
    public String desc;


    public static class DataBean {
        /**
         * yesterday : {"date":"16日星期六","high":"高温 0℃","fx":"西北风","low":"低温 -8℃","fl":"<![CDATA[3级]]>","type":"晴"}
         * city : 北京
         * forecast : [{"date":"17日星期天","high":"高温 4℃","fengli":"<![CDATA[2级]]>","low":"低温 -7℃","fengxiang":"西风","type":"晴"},{"date":"18日星期一","high":"高温 4℃","fengli":"<![CDATA[2级]]>","low":"低温 -7℃","fengxiang":"东北风","type":"多云"},{"date":"19日星期二","high":"高温 0℃","fengli":"<![CDATA[2级]]>","low":"低温 -8℃","fengxiang":"东北风","type":"多云"},{"date":"20日星期三","high":"高温 2℃","fengli":"<![CDATA[1级]]>","low":"低温 -7℃","fengxiang":"北风","type":"晴"},{"date":"21日星期四","high":"高温 5℃","fengli":"<![CDATA[1级]]>","low":"低温 -7℃","fengxiang":"北风","type":"晴"}]
         * ganmao : 感冒高发期，尽量避免外出，外出戴口罩防护。
         * wendu : 1
         */

        public YesterdayBean yesterday;
        public String city;
        public String ganmao;
        public String wendu;
        public List<ForecastBean> forecast;

        public static class YesterdayBean {
            /**
             * date : 16日星期六
             * high : 高温 0℃
             * fx : 西北风
             * low : 低温 -8℃
             * fl : <![CDATA[3级]]>
             * type : 晴
             */

            public String date;
            public String high;
            public String fx;
            public String low;
            public String fl;
            public String type;
        }

        public static class ForecastBean {
            /**
             * date : 17日星期天
             * high : 高温 4℃
             * fengli : <![CDATA[2级]]>
             * low : 低温 -7℃
             * fengxiang : 西风
             * type : 晴
             */

            public String date;
            public String high;
            public String fengli;
            public String low;
            public String fengxiang;
            public String type;

        }
    }
}
