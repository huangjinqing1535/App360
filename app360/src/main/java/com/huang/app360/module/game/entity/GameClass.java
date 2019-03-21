package com.huang.app360.module.game.entity;

import java.util.List;

/**
 * Created by huang on 2018/4/16.
 */

public class GameClass {


    /**
     * code : 0
     * data : [{"case_name":"剑荡八荒","game_tag_name":"中国风","icon":"http://p0.qhimg.com/dr/160_160_/t01a64b72760dcff506.webp","id":"75973","url":""},{"case_name":"黄金裁决","game_tag_name":"欧美魔幻","icon":"http://p2.qhimg.com/dr/160_160_/t01a301060d512a3aeb.webp","id":"75972","url":""},{"case_name":"命运","game_tag_name":"日韩动漫","icon":"http://p6.qhimg.com/dr/160_160_/t015b59af65d6746d01.webp","id":"75971","url":""},{"case_name":"作妖计","game_tag_name":"Q版","icon":"http://p0.qhimg.com/dr/160_160_/t019b7f2d29ee6a8c5d.webp","id":"75970","url":""},{"case_name":"泰拉瑞亚","game_tag_name":"像素","icon":"http://p8.qhimg.com/dr/160_160_/t0103d053a4fd85d280.webp","id":"75978","url":""},{"case_name":"荒野行动","game_tag_name":"写实","icon":"http://p8.qhimg.com/dr/160_160_/t01e6fd65d908d97a71.webp","id":"75975","url":""},{"case_name":"超级幻影猫2","game_tag_name":"卡通","icon":"http://p8.qhimg.com/dr/160_160_/t018ba65e65367c418b.webp","id":"75976","url":""},{"case_name":"大话西游","game_tag_name":"3D","icon":"http://p3.qhimg.com/dr/160_160_/t0115480f0df314c039.webp","id":"75969","url":""},{"case_name":"王者传奇","game_tag_name":"魔幻","icon":"http://p0.qhimg.com/dr/160_160_/t01c1a1264fb3d6e0e6.webp","id":"75977","url":""},{"case_name":"我的汉克狗","game_tag_name":"写实卡通","icon":"http://p0.qhimg.com/dr/160_160_/t017c2d709c590bb100.webp","id":"75974","url":""},{"case_name":"消消乐海滨假日","game_tag_name":"小清新","icon":"http://p0.qhimg.com/dr/160_160_/t01ef6879f4dc7938c2.webp","id":"75979","url":""},{"case_name":"地狱边境","game_tag_name":"阴影","icon":"http://p7.qhimg.com/dr/160_160_/t01af918bff2aeadf3d.webp","id":"75980","url":""}]
     * msg : ok
     * ts : 1523879658
     */

    private int code;
    private String msg;
    private int ts;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * case_name : 剑荡八荒
         * game_tag_name : 中国风
         * icon : http://p0.qhimg.com/dr/160_160_/t01a64b72760dcff506.webp
         * id : 75973
         * url :
         */

        private String case_name;
        private String game_tag_name;
        private String icon;
        private String id;
        private String url;

        public String getCase_name() {
            return case_name;
        }

        public void setCase_name(String case_name) {
            this.case_name = case_name;
        }

        public String getGame_tag_name() {
            return game_tag_name;
        }

        public void setGame_tag_name(String game_tag_name) {
            this.game_tag_name = game_tag_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
