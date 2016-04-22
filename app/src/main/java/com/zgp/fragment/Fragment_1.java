package com.zgp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.zgp.adapter.Adapter_GvGoods;
import com.zgp.bean.Bean_Goods;
import com.zgp.zgpapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 61720 on 2016/4/13.
 */
public class Fragment_1 extends BaseFragment implements View.OnClickListener {

    private View view;
    private GridView gridView;
    private Adapter_GvGoods adapterGvGoods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);

        gridView = (GridView) view.findViewById(R.id.gridView);

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
        Bean_Goods beanGoods4 = new Bean_Goods();
        beanGoods4.setGoods_praise("4");
        beanGoods4.setGoods_img("4");
        beanGoods4.setGoods_title("4");
        beanGoods4.setGoods_title_more("4");
        beanGoods4.setGoods_update_time("4");
        Bean_Goods beanGoods5 = new Bean_Goods();
        beanGoods5.setGoods_praise("5");
        beanGoods5.setGoods_img("5");
        beanGoods5.setGoods_title("5");
        beanGoods5.setGoods_title_more("5");
        beanGoods5.setGoods_update_time("5");

        list.add(beanGoods1);
        list.add(beanGoods2);
        list.add(beanGoods3);
        list.add(beanGoods4);
        list.add(beanGoods5);

        adapterGvGoods = new Adapter_GvGoods(getActivity(), list);

        gridView.setAdapter(adapterGvGoods);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
