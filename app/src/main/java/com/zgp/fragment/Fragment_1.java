package com.zgp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zgp.adapter.Adapter_GvGoods;
import com.zgp.application.UrlPath;
import com.zgp.bean.Bean_Banner;
import com.zgp.bean.Bean_Goods;
import com.zgp.callback.CallBack_ItemButton;
import com.zgp.http.HttpController;
import com.zgp.http.IRequestCallBack;
import com.zgp.utils.MyLogger;
import com.zgp.utils.Util_System;
import com.zgp.view.ToastView;
import com.zgp.zgpapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 61720 on 2016/4/13.
 */
public class Fragment_1 extends BaseFragment implements View.OnClickListener {

    private View view;
    private ListView listView;
    private Adapter_GvGoods adapterGvGoods;

    private LinearLayout ll_item_1;
    private LinearLayout ll_item_2;
    private LinearLayout ll_item_3;
    private LinearLayout ll_item_4;

    /**
     * 商品相关
     */
    private List<List<Bean_Goods>> goodsTotleList;

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
    private LayoutInflater inflater;
    // 显示图片的imageView
    private List<ImageView> mViewList = new ArrayList<ImageView>();
    // 显示点的imageView
    private List<ImageView> dots = new ArrayList<ImageView>();

    // 切换Banner当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            vp_banner.setCurrentItem(currentItem);// 切换当前显示的图片
        }

    };


    private CallBack_ItemButton callBack = new CallBack_ItemButton() {
        @Override
        public void onMeClick(int position, int x) {
            Toast("Position:" + position + " X:" + x);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = LayoutInflater.from(getActivity());

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        bannerOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.banner_default) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.banner_default) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.banner_default) // 设置图片加载或解码过程中发生错误显示的图片
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
        listView = (ListView) view.findViewById(R.id.listView);

        LinearLayout hearderViewLayout = (LinearLayout) inflater.inflate(R.layout.lv_headview, null);
        listView.addHeaderView(hearderViewLayout);

        vp_banner = (ViewPager) view.findViewById(R.id.vp_banner);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        tv_newsTitle = (TextView) view.findViewById(R.id.tv_news_title);

        ll_item_1 = (LinearLayout) view.findViewById(R.id.ll_item_1);
        ll_item_2 = (LinearLayout) view.findViewById(R.id.ll_item_2);
        ll_item_3 = (LinearLayout) view.findViewById(R.id.ll_item_3);
        ll_item_4 = (LinearLayout) view.findViewById(R.id.ll_item_4);

        ll_item_1.setOnClickListener(this);
        ll_item_2.setOnClickListener(this);
        ll_item_3.setOnClickListener(this);
        ll_item_4.setOnClickListener(this);
    }

    public void initData() {
        //1 获取Banner列表
        HttpController.post(UrlPath.GetBannerList, null, new IRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                bannerList = JSON.parseArray(result, Bean_Banner.class);
                if (bannerList != null && bannerList.size() > 0) {
                    initViewPager();
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
                List<Bean_Goods> goodsList = JSON.parseArray(result, Bean_Goods.class);
                if (goodsList != null && goodsList.size() > 0) {

                    List<Bean_Goods> itemList = new ArrayList<Bean_Goods>();

                    goodsTotleList = new ArrayList<List<Bean_Goods>>();

                    for (int i = 1; i < goodsList.size() + 1; i++) {
                        if (i % 2 == 0) {
                            itemList.add(goodsList.get(i - 1));
                            goodsTotleList.add(itemList);
                        } else {
                            itemList = new ArrayList<Bean_Goods>();
                            itemList.add(goodsList.get(i - 1));

                            if (i == goodsList.size()) {
                                goodsTotleList.add(itemList);
                            }
                        }
                    }

                    adapterGvGoods = new Adapter_GvGoods(getActivity(), goodsTotleList, callBack);
                } else {
                    goodsTotleList = new ArrayList<List<Bean_Goods>>();
                }
                listView.setAdapter(adapterGvGoods);

                MyLogger.log("asddfadf:" + JSON.toJSONString(goodsTotleList));
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
        switch (v.getId()) {
            case R.id.ll_item_1:
                Toast("贴心客服");
                break;
            case R.id.ll_item_2:
                Toast("限时抢购");
                break;
            case R.id.ll_item_3:
                Toast("每日推荐");
                break;
            case R.id.ll_item_4:
                Toast("摇一摇");
                break;
            default:
                break;
        }
    }

    private void Toast(String str) {
        ToastView toastView = new ToastView(getActivity(), str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }

    private void initViewPager() {
        float density = Util_System.getDensity(getActivity());
        int length = bannerList.size();

        for (int i = 0; i < length; i++) {
            ImageView view = (ImageView) inflater.inflate(R.layout.banner_item, null);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast("点击了：" + bannerList.get(index).getId());
                }
            });
            mViewList.add(view);
        }

        // 设置点
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (10 * density), (int) (10 * density)));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getLayoutParams());
            lp.setMargins((int) (2.5 * density), 0, (int) (2.5 * density), 0);
            imageView.setLayoutParams(lp);
            if (i == 0) {
                imageView.setImageResource(R.drawable.dot_focused);
            } else {
                imageView.setImageResource(R.drawable.dot_normal);
            }
            dots.add(imageView);
            ll_dot.addView(imageView);
        }
        //设置文字
        for (int i = 0; i < bannerList.size(); i++) {
            if (i == 0) {
                tv_newsTitle.setText(bannerList.get(0).getTitle());
            }
        }
        if (mViewList != null && mViewList.size() > 0) {
            vp_banner.setAdapter(new ViewPagerAdapter());
        }
        vp_banner.setOnPageChangeListener(new PageChangeListener());
        vp_banner.setCurrentItem(0);
        //开启自动轮播任务
        startTask();
    }

    /**
     * 布局适配器，设置数据
     *
     * @author xk
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mViewList.get(position).getParent() == null) {
                ((ViewPager) container).addView(mViewList.get(position));
            } else {
                ((ViewGroup) (mViewList.get(position).getParent())).removeView(mViewList.get(position));
                ((ViewPager) container).addView(mViewList.get(position));
            }

            //MyLogger.log("图片加载：" + UrlPath.IMAGE_URL + bannerList.get(position).getImgUrl());
            imageLoader.displayImage(UrlPath.IMAGE_URL + bannerList.get(position).getImgUrl(), mViewList.get(position), bannerOptions);
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        /* state:0空闲，1是滑行中，2加载完毕*/
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0 && !isTaskRun) {
                startTask();
            } else if (state == 1 && isTaskRun) {
                stopTask();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            tv_newsTitle.setText(bannerList.get(position).getTitle());
            setDotImage(position);
        }
    }

    private void setDotImage(int position) {
        for (int i = 0; i < dots.size(); i++) {
            if (i == position) {
                dots.get(i).setImageResource(R.drawable.dot_focused);
            } else {
                dots.get(i).setImageResource(R.drawable.dot_normal);
            }
        }
    }

    /**
     * 换行切换任务
     *
     * @author Administrator
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (vp_banner) {
                currentItem = (currentItem + 1) % mViewList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    /**
     * 开启定时任务
     */
    private void startTask() {
        //开启任务
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        // command：执行线程 initialDelay：初始化延时 period：两次开始执行最小间隔时间 unit：计时单位
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 3, TimeUnit.SECONDS);
        isTaskRun = true;
    }

    /**
     * 停止定时任务
     */
    private void stopTask() {
        isTaskRun = false;
        scheduledExecutorService.shutdown();
    }
}
