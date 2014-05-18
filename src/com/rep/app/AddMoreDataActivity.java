package com.rep.app;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.rep.util.ActionBar;

/**
 * 完善信息.
 * 
 * @author Administrator
 * 
 */
public class AddMoreDataActivity extends BaseActivity {
	private ActionBar head;
	private EditText price, worktime, weekend
	, worknum, brandtype, brandname;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addmoredata);
		init();

	}

	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.addmore_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.titile_adddata, true, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.finish);
		head.setLeftAction(new ActionBar.BackAction(this));
		brandname = (EditText) findViewById(R.id.brandname_v);
		brandtype = (EditText) findViewById(R.id.brandtype_v);
		worknum = (EditText) findViewById(R.id.worknum_v);
		weekend = (EditText) findViewById(R.id.weekend_v);
		worktime = (EditText) findViewById(R.id.worktime_v);
		price = (EditText) findViewById(R.id.price_v);

	}

}