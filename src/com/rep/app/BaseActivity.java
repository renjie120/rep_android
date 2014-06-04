package com.rep.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rep.util.TextCleanTouch;
import com.rep.util.TextCleanWatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Activity基类，包含一些常用函数.
 */
public class BaseActivity extends Activity {
	// public final static String HOST="http://192.168.1.101:9999";
	public final static String HOST = "http://www.thinksafari.com";
	// 上下标题栏的高度比例
	public static float barH = 0.07f;
	// 标题文字的宽度
	public static float barW = 0.6f;
	// 底部文字的宽度
	public static float bottomW = 91 / 263f;
	// 底部文字的高度
	public static float bottomH = 40 / 273f;
	// 首页标题的高度比例
	public static float titleH = 28 / 671f;
	// 首页左边按钮的比例
	public static float lftBtnW = 63 / 377f;
	public static float lftBtnH = 27 / 670f;
	public static float lftBtnT = -6 / 695f;
	// 首页右边按钮的比例
	public static float rgtBtnW = 44 / 378f;
	public static float rgtBtnH = 40 / 674f;
	// 首页4字标题的宽度
	public static float titleW4 = 70 / 267f;
	public static float titleW6 = 123 / 264f;

	public static String toDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		return sdf.format(date);
	}

	public static Date getDate(String dateStr) {
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = formatter2.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public void addCleanBtn(EditText et) {
		final Resources res = getResources();
		Drawable mIconSearchClear = res
				.getDrawable(R.drawable.txt_search_clear);
		et.addTextChangedListener(new TextCleanWatcher(et, mIconSearchClear,null));
		et.setOnTouchListener(new TextCleanTouch(et));
	}

	public void setWidthHeight(LinearLayout l, final float width,
			final float height) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight(RelativeLayout l, final float width,
			final float height) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight(RelativeLayout l, final float width,
			final float height, final float top, final float left) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		ll.topMargin = (int) (top * dm.heightPixels);
		ll.leftMargin = (int) (left * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight(ImageView l, final float width,
			final float height) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight(ImageView l, final float width,
			final float height, final float top, final float left) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.leftMargin = (int) (left * dm.widthPixels);
		ll.topMargin = (int) (top * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight2(ImageView l, final float width,
			final float height, final float top, final float left) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.height = (int) (height * dm.heightPixels);
		ll.leftMargin = (int) (left * dm.widthPixels);
		ll.topMargin = (int) (top * dm.heightPixels);
		ll.width = (int) (width * dm.widthPixels);
		l.setLayoutParams(ll);
	}

	public void setWidthHeight(TextView l, final float width, final float height) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		l.setHeight((int) (height * dm.heightPixels));
		l.setWidth((int) (width * dm.widthPixels));
	}

	public void setWidthHeight(TextView l, final float width,
			final float height, final float top, final float left) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		l.setHeight((int) (height * dm.heightPixels));
		l.setWidth((int) (width * dm.widthPixels));
		l.layout((int) (left * dm.widthPixels), (int) (top * dm.heightPixels),
				0, 0);
	}

	/**
	 * 判断网络状况.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		try {
			if (context != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					return mNetworkInfo.isAvailable();
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 弹出一个提示框.
	 * 
	 * @param mess
	 */
	public void alert(String mess) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(mess)
				.setPositiveButton("确定", null).show();
	}

	/**
	 * 返回屏幕大小
	 */
	public int[] getScreen() {
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		return new int[] { screenWidth, screenHeight };
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
}
