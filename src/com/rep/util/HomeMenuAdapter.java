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

public class HomeMenuAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> data;// 用于接收传递过来的Context对象
	private Context context;

	public HomeMenuAdapter(ArrayList<HashMap<String, Object>> data,
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
	public HashMap<String, Object> getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.homemenu_item, null);

			viewHolder.menuName = (TextView) convertView
					.findViewById(R.id.menuName);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		HashMap<String, Object> markerItem = getItem(position);
		if (null != markerItem) { 
			viewHolder.menuName.setText("" + markerItem.get("menuName")); 
		}
		return convertView;
	}

	public final static class ViewHolder {
		public TextView menuName;
	}

}
