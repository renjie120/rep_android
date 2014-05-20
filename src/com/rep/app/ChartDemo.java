package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rep.chart.CombinedTemperatureChart;
import com.rep.chart.IDemoChart;

/**
 * 图表插件.
 * @author Administrator
 *
 */
public class ChartDemo extends ListActivity {
	private IDemoChart[] mCharts = new IDemoChart[] { new CombinedTemperatureChart() };

	private String[] mMenuText;

	private String[] mMenuSummary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int length = mCharts.length;
		mMenuText = new String[length + 3];
		mMenuSummary = new String[length + 3];
		mMenuText[0] = "Embedded line chart demo";
		mMenuSummary[0] = "A demo on how to include a clickable line chart into a graphical activity";
		mMenuText[1] = "Embedded pie chart demo";
		mMenuSummary[1] = "A demo on how to include a clickable pie chart into a graphical activity";
		for (int i = 0; i < length; i++) {
			mMenuText[i + 2] = mCharts[i].getName();
			mMenuSummary[i + 2] = mCharts[i].getDesc();
		}
		mMenuText[length + 2] = "Random values charts";
		mMenuSummary[length + 2] = "Chart demos using randomly generated values";
		setListAdapter(new SimpleAdapter(this, getListValues(),
				android.R.layout.simple_list_item_2, new String[] {
						IDemoChart.NAME, IDemoChart.DESC }, new int[] {
						android.R.id.text1, android.R.id.text2 }));
	}

	private List<Map<String, String>> getListValues() {
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		int length = mMenuText.length;
		for (int i = 0; i < length; i++) {
			Map<String, String> v = new HashMap<String, String>();
			v.put(IDemoChart.NAME, mMenuText[i]);
			v.put(IDemoChart.DESC, mMenuSummary[i]);
			values.add(v);
		}
		return values;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = null;
//		if (position == 0) {
//			intent = new Intent(this, XYChartBuilder.class);
//		} else if (position == 1) {
//			intent = new Intent(this, PieChartBuilder.class);
//		} else if (position <= mCharts.length + 1) {
			intent = mCharts[0].execute(this);
//		} else {
//			intent = new Intent(this, GeneratedChartDemo.class);
//		}
		startActivity(intent);
	}
}