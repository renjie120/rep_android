package com.rep.app;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.rep.util.ActionBar;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class SaveDataActivity extends BaseActivity {
	private ActionBar head;
	private TextView comeInNum, intreNum, tryNum, buyNum, oldNum, timeSpan,
			indate, indate_day;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.savedata);
		init();

	}

	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.savedata_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.titile_savadata, true, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.finish);
		head.setLeftAction(new ActionBar.BackAction(this));
		comeInNum = (TextView) findViewById(R.id.comein_v);
		intreNum = (TextView) findViewById(R.id.intre_v);
		tryNum = (TextView) findViewById(R.id.try_v);
		buyNum = (TextView) findViewById(R.id.buy_v);
		oldNum = (TextView) findViewById(R.id.old_v);
		timeSpan = (TextView) findViewById(R.id.timespan_v);
		indate = (TextView) findViewById(R.id.indate);
		indate_day = (TextView) findViewById(R.id.indate_day);

	}

}