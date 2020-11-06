package com.kitty.kitty_base.base.lvadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * @author nk
 * @date 2017/6/16
 */

public abstract class BasicListDataAdapter<T> extends BaseAdapter {

    protected List<T> datas;

    protected Context mContext;

    public BasicListDataAdapter(List<T> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    public void clearDatas() {
        datas.clear();
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 获取Adapter中的数据，datas不可能会是null，只有可能是datas.isEmpty()
     */
    public List<T> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), getItemLayoutId(position), null);
            holder = createViewHolderAndFindViewById(position, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        T data = datas.get(position);
        showData(position, holder, data);
        return convertView;
    }

    public abstract int getItemLayoutId(int position);

    public abstract ViewHolder createViewHolderAndFindViewById(int position, View convertView);

    public abstract void showData(int position, ViewHolder viewHolder, T data);



}
