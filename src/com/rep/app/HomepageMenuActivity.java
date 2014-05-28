package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rep.chart.SalesStackedBarChart;
import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;
import com.rep.util.HomeMenuAdapter;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class HomepageMenuActivity extends BaseActivity {
	private ActionBar head;
	private float screenHeight, screenWidth;
	private ListView menuList;
	private long exitTime = 0;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_menu);
		init();
		ActivityMeg.getInstance().addActivity(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.savedata_head);
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
		String[] names = { "百度地图", "原生chart", "提醒配置", "新首页", "知识分享", "我的资料",
				"设备简介" };
		for (String s : names) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("menuName", s);
			listItem.add(m);
		}
		HomeMenuAdapter adapter = new HomeMenuAdapter(listItem, this);
		menuList = (ListView) findViewById(R.id.menuList);
		menuList.setAdapter(adapter);
		// 去掉分割线。。
		menuList.setDivider(null);
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent intent2 = new Intent(HomepageMenuActivity.this,
							BaseMapDemo.class);
					startActivity(intent2);
				} else if (arg2 == 1) {
					Intent intent2 = new SalesStackedBarChart()
							.execute(HomepageMenuActivity.this);
					startActivity(intent2);
				} else if (arg2 == 2) {
					Intent intent2 = new Intent(HomepageMenuActivity.this,
							ConfigActivity.class);
					startActivity(intent2);
				} else if (arg2 == 3) {
					Intent intent2 = new Intent(HomepageMenuActivity.this,
							NewHomePage.class);
					startActivity(intent2);
				}  else if (arg2 ==4) {
					Intent intent2 = new Intent(HomepageMenuActivity.this,
							HistoryActivity.class);
					startActivity(intent2);
				} else {
					alert("未开发");
				}
			}
		});
	}
}