package com.zgp.application;

/**
 * Created by 61720 on 2016/4/25.
 */
public class UrlPath {

    //Url 相关
    public static final String SERVER_URL = "http://192.168.0.112/index.php?r=";
    public static final String IMAGE_URL = "http://115.28.32.71";

    public static final String ControllerName = "/App/";

    /**
     * Fragment 1
     */
    //水果商品列表
    public static final String GetGoodsList = SERVER_URL + ControllerName + "GetGoodsList";
    //广告列表
    public static final String GetBannerList = SERVER_URL + ControllerName + "GetBannerList";
}
