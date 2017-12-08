package com.amy.weather.api.entity;

import com.amy.weather.api.ApiManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amy on 2017/12/7.
 */

public class HeWeather6 implements Serializable{

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.0"}
         * update : {"loc":"2017-12-07 08:50","utc":"2017-12-07 00:50"}
         * status : ok
         * now : {"cloud":"0","cond_code":"100","cond_txt":"晴","fl":"-9","hum":"28","pcpn":"0","pres":"1029","tmp":"2","vis":"8","wind_deg":"7","wind_dir":"北风","wind_sc":"3-4","wind_spd":"13"}
         * daily_forecast : [{"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴","date":"2017-12-07","hum":"28","mr":"20:58","ms":"10:36","pcpn":"0.0","pop":"0","pres":"1032","sr":"07:21","ss":"16:51","tmp_max":"6","tmp_min":"-5","uv_index":"1","vis":"20","wind_deg":"306","wind_dir":"西北风","wind_sc":"微风","wind_spd":"9"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2017-12-08","hum":"23","mr":"22:07","ms":"11:23","pcpn":"0.0","pop":"0","pres":"1029","sr":"07:22","ss":"16:51","tmp_max":"6","tmp_min":"-4","uv_index":"1","vis":"20","wind_deg":"224","wind_dir":"西南风","wind_sc":"微风","wind_spd":"4"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2017-12-09","hum":"21","mr":"23:14","ms":"12:03","pcpn":"0.0","pop":"0","pres":"1021","sr":"07:23","ss":"16:51","tmp_max":"6","tmp_min":"-3","uv_index":"1","vis":"20","wind_deg":"194","wind_dir":"西南风","wind_sc":"微风","wind_spd":"4"}]
         * hourly : [{"cloud":"10","cond_code":"103","cond_txt":"晴间多云","dew":"-16","hum":"25","pop":"0","pres":"1030","time":"2017-12-07 10:00","tmp":"0","wind_deg":"327","wind_dir":"西北风","wind_sc":"微风","wind_spd":"15"},{"cloud":"38","cond_code":"103","cond_txt":"晴间多云","dew":"-16","hum":"20","pop":"0","pres":"1029","time":"2017-12-07 13:00","tmp":"3","wind_deg":"320","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"18"},{"cloud":"24","cond_code":"103","cond_txt":"晴间多云","dew":"-16","hum":"21","pop":"0","pres":"1029","time":"2017-12-07 16:00","tmp":"3","wind_deg":"325","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"19"},{"cloud":"4","cond_code":"103","cond_txt":"晴间多云","dew":"-14","hum":"25","pop":"0","pres":"1031","time":"2017-12-07 19:00","tmp":"1","wind_deg":"318","wind_dir":"西北风","wind_sc":"微风","wind_spd":"11"},{"cloud":"8","cond_code":"103","cond_txt":"晴间多云","dew":"-13","hum":"29","pop":"0","pres":"1032","time":"2017-12-07 22:00","tmp":"0","wind_deg":"320","wind_dir":"西北风","wind_sc":"微风","wind_spd":"8"},{"cloud":"12","cond_code":"103","cond_txt":"晴间多云","dew":"-17","hum":"24","pop":"0","pres":"1032","time":"2017-12-08 01:00","tmp":"-2","wind_deg":"284","wind_dir":"西北风","wind_sc":"微风","wind_spd":"6"},{"cloud":"20","cond_code":"103","cond_txt":"晴间多云","dew":"-17","hum":"26","pop":"0","pres":"1030","time":"2017-12-08 04:00","tmp":"-4","wind_deg":"254","wind_dir":"西南风","wind_sc":"微风","wind_spd":"6"},{"cloud":"12","cond_code":"103","cond_txt":"晴间多云","dew":"-17","hum":"25","pop":"0","pres":"1030","time":"2017-12-08 07:00","tmp":"-3","wind_deg":"250","wind_dir":"西南风","wind_sc":"微风","wind_spd":"6"}]
         * lifestyle : [{"brf":"较不舒适","txt":"白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。","type":"comf"},{"brf":"冷","txt":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","type":"drsg"},{"brf":"较易发","txt":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。","type":"flu"},{"brf":"较适宜","txt":"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动注意防风并适当增减衣物。","type":"sport"},{"brf":"一般","txt":"天空状况还是比较好的，但温度稍微有点低，且风稍大，会让您感觉些许凉意。外出请注意防风。","type":"trav"},{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","type":"uv"},{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","type":"cw"},{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。","type":"air"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private NowBean now;
        private List<DailyForecastBean> daily_forecast;
        private List<HourlyBean> hourly;
        private List<LifestyleBean> lifestyle;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.4052887
             * tz : +8.0
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2017-12-07 08:50
             * utc : 2017-12-07 00:50
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class NowBean {
            /**
             * cloud : 0
             * cond_code : 100
             * cond_txt : 晴
             * fl : -9
             * hum : 28
             * pcpn : 0
             * pres : 1029
             * tmp : 2
             * vis : 8
             * wind_deg : 7
             * wind_dir : 北风
             * wind_sc : 3-4
             * wind_spd : 13
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 101
             * cond_code_n : 100
             * cond_txt_d : 多云
             * cond_txt_n : 晴
             * date : 2017-12-07
             * hum : 28
             * mr : 20:58
             * ms : 10:36
             * pcpn : 0.0
             * pop : 0
             * pres : 1032
             * sr : 07:21
             * ss : 16:51
             * tmp_max : 6
             * tmp_min : -5
             * uv_index : 1
             * vis : 20
             * wind_deg : 306
             * wind_dir : 西北风
             * wind_sc : 微风
             * wind_spd : 9
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class HourlyBean {
            /**
             * cloud : 10
             * cond_code : 103
             * cond_txt : 晴间多云
             * dew : -16
             * hum : 25
             * pop : 0
             * pres : 1030
             * time : 2017-12-07 10:00
             * tmp : 0
             * wind_deg : 327
             * wind_dir : 西北风
             * wind_sc : 微风
             * wind_spd : 15
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String dew;
            private String hum;
            private String pop;
            private String pres;
            private String time;
            private String tmp;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getDew() {
                return dew;
            }

            public void setDew(String dew) {
                this.dew = dew;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class LifestyleBean {
            /**
             * brf : 较不舒适
             * txt : 白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。
             * type : comf
             */

            private String brf;
            private String txt;
            private String type;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
    public String getTodayTempDescription(){
        final int todayIndex = getTodayDailyForecastIndex();
        if(todayIndex != -1){
            HeWeather6Bean.DailyForecastBean forecast = HeWeather6.get(0).getDaily_forecast().get(todayIndex);
            return forecast.getTmp_min()+ "~" + forecast.getTmp_max()+ "°";
        }
        return "";
    }
    public int getTodayDailyForecastIndex(){
        int todayIndex = -1;
        if(!HeWeather6.get(0).getStatus().equals("ok")){
            return todayIndex;
        }
        for(int i = 0;i<HeWeather6.get(0).getDaily_forecast().size(); i++){
            if(ApiManager.isToday(HeWeather6.get(0).getDaily_forecast().get(i).getDate())){
                todayIndex = i;
                break;
            }
        }
        return todayIndex;
    }
}
