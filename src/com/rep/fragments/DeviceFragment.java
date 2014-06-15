package com.rep.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.app.R.drawable;
import com.rep.app.R.id;
import com.rep.app.R.layout;
import com.rep.app.R.string;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class DeviceFragment extends BaseFragment {
	private ActionBar head;
	private LinearLayout r1;
	private LinearLayout r2;
	private LinearLayout r3;
	private LinearLayout r4;
	private float screenHeight, screenWidth;
	private ListView deviceList;
	private TextView deviceDetail;

	private OnDeviceSelectedListener listener;

	public interface OnDeviceSelectedListener {
		public void back();

		/**
		 * 页面的向右滑动进行页面的退回.
		 * 
		 * @param event
		 */
		public void leftBack(MotionEvent event);

		public void openDeviceDetail(String deviceName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.devices, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnDeviceSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDeviceSelectedListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("device", "红外设备");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("device", "蓝牙设备");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("device", "有线设备");
		list.add(map);
		return list;
	}

	public void init() {
		head = (ActionBar) findViewById(R.id.device_head);
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

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.device_item, new String[] { "device" },
				new int[] { R.id.device });
		deviceList = (ListView) findViewById(R.id.deviceList);
		deviceList.setAdapter(adapter);
		// 去掉分割线。。
		deviceList.setDivider(null);
		deviceList.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				listener.leftBack(event);
				return false;
			}
		});
		deviceList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							listener.openDeviceDetail("test");
							// openDetail("红外线的特征：红外传输是一种点对点的传输方式，无线，不能离的太远，要对准方向，且中间不能有障碍物也就是不能穿墙而过，几乎无法控制信息传输的进度；IrDA已经是一套标准，IR收/发的组件也是标准化产品。");
						}
					}
				});
	}

	private Bundle b;

}