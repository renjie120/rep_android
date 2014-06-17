package com.rep.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.rep.app.BaseActivity;
import com.rep.app.R;
import com.rep.app.RingView;
import com.rep.bean.Result;
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
		initData();
	}

	private Bundle b;
	private static final int DIALOG_KEY = 0;

	private void showContent(String str) {
		JSONObject statis = JSON.parseObject(str);
		String rank = statis.getString("rank");
		tv1.setText("本次RPI指数是:" + statis.getDoubleValue("rpi"));
		tv2.setText("击败了" + rank + "的店铺");
		ring.setScore(Float.parseFloat(rank.replace("%", "")));
		ring.reset();
		getActivity().removeDialog(DIALOG_KEY);
	}

	/**
	 * 请求服务端数据.
	 */
	private void initData() {
		HttpUtils http = new HttpUtils();
		String userId = b.getString("userId");
		String token = b.getString("token");
		RequestParams p = new RequestParams();
		p.addBodyParameter("userId", userId);
		p.addBodyParameter("token", token);
		http.send(HttpRequest.HttpMethod.POST, url, p,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						getActivity().showDialog(DIALOG_KEY);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// resultText.setText(current + "/" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Result r = (Result) JSON.parseObject(
								responseInfo.result, Result.class);
						if (r.getErrorCode() == 0) {
							String _res = r.getData().toString();
							showContent(_res);
						} else {
							tv1.setText("本次RPI指数是:0");
							tv2.setText("击败了0%的店铺");
							ring.setScore(0);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						getActivity().removeDialog(DIALOG_KEY);
					}
				});

	}

	private static final String url = BaseActivity.HOST
			+ "/services/statisService!getLastData.do";
	private static final String url2 = BaseActivity.HOST
			+ "/services/statisService!goStatis.do";
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
		b = getArguments();
		// ring.reset();
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
		//打开历史界面
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.goHistory(userid);
			}
		});
		//进行手动调试模拟自动计算.
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				HttpUtils http = new HttpUtils();
				String userId = b.getString("userId");
				String token = b.getString("token");
				RequestParams p = new RequestParams();
				p.addBodyParameter("userId", userId);
				p.addBodyParameter("token", token);
				http.send(HttpRequest.HttpMethod.POST, url2, p,
						new RequestCallBack<String>() {
							@Override
							public void onStart() {
								getActivity().showDialog(DIALOG_KEY);
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// resultText.setText(current + "/" + total);
							}

							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								Result r = (Result) JSON.parseObject(
										responseInfo.result, Result.class);
								if (r.getErrorCode() == 0) {
									getActivity().removeDialog(DIALOG_KEY);
								} else {
									alert("请求失败!");
									getActivity().removeDialog(DIALOG_KEY);
								}
							}

							@Override
							public void onFailure(HttpException error, String msg) {
								getActivity().removeDialog(DIALOG_KEY);
							}
						});
			}
		});
	}
}