package com.zgp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import com.zgp.utils.Util_System;
import com.zgp.view.ToastView;
import com.zgp.zgpapp.R;

import net.tsz.afinal.FinalHttp;

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
    private List<Bean_Banner> mNewsList;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finalHttp = new FinalHttp();
        inflater = LayoutInflater.from(getActivity());

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
                mNewsList = JSON.parseArray(result, Bean_Banner.class);
                if (mNewsList != null && mNewsList.size() > 0) {
                    initViewPager();
                } else {
                    mNewsList = new ArrayList<Bean_Banner>();
                }

                MyLogger.log("mNewsList.size：" + mNewsList.size());
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

    private void initViewPager() {
        float density = Util_System.getDensity(getActivity());
        int length = mNewsList.size();

        for (int i = 0; i < length; i++) {
            ImageView view = (ImageView) inflater.inflate(R.layout.banner_item, null);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast("点击了：" + mNewsList.get(index).getId());
                }
            });
            mViewList.add(view);
        }

        // 设置点
        for (int i = 0; i < mNewsList.size(); i++) {
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
        for (int i = 0; i < mNewsList.size(); i++) {
            if (i == 0) {
                tv_newsTitle.setText(mNewsList.get(0).getTitle());
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
            imageLoader.displayImage(UrlPath.IMAGE_URL + mNewsList.get(position).getImgUrl(), mViewList.get(position), bannerOptions);
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
            tv_newsTitle.setText(mNewsList.get(position).getTitle());
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
