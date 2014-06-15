package com.rep.fragments;

import java.util.Vector;

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
import android.widget.LinearLayout;

import com.rep.app.R;
import com.rep.app.R.drawable;
import com.rep.app.R.id;
import com.rep.app.R.layout;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;

public class IchartFragment extends BaseFragment {
	private WebView webView;
	private float screenHeight, screenWidth;
	private ActionBar head; 
	private WebSettings settings;
	private Vector<Item> chart = new Vector<Item>();
	private String data;
	private String dataLabels;
	private Handler mHandler = new Handler();
	private String inDate;
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

	public void init() {
		inDate = getArguments().getString("inDate");
		// ActivityMeg.getInstance().addActivity(this);
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
		// webView.addJavascriptInterface(new Object() {
		//
		// public void clickOnAndroid() {
		//
		// mHandler.post(new Runnable() {
		//
		// public void run() {
		//
		// webView.loadUrl("javascript:alert(12345)");
		//
		// }
		//
		// });
		//
		// }
		//
		// }, "demo");
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.addJavascriptInterface(new Java2JS(), "jsjs");
		// 下面的java方法，可以用于在html中通过：window.demo.clickOnAndroid(23);调用
		// webView.addJavascriptInterface(new Object() {
		// public void clickOnAndroid(final int i) {
		// mHandler.post(new Runnable() {
		// public void run() {
		// int j = i;
		// j++;
		// Toast.makeText(IchartActivity.this,
		// "测试调用java" + String.valueOf(j),
		// Toast.LENGTH_LONG).show();
		// }
		//
		// });
		//
		// }
		//
		// }, "demo");

		// 加载assets目录下的文件
		String url = "file:///android_asset/ichart.html";

		webView.loadUrl(url);
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

	private final class Java2JS {
		private double g1 = 123;
		private double g2 = 38;
		private double g3 = 58;
		private double g4 = 28;
		private double g5 = 35;
		private double g6 = 12;
		private double rpi = 53;
		private double per = 23;
		private String indate = "234";
		private String option = "3u899reuirieurie";

		public double getG1() {
			return g1;
		}

		public void setG1(double g1) {
			this.g1 = g1;
		}

		public double getG2() {
			return g2;
		}

		public void setG2(double g2) {
			this.g2 = g2;
		}

		public double getG3() {
			return g3;
		}

		public void setG3(double g3) {
			this.g3 = g3;
		}

		public double getG4() {
			return g4;
		}

		public void setG4(double g4) {
			this.g4 = g4;
		}

		public double getG5() {
			return g5;
		}

		public void setG5(double g5) {
			this.g5 = g5;
		}

		public double getG6() {
			return g6;
		}

		public void setG6(double g6) {
			this.g6 = g6;
		}

		public double getRpi() {
			return rpi;
		}

		public void setRpi(double rpi) {
			this.rpi = rpi;
		}

		public double getPer() {
			return per;
		}

		public void setPer(double per) {
			this.per = per;
		}

		public String getIndate() {
			return indate;
		}

		public void setIndate(String indate) {
			this.indate = indate;
		}

		public Java2JS() {

		}

		public String getString() {
			String result = " { 'G1':" + g1 + ", 'G2' :" + g2 + ",'G3' :" + g3
					+ ",'G4' :" + g4 + ", 'G5' :" + g5 + ", 'G6' :" + g6
					+ ",'rpi':" + rpi + ",'percent':'" + per + "%','indate':'"
					+ indate + "','option':'" + option + "'}";
			return result; // 在JS中typeof value结果为string
		}

	}
}