package com.rep.app;

import java.util.Vector;

import android.content.ClipData.Item;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;

public class ItimeActivity extends BaseActivity {
	private WebView webView;
	private float screenHeight, screenWidth;
	private ActionBar head;
	private WebSettings settings;
	private Vector<Item> chart = new Vector<Item>();
	private String data;
	private String dataLabels;
	private Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.itime);
		ActivityMeg.getInstance().addActivity(this);
		head = (ActionBar) findViewById(R.id.ichart_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.app_name, false, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
//		head.setLeftAction(new ActionBar.BackAction(this));

		webView = (WebView) findViewById(R.id.ichart);

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
 
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.addJavascriptInterface(new Java2JS(), "jsjs"); 

		// 加载assets目录下的文件
		String url = "file:///android_asset/time.html";

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
		private double g1=123;
		private double g2=38;
		private double g3=58;
		private double g4=28;
		private double g5=35;
		private double g6=12;
		private double rpi=53;
		private double per=23;
		private String indate="234";
		private String option="3u899reuirieurie";

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
					+ indate + "','option':'"+option+"'}";
			return result; // 在JS中typeof value结果为string
		}

	}
}