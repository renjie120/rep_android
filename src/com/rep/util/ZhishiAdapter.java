package com.rep.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.bean.ZhishiBean;

public class ZhishiAdapter extends BaseAdapter {
	private ArrayList<ZhishiBean> data;// 用于接收传递过来的Context对象
	private Context context;

	public ZhishiAdapter(ArrayList<ZhishiBean> data,
			Context context) {
		super();
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (null != data) {
			count = data.size();
		}
		return count;
	}

	@Override
	public ZhishiBean getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.zhishi_item, null);

			viewHolder.content = (TextView) convertView
					.findViewById(R.id.content);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ZhishiBean markerItem = getItem(position);
		if (null != markerItem) {
			viewHolder.time.setText(markerItem.getTime());
			viewHolder.content.setText(markerItem.getContent());
		}
		return convertView;
	}

	public final static class ViewHolder {
		public TextView time;
		public TextView content;
	}

}
