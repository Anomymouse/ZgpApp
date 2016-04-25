package com.zgp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.zgp.view.ToastView;
import com.zgp.zgpapp.R;

/**
 * Created by 61720 on 2016/4/13.
 */
public class Fragment_2 extends BaseFragment implements View.OnClickListener {

    private View mView;

    private ImageButton fenlei_01;
    private ImageButton fenlei_02;
    private ImageButton fenlei_03;
    private ImageButton fenlei_04;
    private ImageButton fenlei_05;
    private ImageButton fenlei_06;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_2, container, false);

        initView(mView);

        return mView;
    }

    public void initView(View view) {
        fenlei_01 = (ImageButton) view.findViewById(R.id.fenlei_01);
        fenlei_02 = (ImageButton) view.findViewById(R.id.fenlei_02);
        fenlei_03 = (ImageButton) view.findViewById(R.id.fenlei_03);
        fenlei_04 = (ImageButton) view.findViewById(R.id.fenlei_04);
        fenlei_05 = (ImageButton) view.findViewById(R.id.fenlei_05);
        fenlei_06 = (ImageButton) view.findViewById(R.id.fenlei_06);

        fenlei_01.setOnClickListener(this);
        fenlei_02.setOnClickListener(this);
        fenlei_03.setOnClickListener(this);
        fenlei_04.setOnClickListener(this);
        fenlei_05.setOnClickListener(this);
        fenlei_06.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenlei_01:
                Toast("时令水果");
                break;
            case R.id.fenlei_02:
                Toast("反季水果");
                break;
            case R.id.fenlei_03:
                Toast("进口水果");
                break;
            case R.id.fenlei_04:
                Toast("国产水果");
                break;
            case R.id.fenlei_05:
                Toast("小包装水果");
                break;
            case R.id.fenlei_06:
                Toast("水果加工品");
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
}
