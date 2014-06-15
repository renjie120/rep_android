package com.rep.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.rep.fragments.HistoryFragment;
import com.rep.fragments.IchartFragment;
import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;
import com.rep.util.MyGestureDetector;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class HistoryActivity extends FragmentActivity implements
		HistoryFragment.OnHistorySelectedListener,
		IchartFragment.OnIchartListener {
	private ActionBar head;
	private float screenHeight, screenWidth;
	private ListView historyList;
	private long exitTime = 0;
	private LinearLayout all;
	/**
	 * 手势对象
	 */
	private MyGestureDetector detector;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_frame);
		all = (LinearLayout) findViewById(R.id.all);
		// 设置手势动作.
		detector = new MyGestureDetector(HistoryActivity.this, all);
		detector.setRightFling();// init();
		// 初始化页面的时候，加载Grid布局的碎片.
		if (findViewById(R.id.tab_content) != null) {
			if (savedInstanceState != null) {
				return;
			}
			HistoryFragment historyFragment = new HistoryFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.tab_content, historyFragment).commit();
		}
		ActivityMeg.getInstance().addActivity(this);
	}

	// private String userId;
	// private SharedPreferences mSharedPreferences;
	/**
	 * 第二种返回屏幕大小的方式.
	 * 
	 * @return
	 */
	public float[] getScreen2() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		return new float[] { dm.widthPixels, dm.heightPixels };
	}// 上下标题栏的高度比例

	public static float barH = 0.07f;
	// 首页标题的高度比例
	public static float titleH = 28 / 671f;
	// 标题文字的宽度
	public static float barW = 0.6f;
	// 首页4字标题的宽度
	public static float titleW4 = 70 / 267f;
	public static float titleW6 = 123 / 264f;

	/**
	 * 初始化控件.
	 */
	private void init() {
		// head = (ActionBar) findViewById(R.id.history_head);
		// historyList = (ListView) findViewById(R.id.historyList);
		// // 得到屏幕大小.
		// float[] screen2 = getScreen2();
		// screenHeight = screen2[1];
		// screenWidth = screen2[0];
		// Bundle b = getIntent().getExtras();
		// // userId = b.getString("userId");
		// head.init(R.string.history_title, false, false, false, false,
		// (int) (screenHeight * barH));
		// head.setTitleSize((int) (screenWidth * titleW4),
		// (int) (screenHeight * titleH));
		//
		// ArrayList<HashMap<String, Object>> listItem = new
		// ArrayList<HashMap<String, Object>>();
		// String[] names = { "2014-5-1", "2014-5-7", "2014-5-14", "2014-5-21"
		// };
		// for (String s : names) {
		// HashMap<String, Object> m = new HashMap<String, Object>();
		// m.put("indate", s);
		// listItem.add(m);
		// }
		// HistoryAdapter adapter = new HistoryAdapter(listItem, this);
		// historyList.setAdapter(adapter);
		// // 去掉分割线。。
		// historyList.setDivider(null);
		// historyList
		// .setOnItemClickListener(new AdapterView.OnItemClickListener() {
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// {
		// TextView txt = (TextView) arg1
		// .findViewById(R.id.indate);
		// Intent tent = new Intent(HistoryActivity.this,
		// IchartActivity.class);
		// tent.putExtra("inDate", txt.getText().toString());
		// startActivity(tent);
		// }
		// }
		// });

		// Date d = new Date();
		// String today = SaveDataActivity.toDString(d, "yyyy-MM-dd");
		// mSharedPreferences = PreferenceManager
		// .getDefaultSharedPreferences(this);
		// if ("false".equals(mSharedPreferences.getString(today + "," + userId
		// + "_firstOpen", "false"))) {
		// mSharedPreferences.edit().putString(
		// today + "," + userId + "_firstOpen", "true");
		// Intent t = new Intent(HistoryActivity.this,
		// MyViewPagerActivity.class);
		// t.putExtras(b);
		// startActivity(t);
		// }
	}

	@Override
	public void onHistorySelected(String indate) {
		Bundle args = new Bundle();
		args.putString("inDate", indate);
		IchartFragment newFragment = new IchartFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 打开页面设置动画.
		transaction.setCustomAnimations(R.anim.slide_left_in,
				R.anim.slide_left_out);
		newFragment.setArguments(args);
		// 进行碎片替换
		transaction.replace(R.id.tab_content, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void back() {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack();
	}

	@Override
	public void leftBack(MotionEvent event) {
		detector.onTouchEvent(event);
	}
}