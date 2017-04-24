package cn.asheng.blur.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<E> extends android.widget.BaseAdapter implements AdapterInterface {
    private List<E> dataCache;
    protected LayoutInflater mInflater;
    protected Context context;
    private Dialog mProgressDialogCancle;

    public BaseAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 获取适配器数据
     *
     * @return List数据
     */
    public List<E> getDataCache() {
        return dataCache;
    }

    /**
     * 设置适配器数据
     *
     * @param dataCache 所有数据
     * @return void
     */
    public void setDataCache(List<E> dataCache) {
        this.dataCache = dataCache;
    }

    /**
     * 更新适配器数据，并刷新
     *
     * @param data 所有数据
     * @return void
     */
    public void update(List<E> data) {
        this.dataCache = data;
        notifyDataSetChanged();
        setDataCache(data);
    }

    /**
     * 添加部分数据，并刷新
     *
     * @param set 部分数据
     * @return void
     */
    public void add(List<E> set) {
        if (this.dataCache == null)
            this.dataCache = new ArrayList<E>();
        this.dataCache.addAll(set);
        notifyDataSetChanged();
    }

    /**
     * 添加单条数据，并刷新
     *
     * @param item 单条数据
     * @return void
     */
    public void add(E item) {
        if (this.dataCache == null)
            this.dataCache = new ArrayList<E>();
        this.dataCache.add(item);
        notifyDataSetChanged();
    }

    /**
     * 获取总条数
     *
     * @return int 总数
     */
    public int getCount() {
        return this.dataCache == null ? 0 : this.dataCache.size();
    }

    /**
     * 获取单条数据
     *
     * @return Object 单条数据对象
     */
    public E getItem(int position) {
        if (position >= this.dataCache.size()) {
            position = this.dataCache.size() - 1;
        }
        return this.dataCache == null ? null : this.dataCache.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}