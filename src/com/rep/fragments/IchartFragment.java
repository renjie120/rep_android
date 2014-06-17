package com.rep.fragments;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
import com.rep.bean.Result;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;

public class IchartFragment extends BaseFragment {
	private static final String url = BaseActivity.HOST
			+ "/services/statisService!getData.do";
	private WebView webView;
	private float screenHeight, screenWidth;
	private ActionBar head;
	private WebSettings settings;
	private Vector<Item> chart = new Vector<Item>();
	private String data;
	private String dataLabels;
	private Handler mHandler = new Handler();
	private String inDate, userId, token;
	private OnIchartListener listener;

	public interface OnIchartListener {
		/**
		 * 页面的向右滑动进行页面的退回.
		 * 
		 * @param event
		 */
		public void leftBack(MotionEvent event);

		public void back();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ichart, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnIchartListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnIchartListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	public void onResume() {
		super.onResume();
		showChart();
	}

	private void showChart() { 
		webView.addJavascriptInterface(new Java2JS(userId, inDate, token),
				"jsjs");
		String url = "file:///android_asset/ichart.html";
		webView.loadUrl(url);
	}

	public void init() {
		inDate = getArguments().getString("inDate");
		userId = getArguments().getString("userId");
		token = getArguments().getString("token");
		head = (ActionBar) findViewById(R.id.ichart_head);

		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init2(inDate + "统计数据", true, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 0.8),
				(int) (screenHeight * titleH));
		head.setLeftAction(new AbstractAction(R.drawable.back) {
			@Override
			public void performAction(View view) {
				// 调用父亲acitivty中的回退操作.
				listener.back();
			}
		});

		webView = (WebView) findViewById(R.id.ichart);
		webView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				listener.leftBack(event);
				return false;
			}
		});
		webView.setHorizontalScrollBarEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
		// webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		settings = webView.getSettings();

		// 设置字符集编码
		settings.setDefaultTextEncodingName("UTF-8");
		// settings.setPluginsEnabled(true);
		// 开启JavaScript支持
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		mHandler = new Handler();

		webView.addJavascriptInterface(this, "someThing");
		webView.setWebChromeClient(new MyWebChromeClient());

	}

	public void setSmething(String some) {
		System.out.println("----------" + some + "---------------");
	}

	final class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			Log.d("debug", message);
			result.confirm();
			return true;
		}
	}

	private static final int DIALOG_KEY = 0;

	private final class Java2JS {
		private double g1;
		private double g2;
		private double g3;
		private double g4;
		private double g5;
		private double g6;
		private double rpi;
		private double per;
		private String indate;
		private String option;
		private String userId;
		private String inDate;
		private String token;

		public Java2JS(String userId, String inDate, String token) {
			this.userId = userId;
			this.inDate = inDate;
			this.token = token;

		}

		private class GetDate implements Callable<String> {
			private CountDownLatch begin;

			public GetDate(CountDownLatch begin) {
				this.begin = begin;
			}

			@Override
			public String call() throws Exception {
				try {
					begin.await();
					return " { 'G1':" + g1 + ", 'G2' :" + g2 + ",'G3' :" + g3
							+ ",'G4' :" + g4 + ", 'G5' :" + g5 + ", 'G6' :"
							+ g6 + ",'rpi':" + rpi + ",'percent':'" + per
							+ "%','indate':'" + indate + "','option':'"
							+ option + "'}";
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				return null;
			}

		}

		public String getString() {
			// 开始的倒数锁.
			ExecutorService exec = Executors.newFixedThreadPool(2);
			final CountDownLatch begin = new CountDownLatch(1);
			Future<String> result = exec.submit(new GetDate(begin));
			HttpUtils http = new HttpUtils();
			RequestParams p = new RequestParams();
			p.addBodyParameter("userId", userId);
			p.addBodyParameter("indate", inDate);
			p.addBodyParameter("token", token);
			try {
				http.send(HttpRequest.HttpMethod.POST, url, p,
						new RequestCallBack<String>() {
							@Override
							public void onStart() {
								getActivity().showDialog(DIALOG_KEY);
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {

							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								Result r = (Result) JSON.parseObject(
										responseInfo.result, Result.class);
								try {
									System.out.println(211);
									Thread.sleep(1000);
									System.out.println(212);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (r.getErrorCode() == 0) {
									JSONObject statis = JSON.parseObject(r
											.getData().toString());
									g1 = statis.getDoubleValue("statis1");
									g2 = statis.getDoubleValue("statis2");
									g3 = statis.getDoubleValue("statis3");
									g4 = statis.getDoubleValue("statis4");
									g5 = statis.getDoubleValue("statis5");
									g6 = statis.getDoubleValue("statis6");
									rpi = statis.getDoubleValue("rpi");
									per = Double.parseDouble(statis.getString(
											"rank").replace("%", ""));
									getActivity().removeDialog(DIALOG_KEY);
									begin.countDown();
								}
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								getActivity().removeDialog(DIALOG_KEY);
							}
						});

				return result.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				exec.shutdown();
			}
			return "";
		}
	}
}