package com.zgp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zgp.adapter.Adapter_GvGoods;
import com.zgp.bean.Bean_Banner;
import com.zgp.bean.Bean_Goods;
import com.zgp.utils.Util_System;
import com.zgp.zgpapp.R;

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

    /**
     * 图片加载相关
     */
    private ImageLoader imageLoader = ImageLoader.getInstance();// 得到图片加载器
    private DisplayImageOptions bannerOptions;

    /**
     * 广告相关
     */
    private List<Bean_Banner> mNewsList;
    private ViewPager vp_banner;// 广告banner
    private LinearLayout ll_dot;// 广告右下角 点
    private TextView tv_newsTitle;// 广告标题
    private int currentItem = 0; // 当前图片的索引号
    private boolean isTaskRun;//当用户滑动中暂停自动滑动任务，提高UI交互效果
    private ScheduledExecutorService scheduledExecutorService;//定时任务


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        List<Bean_Goods> list = new ArrayList<>();
        Bean_Goods beanGoods1 = new Bean_Goods();
        beanGoods1.setGoods_praise("1");
        beanGoods1.setGoods_img("1");
        beanGoods1.setGoods_title("1");
        beanGoods1.setGoods_title_more("1");
        beanGoods1.setGoods_update_time("1");
        Bean_Goods beanGoods2 = new Bean_Goods();
        beanGoods2.setGoods_praise("2");
        beanGoods2.setGoods_img("2");
        beanGoods2.setGoods_title("2");
        beanGoods2.setGoods_title_more("2");
        beanGoods2.setGoods_update_time("2");
        Bean_Goods beanGoods3 = new Bean_Goods();
        beanGoods3.setGoods_praise("3");
        beanGoods3.setGoods_img("3");
        beanGoods3.setGoods_title("3");
        beanGoods3.setGoods_title_more("3");
        beanGoods3.setGoods_update_time("3");
        list.add(beanGoods1);
        list.add(beanGoods2);
        list.add(beanGoods3);
        adapterGvGoods = new Adapter_GvGoods(getActivity(), list);
        gridView.setAdapter(adapterGvGoods);

        mNewsList = new ArrayList<>();
        Bean_Banner banner1 = new Bean_Banner();
        banner1.setId(1);
        banner1.setContent("1");
        banner1.setImgUrl("1");
        banner1.setTitle("1");
        Bean_Banner banner2 = new Bean_Banner();
        banner2.setId(2);
        banner2.setContent("2");
        banner2.setImgUrl("2");
        banner2.setTitle("2");
        Bean_Banner banner3 = new Bean_Banner();
        banner3.setId(3);
        banner3.setContent("3");
        banner3.setImgUrl("3");
        banner3.setTitle("3");
        mNewsList.add(banner1);
        mNewsList.add(banner2);
        mNewsList.add(banner3);


    }

    @Override
    public void onClick(View v) {

    }

}
