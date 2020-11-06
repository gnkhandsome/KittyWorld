package com.kitty.kitty_base.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kitty.kitty_base.base.enums.LoadType;

import java.util.List;


/**
 * Created by hongliJiang on 2019/10/17 15:49
 * 描述：适配器基类
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    private List<T> mDatas;
    private LoadFinishListener loadListener;
    public OnItemClickListener onItemClickListener;
    public int dp_5;
    public int dp_10;
    public int mScreenWidth;
    public BaseRvAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(mContext).inflate(setView(itemType), viewGroup, false);
        return setCreateViewHolder(view, itemType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        onBindHolder(viewHolder, i, mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 设置item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * 设置加载结束监听
     *
     * @param listener
     */
    public void setOnFinishLoadListener(LoadFinishListener listener) {
        this.loadListener = listener;
    }


    protected abstract int setView(int itemType);

    protected abstract RecyclerView.ViewHolder setCreateViewHolder(View view, int itemType);

    protected abstract void onBindHolder(RecyclerView.ViewHolder viewHolder, int position, T t);

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setDatas(List<T> datas) {
        mDatas = datas;
        if (loadListener != null) {
            loadListener.onNotify(LoadType.REFRESH, datas.size(), getDatas().size());
        }
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
        if (loadListener != null) {
            loadListener.onNotify(LoadType.LOAD, datas.size(), getDatas().size());
        }
    }

    /**
     * 加载结束监听
     */
    public interface LoadFinishListener {
        /**
         * @param loadType 加载方式
         * @param laodSize 每次加载的个数
         * @param total    适配器目前总共的数据个数
         */
        void onNotify(LoadType loadType, int laodSize, int total);

    }
}
