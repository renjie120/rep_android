package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;
import com.rep.util.HistoryAdapter;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class HistoryActivity extends BaseActivity {
	private ActionBar head;
	private float screenHeight, screenWidth;
	private ListView historyList;
	private long exitTime = 0;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history);
		init();
		ActivityMeg.getInstance().addActivity(this);
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.history_head);
		historyList = (ListView) findViewById(R.id.historyList);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.app_name, true, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setLeftAction(new ActionBar.BackAction(this));
		
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		String[] names = { "2014-5-1", "2014-5-7", "2014-5-14", "2014-5-21" };
		for (String s : names) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("indate", s);
			listItem.add(m);
		}
		HistoryAdapter adapter = new HistoryAdapter(listItem, this);
		historyList.setAdapter(adapter);
		// 去掉分割线。。
		historyList.setDivider(null);
		historyList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							alert("未开发"); 
						}
					}
				});
	}
}