package com.zgp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zgp.adapter.Adapter_GvGoods;
import com.zgp.application.UrlPath;
import com.zgp.bean.Bean_Banner;
import com.zgp.bean.Bean_Goods;
import com.zgp.http.HttpController;
import com.zgp.http.IRequestCallBack;
import com.zgp.utils.MyLogger;
import com.zgp.view.ToastView;
import com.zgp.zgpapp.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by 61720 on 2016/4/13.
 */
public class Fragment_1 extends BaseFragment implements View.OnClickListener {

    private View view;
    private GridView gridView;
    private Adapter_GvGoods adapterGvGoods;

    private FinalHttp finalHttp;

    /**
     * 商品相关
     */
    private List<Bean_Goods> goodsList;

    /**
     * 图片加载相关
     */
    private ImageLoader imageLoader = ImageLoader.getInstance();// 得到图片加载器
    private DisplayImageOptions bannerOptions;

    /**
     * 广告相关
     */
    private List<Bean_Banner> bannerList;
    private ViewPager vp_banner;// 广告banner
    private LinearLayout ll_dot;// 广告右下角 点
    private TextView tv_newsTitle;// 广告标题
    private int currentItem = 0; // 当前图片的索引号
    private boolean isTaskRun;//当用户滑动中暂停自动滑动任务，提高UI交互效果
    private ScheduledExecutorService scheduledExecutorService;//定时任务


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finalHttp = new FinalHttp();

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        bannerOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.logo) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.logo) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.logo) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .build(); // 创建配置过得DisplayImageOption对象
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);

        initView(view);

        initData();

        return view;
    }

    public void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);

        vp_banner = (ViewPager) view.findViewById(R.id.vp_banner);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        tv_newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
    }

    public void initData() {
        //1 获取Banner列表
        HttpController.post(UrlPath.GetBannerList, null, new IRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                bannerList = JSON.parseArray(result, Bean_Banner.class);
                if (bannerList != null && bannerList.size() > 0) {
                } else {
                    bannerList = new ArrayList<Bean_Banner>();
                }

                MyLogger.log("bannerList.size：" + bannerList.size());
            }

            @Override
            public void onFailure(String failure) {
                Toast(failure);
            }
        });

        //2 获取水果商品列表
        HttpController.post(UrlPath.GetGoodsList, null, new IRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                goodsList = JSON.parseArray(result, Bean_Goods.class);
                if (goodsList != null && goodsList.size() > 0) {
                    adapterGvGoods = new Adapter_GvGoods(getActivity(), goodsList);
                } else {
                    goodsList = new ArrayList<Bean_Goods>();
                }
                gridView.setAdapter(adapterGvGoods);

                MyLogger.log("goodsList.size：" + goodsList.size());
            }

            @Override
            public void onFailure(String failure) {
                Toast(failure);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void Toast(String str) {
        ToastView toastView = new ToastView(getActivity(), str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }
}
