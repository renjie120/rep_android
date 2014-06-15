package com.rep.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rep.fragments.AboutFragment;
import com.rep.fragments.DeviceDetailFragment;
import com.rep.fragments.DeviceFragment;
import com.rep.fragments.MyDataFragment;
import com.rep.util.MyGestureDetector;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class AboutActivity extends FragmentActivity implements
		AboutFragment.OnAboutSelectedListener,
		DeviceFragment.OnDeviceSelectedListener,
		DeviceDetailFragment.OnDeviceDetailListener,
		MyDataFragment.OnMydataListener {

	private float screenHeight, screenWidth;
	private ListView deviceList;
	private TextView deviceDetail;
	/**
	 * 手势对象
	 */
	private MyGestureDetector detector;

	private Bundle b;
	private LinearLayout all;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_frame);
		b = getIntent().getExtras();
		all = (LinearLayout) findViewById(R.id.all);
		// 设置手势动作.
		detector = new MyGestureDetector(AboutActivity.this, all);
		detector.setRightFling();// init();
		// 初始化页面的时候，加载Grid布局的碎片.
		if (findViewById(R.id.tab_content) != null) {
			if (savedInstanceState != null) {
				return;
			}
			AboutFragment aboutFragment = new AboutFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.tab_content, aboutFragment).commit();
		}
	}

	@Override
	public void back() {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack();
	}

	@Override
	public void openDevice() {
		DeviceFragment newFragment = new DeviceFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 打开页面设置动画.
		transaction.setCustomAnimations(R.anim.slide_left_in,
				R.anim.slide_left_out);
		// 进行碎片替换
		transaction.replace(R.id.tab_content, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void leftBack(MotionEvent event) {
		detector.onTouchEvent(event);
	}

	@Override
	public void openWodeziliao() {
		MyDataFragment newFragment = new MyDataFragment();
		newFragment.setArguments(b);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 打开页面设置动画.
		transaction.setCustomAnimations(R.anim.slide_left_in,
				R.anim.slide_left_out);
		// 进行碎片替换
		transaction.replace(R.id.tab_content, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void openHuizhen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openZaixian() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openDeviceDetail(String deviceName) {
		Bundle args = new Bundle();
		// 根据：deviceName进行查询出来假数据：deviceName
		args.putString(
				"content",
				"红外线的特征：红外传输是一种点对点的传输方式，无线，不能离的太远，要对准方向，且中间不能有障碍物也就是不能穿墙而过，几乎无法控制信息传输的进度；IrDA已经是一套标准，IR收/发的组件也是标准化产品。");
		DeviceDetailFragment deviceDetailFragment = new DeviceDetailFragment();
		deviceDetailFragment.setArguments(args);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 打开页面设置动画.
		transaction.setCustomAnimations(R.anim.slide_left_in,
				R.anim.slide_left_out);
		// 进行碎片替换
		transaction.replace(R.id.tab_content, deviceDetailFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}