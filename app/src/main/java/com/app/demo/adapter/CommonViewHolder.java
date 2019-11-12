package com.app.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xww
 * @since 2019-02-06
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    //用来缓存各种类型的view
    private SparseArray<View> viewArray;
    private int mPosition;
    public View mConvertView;
    public Context mContext;
    public int mLayoutId;

    public CommonViewHolder(Context context, View itemView, ViewGroup parent, int position)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        viewArray = new SparseArray<View>();
        mConvertView.setTag(this);

    }


    public static CommonViewHolder get(Context context, View convertView,
                                       ViewGroup parent, int layoutId, int position)
    {
        if (convertView == null)
        {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            CommonViewHolder holder = new CommonViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else
        {
            CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public int getmPosition() {
        return mPosition;
    }

    /**
     * 获取view
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id){
        View view = viewArray.get(id);
        if(view == null){
            view = mConvertView.findViewById(id);
            viewArray.put(id,view);
        }
        return (T)view;
    }

    public View getConvertView(){
        return this.mConvertView;
    }


    /**
     * 设置text显示值
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return  this;
    }

    public CommonViewHolder setImageByResource(int viewId, int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }
    public CommonViewHolder setImageByBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public CommonViewHolder setImageByURL(int viewId, Uri uri){
        ImageView imageView = getView(viewId);
        imageView.setImageURI(uri);
        return this;
    }

    public CommonViewHolder setImageByDrawable(int viewId, Drawable drawable){
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }
}
