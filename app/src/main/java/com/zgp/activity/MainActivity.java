package com.zgp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zgp.fragment.Fragment_1;
import com.zgp.fragment.Fragment_2;
import com.zgp.fragment.Fragment_3;
import com.zgp.fragment.Fragment_4;
import com.zgp.fragment.Fragment_5;
import com.zgp.zgpapp.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    // fragment容器
    private FrameLayout mContainer;
    // 存储底部导航栏
    private SparseArray<RelativeLayout> naviArray;
    private MyAdapter adapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyAdapter(getSupportFragmentManager());
        mContainer = (FrameLayout) findViewById(R.id.content_frame);

        replaceFragment(R.id.tab_rl_1);// xk修改，设置首页默认显示
    }

    @Override
    public void onClick(View v) {

    }

    public SparseArray<RelativeLayout> getNaviArray() {
        return naviArray;
    }

    public void setNaviArray(SparseArray<RelativeLayout> naviArray) {
        this.naviArray = naviArray;
    }

    /**
     * 设置显示指定id的fragment
     *
     * @param id
     */
    public void replaceFragment(int id) {
        Fragment fragment = (Fragment) adapter.instantiateItem(mContainer, id);
        adapter.setPrimaryItem(mContainer, 0, fragment);
        adapter.finishUpdate(mContainer);
        // 重置导航选中状态
        for (int i = 0, size = naviArray.size(); i < size; i++) {
            int curId = naviArray.keyAt(i);
            if (curId == id) {
                naviArray.get(curId).setSelected(true);
            } else {
                naviArray.get(curId).setSelected(false);
            }
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case R.id.tab_rl_1:
                    return new Fragment_1();
                case R.id.tab_rl_2:
                    return new Fragment_2();
                case R.id.tab_rl_3:
                    return new Fragment_3();
                case R.id.tab_rl_4:
                    return new Fragment_4();
                case R.id.tab_rl_5:
                    return new Fragment_5();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 5;
        }
    }
}
