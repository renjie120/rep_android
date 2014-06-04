package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class AboutActivity extends BaseActivity {
	@ViewInject(R.id.about_head)
	private ActionBar head;
	@ViewInject(R.id.r1)
	private LinearLayout r1;
	@ViewInject(R.id.r2)
	private LinearLayout r2;
	@ViewInject(R.id.r3)
	private LinearLayout r3;
	@ViewInject(R.id.r4)
	private LinearLayout r4;
	private float screenHeight, screenWidth;
	private ListView deviceList;
	private TextView deviceDetail;

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

	@OnClick({ R.id.r1 })
	public void openShebei(View v) {
		setContentView(R.layout.devices);
		head = (ActionBar) findViewById(R.id.device_head);
		head.init(R.string.device_title, true, false, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 0.6),
				(int) (screenHeight * titleH));
		head.setLeftAction(new AbstractAction(R.drawable.back) {
			@Override
			public void performAction(View view) {
				initPage();
			}
		});
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.device_item, new String[] { "device" },
				new int[] { R.id.device });
		deviceList = (ListView) findViewById(R.id.deviceList);
		deviceList.setAdapter(adapter);
		// 去掉分割线。。
		deviceList.setDivider(null);
		deviceList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							openDetail("红外线的特征：红外传输是一种点对点的传输方式，无线，不能离的太远，要对准方向，且中间不能有障碍物也就是不能穿墙而过，几乎无法控制信息传输的进度；IrDA已经是一套标准，IR收/发的组件也是标准化产品。");
						}
					}
				});
	}

	public void onResume() {
		super.onResume();
		System.out.println("onResume");
		initPage();
	}

	public void openDetail(String str) {
		setContentView(R.layout.devices_detail);
		head = (ActionBar) findViewById(R.id.device_detail_head);
		head.init(R.string.device_title, true, false, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 0.6),
				(int) (screenHeight * titleH));
		head.setLeftAction(new AbstractAction(R.drawable.back) {
			@Override
			public void performAction(View view) {
				openShebei(null);
			}
		});
		deviceDetail = (TextView) findViewById(R.id.deviceDetail);
		deviceDetail.setText(str);
	}

	@OnClick({ R.id.r2 })
	public void openZaixian(View v) {

	}

	@OnClick({ R.id.r3 })
	public void openHuizhen(View v) {

	}

	@OnClick({ R.id.r4 })
	public void openWodeziliao(View v) {

	}

	private Bundle b;

	private void initPage() {
		setContentView(R.layout.about);
		ViewUtils.inject(this);
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.about_title, false, false, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
	}

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.out.println("onCreate");
		b = getIntent().getExtras();
		initPage();
	}
}