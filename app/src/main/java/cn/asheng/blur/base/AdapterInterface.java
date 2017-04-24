package cn.asheng.blur.base;

import android.view.View;
import android.view.ViewGroup;

public interface AdapterInterface {
	public abstract void init();
	public abstract View getView(int position, View convertView, ViewGroup parent);
}
