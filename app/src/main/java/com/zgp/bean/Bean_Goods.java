package com.zgp.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 61720 on 2016/4/22.
 */
public class Bean_Goods {

    private int id;
    private String name;
    private String type;

    private String goods_img;
    private String goods_title;
    private String goods_title_more;
    private String goods_praise;
    private String goods_update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_title_more() {
        return goods_title_more;
    }

    public void setGoods_title_more(String goods_title_more) {
        this.goods_title_more = goods_title_more;
    }

    public String getGoods_praise() {
        return goods_praise;
    }

    public void setGoods_praise(String goods_praise) {
        this.goods_praise = goods_praise;
    }

    public String getGoods_update_time() {
        return goods_update_time;
    }

    public void setGoods_update_time(String goods_update_time) {
        this.goods_update_time = goods_update_time;
    }
}
