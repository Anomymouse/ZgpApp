package com.zgp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zgp.bean.Bean_Goods;
import com.zgp.zgpapp.R;

import java.util.List;

/**
 * Created by 61720 on 2016/4/22.
 */
public class Adapter_GvGoods extends BaseAdapter {

    private List<Bean_Goods> list;

    private List<List<Bean_Goods>> listTotle;

    private Context context;
    private LayoutInflater inflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options; // 显示图像设置

    public Adapter_GvGoods(Context c, List<Bean_Goods> list) {
        this.context = c;
        this.list = list;

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
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_gvgoods, parent, false);
            holder.left_goods_img = (ImageView) convertView.findViewById(R.id.left_goods_img);
            holder.left_goods_title = (TextView) convertView.findViewById(R.id.left_goods_title);
            holder.left_goods_title_more = (TextView) convertView.findViewById(R.id.left_goods_title_more);
            holder.left_goods_praise = (TextView) convertView.findViewById(R.id.left_goods_praise);
            holder.left_goods_update_time = (TextView) convertView.findViewById(R.id.left_goods_update_time);

            holder.rigth_goods_img = (ImageView) convertView.findViewById(R.id.right_goods_img);
            holder.rigth_goods_title = (TextView) convertView.findViewById(R.id.right_goods_title);
            holder.rigth_goods_title_more = (TextView) convertView.findViewById(R.id.right_goods_title_more);
            holder.rigth_goods_praise = (TextView) convertView.findViewById(R.id.right_goods_praise);
            holder.rigth_goods_update_time = (TextView) convertView.findViewById(R.id.right_goods_update_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list == null || list.get(position) == null) {
            return null;
        }

        Bean_Goods goods = list.get(position);
        if (TextUtils.isEmpty(goods.getGoods_img())) {
            holder.left_goods_img.setImageResource(R.drawable.logo);
        } else {
            imageLoader.displayImage(goods.getGoods_img(), holder.left_goods_img, options);
        }
        holder.left_goods_title.setText(goods.getGoods_title());
        holder.left_goods_title_more.setText(goods.getGoods_title_more());
        holder.left_goods_update_time.setText((TextUtils.isEmpty(goods.getGoods_update_time()) || ("null".equals(goods.getGoods_update_time()))) ? "2015年11月11日" : goods.getGoods_update_time());
        holder.left_goods_praise.setText(TextUtils.isEmpty(goods.getGoods_praise()) ? "嘟柚爱车" : goods.getGoods_praise());

        return convertView;
    }

    private class ViewHolder {
        public ImageView left_goods_img;
        public TextView left_goods_title;
        public TextView left_goods_title_more;
        public TextView left_goods_praise;
        public TextView left_goods_update_time;

        public ImageView rigth_goods_img;
        public TextView rigth_goods_title;
        public TextView rigth_goods_title_more;
        public TextView rigth_goods_praise;
        public TextView rigth_goods_update_time;
    }
}
