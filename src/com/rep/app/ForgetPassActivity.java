package com.rep.app;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;

/**
 * 忘记密码.
 * 
 * @author Administrator
 * 
 */
public class ForgetPassActivity extends BaseActivity {
	private ActionBar head;
	private EditText pass, pass_confirm, code;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgetpass);
		init();
		ActivityMeg.getInstance().addActivity(this);

	}
	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.forgetpass_head);
		pass = (EditText) findViewById(R.id.pass);
		pass_confirm = (EditText) findViewById(R.id.confirm_pass_v);
		code = (EditText) findViewById(R.id.code);
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
		head.setLeftAction(new ActionBar.BackAction(this));
	}

	public void getCode(View arg0) {
		// new MyListLoader(false).execute("");
		// Intent intent2 = new Intent(LoginActivity.this,
		// SaveDataActivity.class);
		// startActivity(intent2);
	}

}