package com.rep.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class DeviceDetailFragment extends BaseFragment {
	private ActionBar head;

	private float screenHeight, screenWidth; 
	private TextView deviceDetail;

	private OnDeviceDetailListener listener;

	public interface OnDeviceDetailListener {
		public void back();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.devices_detail, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnDeviceDetailListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDeviceDetailListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	public void init() {
		head = (ActionBar) findViewById(R.id.device_detail_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.device_title, true, false, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 0.6),
				(int) (screenHeight * titleH));
		head.setLeftAction(new AbstractAction(R.drawable.back) {
			@Override
			public void performAction(View view) {
				// 调用父亲acitivty中的回退操作.
				listener.back();
			}
		});
		String str = getArguments().getString("content");
		deviceDetail = (TextView) findViewById(R.id.deviceDetail);
		deviceDetail.setText(str);
	}

}