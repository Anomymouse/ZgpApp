package com.zgp.http;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zgp.utils.MyLogger;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by 61720 on 2016/4/25.
 */
public class HttpController {

    public static void post(final String url, AjaxParams params, final IRequestCallBack callBack) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String str) {
                super.onSuccess(str);
                MyLogger.log("HttpController.post （" + url + "）：" + str);

                try {
                    JSONObject json = JSONObject.parseObject(str);

                    if (json.getJSONObject("status").getString("code").equals("success")) {
                        callBack.onSuccess(json.getString("datas").toString());
                    } else if (json.getJSONObject("status").getString("code").equals("failure")) {
                        callBack.onFailure(json.getJSONObject("status").getString("desc"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("操作失败！ JSONException");
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                callBack.onFailure("操作失败！ onFailure");
            }
        });
    }
}
