package com.rep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;

/**
 * 忘记密码.
 * 
 * @author Administrator
 * 
 */
public class RegiestActivity extends BaseActivity {
	private ActionBar head;
	private EditText pass, pass_confirm, code, phone;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regiest);
		init();

	}
	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.regiest_head);
		pass = (EditText) findViewById(R.id.pass);
		pass_confirm = (EditText) findViewById(R.id.confirm_pass_v);
		code = (EditText) findViewById(R.id.code);
		phone = (EditText) findViewById(R.id.phone_v);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.titile_regiest, true, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.makesure); 
		head.setRightActionWithText(new Action() {

			@Override
			public int getDrawable() {
				return R.string.makesure;
			}

			@Override
			public void performAction(View view) {
				Intent intent2 = new Intent(RegiestActivity.this,
						AddMoreDataActivity.class);
				startActivity(intent2);
			}
		});
		head.setLeftAction(new ActionBar.BackAction(this));
	}

	public void getCode(View arg0) {
		// new MyListLoader(false).execute("");
		// Intent intent2 = new Intent(LoginActivity.this,
		// SaveDataActivity.class);
		// startActivity(intent2);
	}

}