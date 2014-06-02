package com.rep.util;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rep.app.R;

public class TimeSpanTipAdapter extends BaseAdapter {
	private ArrayList<Map<String, String>> data;// 用于接收传递过来的Context对象
	private Context context;

	public TimeSpanTipAdapter(ArrayList<Map<String, String>> data,
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
	public Map<String, String> getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.everyday_tips_item, null);

			viewHolder.timeSpan = (TextView) convertView
					.findViewById(R.id.time_span);
			viewHolder.wujiaoxing = (ImageView) convertView
					.findViewById(R.id.wujiaoxing);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Map<String, String> markerItem = getItem(position);
		if (null != markerItem) {
			viewHolder.timeSpan.setText("" + markerItem.get("timeSpan"));
			if ("true".equals(markerItem.get("tixing")))
				viewHolder.wujiaoxing.setVisibility(View.VISIBLE);
			else
				viewHolder.wujiaoxing.setVisibility(View.GONE);

			if ("true".equals(markerItem.get("haveAdded"))) {
				viewHolder.status.setText("已填报");
				viewHolder.status.setTextColor(convertView.getResources()
						.getColor(R.color.orange));
			} else {
				viewHolder.status.setText("未填报");
				viewHolder.status.setTextColor(convertView.getResources()
						.getColor(R.color.black));
			}
		}
		return convertView;
	}

	public final static class ViewHolder {
		public TextView timeSpan;
		public ImageView wujiaoxing;
		public TextView status;
	}

}
