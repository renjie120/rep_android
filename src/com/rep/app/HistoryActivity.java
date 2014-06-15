package com.rep.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;

import com.rep.fragments.HistoryFragment;
import com.rep.fragments.IchartFragment;
import com.rep.fragments.LastWeekFragment;
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
		LastWeekFragment.OnLastweekListener, IchartFragment.OnIchartListener {
	private LinearLayout all;
	/**
	 * 手势对象
	 */
	private MyGestureDetector detector;
	private Bundle b;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_frame);
		all = (LinearLayout) findViewById(R.id.all);
		b = getIntent().getExtras();
		// 设置手势动作.
		detector = new MyGestureDetector(HistoryActivity.this, all);
		detector.setRightFling();// init();
		// 初始化页面的时候，加载Grid布局的碎片.
		if (findViewById(R.id.tab_content) != null) {
			if (savedInstanceState != null) {
				return;
			}
			LastWeekFragment firstFragment = new LastWeekFragment();
			firstFragment.setArguments(b);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.tab_content, firstFragment).commit();
		}
		ActivityMeg.getInstance().addActivity(this);
	}

	/**
	 * 第二种返回屏幕大小的方式.
	 * 
	 * @return
	 */
	public float[] getScreen2() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		return new float[] { dm.widthPixels, dm.heightPixels };
	}

	// 上下标题栏的高度比例

	public static float barH = 0.07f;
	// 首页标题的高度比例
	public static float titleH = 28 / 671f;
	// 标题文字的宽度
	public static float barW = 0.6f;
	// 首页4字标题的宽度
	public static float titleW4 = 70 / 267f;
	public static float titleW6 = 123 / 264f;

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

	@Override
	public void goHistory(String userId) {
		Bundle args = new Bundle();
		args.putString("userId", userId);
		HistoryFragment historyFragment = new HistoryFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 打开页面设置动画.
		transaction.setCustomAnimations(R.anim.slide_left_in,
				R.anim.slide_left_out);
		historyFragment.setArguments(args);
		// 进行碎片替换
		transaction.replace(R.id.tab_content, historyFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}