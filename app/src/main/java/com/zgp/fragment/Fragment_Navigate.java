package com.zgp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zgp.activity.MainActivity;
import com.zgp.zgpapp.R;


public class Fragment_Navigate extends Fragment implements OnClickListener {

    private View mView;
    private MainActivity mActivity;
    private SparseArray<RelativeLayout> naviArray;

    private int curId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.navigate_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        naviArray = new SparseArray<RelativeLayout>();
        initNaviArray(view, R.id.tab_rl_1);
        initNaviArray(view, R.id.tab_rl_2);
        initNaviArray(view, R.id.tab_rl_3);
        initNaviArray(view, R.id.tab_rl_4);
        initNaviArray(view, R.id.tab_rl_5);


        mActivity = (MainActivity) getActivity();
        mActivity.setNaviArray(naviArray);
    }

    private void initNaviArray(View view, int id) {
        RelativeLayout naviLayout = (RelativeLayout) view.findViewById(id);
        naviLayout.setOnClickListener(this);
        naviLayout.setSelected(false);
        naviArray.put(id, naviLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_rl_1:
                curId = R.id.tab_rl_1;
                mActivity.replaceFragment(R.id.tab_rl_1);
                break;
            case R.id.tab_rl_2:
                curId = R.id.tab_rl_2;
                mActivity.replaceFragment(R.id.tab_rl_2);
                break;
            case R.id.tab_rl_3:
                curId = R.id.tab_rl_3;
                mActivity.replaceFragment(R.id.tab_rl_3);
                break;
            case R.id.tab_rl_4:
                curId = R.id.tab_rl_4;
                mActivity.replaceFragment(R.id.tab_rl_4);
                break;
            case R.id.tab_rl_5:
                curId = R.id.tab_rl_5;
                mActivity.replaceFragment(R.id.tab_rl_5);
                break;
            default:
                break;
        }
    }
}