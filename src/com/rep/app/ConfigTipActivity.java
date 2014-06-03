package com.rep.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;
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
	private SharedPreferences mSharedPreferences;
	private String checkedWay = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.config_tips);
		ViewUtils.inject(this);
		float[] screen2 = getScreen2();
		String user = getIntent().getStringExtra("userId");
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		checkedWay = mSharedPreferences.getString(user + "_tipway", "不提醒");
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.config_tip2, true, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 1),
				(int) (screenHeight * titleH));
		head.setLeftAction(new ActionBar.BackAction(this));
		head.setRightText(R.string.makesure);
		head.setRightActionWithText(new Action() {

			@Override
			public int getDrawable() {
				return R.string.makesure;
			}

			@Override
			public void performAction(View view) {
				Toast.makeText(ConfigTipActivity.this, "保存成功",
						Toast.LENGTH_LONG).show();
			}
		});
		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 3; i++) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("way", ways[i]);
			if (checkedWay.equals(ways[i]))
				m.put("checked", "true");
			else
				m.put("checked", "false");
			list.add(m);
		}
		final ConfigTipAdapter adapter = new ConfigTipAdapter(list, this);
		tip_way.setDivider(null);
		tip_way.setAdapter(adapter);
		tip_way.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView way = (TextView) arg1.findViewById(R.id.way);
				String currentWay = way.getText().toString();
				for (HashMap<String, Object> m : list) {
					if (currentWay.equals(m.get("way"))) {
						m.put("checked", "true");
					} else {
						m.put("checked", "false");
					}
				}
				ConfigTipAdapter adapter = new ConfigTipAdapter(list,
						ConfigTipActivity.this);
				// 重新设置数据
				tip_way.setAdapter(adapter);
			}
		});
	}
};