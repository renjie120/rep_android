package com.rep.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.bean.ZhishiBean;
import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;
import com.rep.util.ZhishiAdapter;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class ZhishiActivity extends BaseActivity {
	@ViewInject(R.id.zhishi_head)
	private ActionBar head;
	private float screenHeight, screenWidth;
	@ViewInject(R.id.zhishiList)
	private ListView zhishi_list;

	private long exitTime = 0;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhishi);
		ViewUtils.inject(this);
		init();
		ActivityMeg.getInstance().addActivity(this);
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.zhishi_title, false, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));

		ArrayList<ZhishiBean> listItem = new ArrayList<ZhishiBean>();
		String[] names = { "2014-5-1", "2014-5-7", "2014-5-14", "2014-5-21" };
		for (String s : names) {
			ZhishiBean m = new ZhishiBean();
			m.setTime(s);
			m.setContent("testtesttesttesttesttesttesttesttestt" +
					"esttesttesttesttesttesttesttesttesttesttesttesttest" +
					"testtesttesttesttesttesttesttesttesttesttesttest");
			listItem.add(m);
		}
		ZhishiAdapter adapter = new ZhishiAdapter(listItem, this);
		zhishi_list.setAdapter(adapter);
		// 去掉分割线。。
		zhishi_list.setDivider(null);
		zhishi_list
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							alert("未开发");
						}
					}
				});
	}
}