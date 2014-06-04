package com.rep.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.search.MKSearch;
import com.example.jpushdemo.ExampleApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;

/**
 * 我的资料
 * 
 * @author Administrator
 * 
 */
public class MyDataActivity extends BaseActivity {
	private static final String url = HOST + "/services/userService!regiest.do";
	@ViewInject(R.id.addmore_head)
	private ActionBar head;
	@ViewInject(R.id.userid_v)
	private TextView userid_v;
	@ViewInject(R.id.price_v)
	private EditText price;
	@ViewInject(R.id.city_v)
	private TextView city_v;
	@ViewInject(R.id.worktime_v)
	private EditText worktime;
	@ViewInject(R.id.weekend_v)
	private EditText weekend;
	@ViewInject(R.id.worknum_v)
	private EditText worknum;
	@ViewInject(R.id.brandtype_v)
	private EditText brandtype;
	@ViewInject(R.id.brandname_v)
	private EditText brandname;
	private String phone ;
	private ExampleApplication app;
 
	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mydata);
		ViewUtils.inject(this);
		phone = getIntent().getStringExtra("phone");  
		init();
		userid_v.setText(phone);
		ActivityMeg.getInstance().addActivity(this);
	}

	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		// initMap();
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.titile_adddata, true, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setLeftAction(new ActionBar.BackAction(this));

		addCleanBtn(price);
		addCleanBtn(worktime);
		addCleanBtn(weekend);
		addCleanBtn(worknum);
		addCleanBtn(brandtype);
		addCleanBtn(brandname); 
	}

	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	private static final int DIALOG_KEY = 0;
	private ProgressDialog dialog;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在查询,请稍候");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		}
		return null;
	}
 
}