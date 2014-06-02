package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.util.ActionBar;
import com.rep.util.ConfigTipAdapter;

/**
 * 一周指标检测提醒界面
 * 
 */
public class ConfigTipActivity extends BaseActivity {

	@ViewInject(R.id.config_tip_head)
	private ActionBar head;
	private float screenHeight, screenWidth;

	@ViewInject(R.id.tip_way)
	private ListView tip_way;

	private String[] ways = new String[] { "每天提醒", "指定日期定时提醒", "不提醒" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.config_tips);
		ViewUtils.inject(this);
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.config_tip2, false, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 1),
				(int) (screenHeight * titleH));
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 3; i++) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("way", ways[i]);
			m.put("checked", "true");
			list.add(m);
		}
		ConfigTipAdapter adapter = new ConfigTipAdapter(list, this);
		tip_way.setAdapter(adapter);
	}
}