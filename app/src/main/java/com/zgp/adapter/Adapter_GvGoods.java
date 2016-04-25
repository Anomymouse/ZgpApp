package com.zgp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zgp.application.UrlPath;
import com.zgp.bean.Bean_Goods;
import com.zgp.callback.CallBack_ItemButton;
import com.zgp.utils.MyLogger;
import com.zgp.zgpapp.R;

import java.util.List;

/**
 * Created by 61720 on 2016/4/22.
 */
public class Adapter_GvGoods extends BaseAdapter {

    private List<List<Bean_Goods>> listTotle;
    private CallBack_ItemButton callBack_itemButton;

    private Context context;
    private LayoutInflater inflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options; // 显示图像设置

    public Adapter_GvGoods(Context c, List<List<Bean_Goods>> list, CallBack_ItemButton callBack_itemButton) {
        this.context = c;
        this.listTotle = list;
        this.callBack_itemButton = callBack_itemButton;

        inflater = LayoutInflater.from(c);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(c));
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.logo) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.logo) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.logo) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .build(); // 创建配置过得DisplayImageOption对象
    }

    @Override
    public int getCount() {
        return listTotle.size();
    }

    @Override
    public Object getItem(int position) {
        return listTotle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_gvgoods, parent, false);
            holder.item_ll_left = (LinearLayout) convertView.findViewById(R.id.item_ll_left);
            holder.left_goods_img = (ImageView) convertView.findViewById(R.id.left_goods_img);
            holder.left_goods_title = (TextView) convertView.findViewById(R.id.left_goods_title);
            holder.left_goods_title_more = (TextView) convertView.findViewById(R.id.left_goods_title_more);
            holder.left_goods_praise = (TextView) convertView.findViewById(R.id.left_goods_praise);
            holder.left_goods_update_time = (TextView) convertView.findViewById(R.id.left_goods_update_time);

            holder.item_ll_right = (LinearLayout) convertView.findViewById(R.id.item_ll_right);
            holder.rigth_goods_img = (ImageView) convertView.findViewById(R.id.right_goods_img);
            holder.rigth_goods_title = (TextView) convertView.findViewById(R.id.right_goods_title);
            holder.rigth_goods_title_more = (TextView) convertView.findViewById(R.id.right_goods_title_more);
            holder.rigth_goods_praise = (TextView) convertView.findViewById(R.id.right_goods_praise);
            holder.rigth_goods_update_time = (TextView) convertView.findViewById(R.id.right_goods_update_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (listTotle == null || listTotle.get(position) == null) {
            return null;
        }

        List<Bean_Goods> listItem = listTotle.get(position);

        if (listItem.size() == 2) {
            Bean_Goods goodsLeft = listItem.get(0);

            if (TextUtils.isEmpty(goodsLeft.getGoods_img())) {
                imageLoader.displayImage(UrlPath.IMAGE_URL + goodsLeft.getGoods_img(), holder.left_goods_img, options);
            } else {
                imageLoader.displayImage(UrlPath.IMAGE_URL + goodsLeft.getGoods_img(), holder.left_goods_img, options);
            }
            holder.left_goods_title.setText(goodsLeft.getGoods_title());
            holder.left_goods_title_more.setText(goodsLeft.getGoods_title_more());
            holder.left_goods_update_time.setText((TextUtils.isEmpty(goodsLeft.getGoods_update_time()) || ("null".equals(goodsLeft.getGoods_update_time()))) ? "2015年11月11日" : goodsLeft.getGoods_update_time());
            holder.left_goods_praise.setText(goodsLeft.getGoods_praise());

            Bean_Goods goodsRight = listTotle.get(position).get(1);
            if (TextUtils.isEmpty(goodsRight.getGoods_img())) {
                imageLoader.displayImage(UrlPath.IMAGE_URL + goodsRight.getGoods_img(), holder.rigth_goods_img, options);
            } else {
                imageLoader.displayImage(UrlPath.IMAGE_URL + goodsRight.getGoods_img(), holder.rigth_goods_img, options);
            }
            holder.rigth_goods_title.setText(goodsRight.getGoods_title());
            holder.rigth_goods_title_more.setText(goodsRight.getGoods_title_more());
            holder.rigth_goods_update_time.setText((TextUtils.isEmpty(goodsRight.getGoods_update_time()) || ("null".equals(goodsRight.getGoods_update_time()))) ? "2015年11月11日" : goodsRight.getGoods_update_time());
            holder.rigth_goods_praise.setText(goodsLeft.getGoods_praise());
        } else if (listItem.size() == 1) {
            Bean_Goods goodsLeft = listItem.get(0);

            if (TextUtils.isEmpty(goodsLeft.getGoods_img())) {
                holder.left_goods_img.setImageResource(R.drawable.logo);
                imageLoader.displayImage(goodsLeft.getGoods_img(), holder.left_goods_img, options);
            } else {
                imageLoader.displayImage(goodsLeft.getGoods_img(), holder.left_goods_img, options);
            }
            holder.left_goods_title.setText(goodsLeft.getGoods_title());
            holder.left_goods_title_more.setText(goodsLeft.getGoods_title_more());
            holder.left_goods_update_time.setText((TextUtils.isEmpty(goodsLeft.getGoods_update_time()) || ("null".equals(goodsLeft.getGoods_update_time()))) ? "2015年11月11日" : goodsLeft.getGoods_update_time());
            holder.left_goods_praise.setText(goodsLeft.getGoods_praise());

            holder.item_ll_right.setVisibility(View.GONE);
        }

        holder.item_ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLogger.log("点击了左边");
                callBack_itemButton.onMeClick(position, 0);
            }
        });

        holder.item_ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLogger.log("点击了右边");
                callBack_itemButton.onMeClick(position, 1);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public LinearLayout item_ll_left;
        public ImageView left_goods_img;
        public TextView left_goods_title;
        public TextView left_goods_title_more;
        public TextView left_goods_praise;
        public TextView left_goods_update_time;

        public LinearLayout item_ll_right;
        public ImageView rigth_goods_img;
        public TextView rigth_goods_title;
        public TextView rigth_goods_title_more;
        public TextView rigth_goods_praise;
        public TextView rigth_goods_update_time;
    }
}
