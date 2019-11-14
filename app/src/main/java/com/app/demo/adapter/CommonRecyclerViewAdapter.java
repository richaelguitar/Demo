package com.app.demo.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

/**
 @since 2019-02-06
 * RecyclerView公共适配器,只针对单一的item类型
 * T代表动态数据类型
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected LayoutInflater layoutInflater;
    protected List<T> mList;
    protected int mLayoutId;
    protected OnItemClickListener onItemClickListener;



    /**
     * 如果是给item条目里面的子控价添加监听，必须调用该构造方法
     * @param context
     * @param list
     * @param layoutId
     */
    public CommonRecyclerViewAdapter(Context context, List<T> list, int layoutId) {

       this(context, list,layoutId,null);
    }

    /**
     * 如果是要给item条目添加点击事件，必须调用该构造方法
     * @param context
     * @param list
     * @param layoutId
     * @param onItemClickListener
     */
    public CommonRecyclerViewAdapter(Context context, List<T> list, int layoutId, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mList = list;
        this.mLayoutId = layoutId;
        this.onItemClickListener = onItemClickListener;
    }

    public List<T> getmList() {
        return mList;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext,null,parent,mLayoutId,-1);
        //设置监听
        setListener(commonViewHolder,viewType);
        return commonViewHolder;
    }
   //是否启用多类型，给扩展类使用
    protected boolean isEnabled(int viewType)
    {
        return true;
    }

    public  void  setListener(final CommonViewHolder commonViewHolder, final int viewType){

        if (!isEnabled(viewType)) return;
        //判断监听是否实例化,设置整个条目的监听
        commonViewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onItemClickListener != null)
                {
                    int position = getPosition(commonViewHolder);
                    onItemClickListener.onItemClick(v, mList.get(position), position);
                }
            }
        });


        commonViewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (onItemClickListener != null)
                {
                    int position = getPosition(commonViewHolder);
                    return onItemClickListener.onLongItemClick(v, mList.get(position), position);
                }
                return false;
            }
        });
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //自定义绑定数据
        this.convert((CommonViewHolder)holder,mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 定义点击或者长按事件接口
     */
    public interface OnItemClickListener<T>{
        void onItemClick(View view, T object, int position);//点击
        boolean onLongItemClick(View view, T object, int position);//长按
    }

    //子类实现绑定数据的方法
    protected abstract void convert(CommonViewHolder viewHolder, T t);

    //设置事件监听
    public void  setOnItemClickListener(OnItemClickListener itemClickListener){
        this.onItemClickListener = itemClickListener;
    }

    public int getPosition(RecyclerView.ViewHolder viewHolder){
        return viewHolder.getAdapterPosition();
    }

    public void removeItem(T t) {
        int position = mList.indexOf(t);
        mList.remove(position);
        notifyItemRemoved(position);//Attention!
    }


}
