package com.hunre.food_center.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hunre.food_center.FoodData;
import com.hunre.food_center.R;

import java.util.ArrayList;

import static com.hunre.food_center.R.id.ratingBar;

/**
 * Created by Administrator on 10/01/2017.
 */

public class AdapterFood extends BaseAdapter {
    private ArrayList<FoodData> foodDatas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AdapterFood(Context mContext, ArrayList<FoodData> foodDatas) {
        this.mContext = mContext;
        this.foodDatas = foodDatas;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodDatas != null ? foodDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return  foodDatas.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_gridview, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.img_food);
            holder.txtname = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtAddr = (TextView) convertView.findViewById(R.id.txt_addr);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FoodData food = (FoodData) getItem(position);
        Log.e("getUrlImg",""+food.getUrlImg());
        Glide.with(mContext).load(food.getUrlImg())
                .into(holder.mImage);
        holder.txtname.setText(food.getName());
        holder.txtAddr.setText(String.valueOf(food.getAddr()));
        holder.txtPrice.setText(String.valueOf(food.getPrice()));
        return convertView;
    }

    public class ViewHolder {
        RatingBar ratingBar;
        private ImageView mImage;
        private TextView txtname;
        private TextView txtAddr;
        private TextView txtPrice;
    }
}
