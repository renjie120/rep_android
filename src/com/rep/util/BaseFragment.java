package com.rep.util;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;

public class BaseFragment extends Fragment {
	// 上下标题栏的高度比例
	public static float barH = 0.07f;
	// 首页标题的高度比例
	public static float titleH = 28 / 671f;
	// 标题文字的宽度
	public static float barW = 0.6f;
	// 首页4字标题的宽度
	public static float titleW4 = 70 / 267f;
	public static float titleW6 = 123 / 264f;

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

	public void alert(String mess) {
		new AlertDialog.Builder(this.getActivity()).setTitle("提示")
				.setMessage(mess).setPositiveButton("确定", null).show();
	}

	public View findViewById(int id) {
		return getView().findViewById(id);
	}

	public Context getApplicationContext() {
		return this.getActivity().getApplicationContext();
	}
}
