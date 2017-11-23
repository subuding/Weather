package com.dragonfly.weatherdemo.weather.bean;

/**
 * Created by Amy on 2017/9/26.
 */

public class Woeid {


    /**
     * query : {"count":1,"created":"2017-09-26T04:42:37Z","lang":"zh-CN","results":{"place":{"woeid":"2151849"}}}
     */

    private QueryBean query;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public static class QueryBean {
        /**
         * count : 1
         * created : 2017-09-26T04:42:37Z
         * lang : zh-CN
         * results : {"place":{"woeid":"2151849"}}
         */

        private int count;
        private String created;
        private String lang;
        private ResultsBean results;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public ResultsBean getResults() {
            return results;
        }

        public void setResults(ResultsBean results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * place : {"woeid":"2151849"}
             */

            private PlaceBean place;

            public PlaceBean getPlace() {
                return place;
            }

            public void setPlace(PlaceBean place) {
                this.place = place;
            }

            public static class PlaceBean {
                /**
                 * woeid : 2151849
                 */

                private String woeid;

                public String getWoeid() {
                    return woeid;
                }

                public void setWoeid(String woeid) {
                    this.woeid = woeid;
                }
            }
        }
    }
}
