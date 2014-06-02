package com.rep.app;

import android.os.Bundle;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.util.ActionBar;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class AboutActivity extends BaseActivity {
	@ViewInject(R.id.about_head)
	private ActionBar head;
	private float screenHeight, screenWidth;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
}