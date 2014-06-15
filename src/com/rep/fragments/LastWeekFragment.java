package com.rep.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.app.RingView;
import com.rep.util.ActionBar;
import com.rep.util.BaseFragment;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class LastWeekFragment extends BaseFragment {
	private ActionBar head;
	private float screenHeight, screenWidth;
	private RingView ring;

	public interface OnLastweekListener {

		public void back();

		public void goHistory(String userid);
	}

	private OnLastweekListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.last_week, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnLastweekListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnLastweekListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private Button btn2, btn1;
	private TextView tv1, tv2;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.history_head);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		ring = (RingView) findViewById(R.id.ring);
		tv1.setText("本次RPI指数是:69");
		tv2.setText("击败了84%的店铺");
		ring.setScore(50);
		ring.reset();
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head = (ActionBar) findViewById(R.id.lastweek_head);
		head.init(R.string.history_title, false, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));

		btn2 = (Button) findViewById(R.id.btn2);
		btn1 = (Button) findViewById(R.id.btn1);

		final String userid = getArguments().getString("phone");
		System.out.println("当前用户是：" + userid);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.goHistory(userid);
			}
		});
	}
}