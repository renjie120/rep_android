package com.rep.app;

import java.security.NoSuchAlgorithmException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.bean.Result;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;
import com.rep.util.ActivityMeg;
import com.rep.util.HttpRequire;

/**
 * 首页登录界面.
 * 
 * @author 130126
 * 
 */
public class LoginActivity extends BaseActivity {
	private static final String url = HOST + "/services/userService!login.do";
	private Button buttonLogin;
	private ImageView remeberPassword;
	private static final int DIALOG_KEY = 0;
	private EditText nameText;  
	@ViewInject(R.id.debug)
	private TextView debug; 
	private EditText passwordText;
	private SharedPreferences mSharedPreferences;
	private ProgressDialog dialog;
	private TextView remember_mess;
	private float screenHeight = 0;
	private float screenWidth = 0;
	// private LinearLayout titile_gre_ym;
	// 登陆框的宽度
	private float tabW = 0.86f;
	// 图标的上下空白
	private float imgMrg = 0.05f;
	private TextView name_title, mess_title;
	private TextView pass_title;
	// 登陆框提示文本的宽度.
	private float textViewW = 57 / 265f;
	private float textEditW = 150 / 265f;
	private float textViewH = 27 / 471f;
	private float checkboxH = 15 / 471f;
	private float checkboxTM = 10 / 471f;
	private float checkboxMesTM = 4 / 471f;
	private float checkboxLM = 8 / 170f;
	private float mestitleLM = 4 / 170f;

	/**
	 * 屏幕适配.
	 */
	private void adjustScreen() {
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		int textHeight = (int) (textViewH * screenHeight);
		int checkboxHeight = (int) (checkboxH * screenHeight);
		int checkboxLMar = (int) (checkboxLM * screenWidth);
		int checkboxTMar = (int) (checkboxTM * screenWidth);
		int textWidth = (int) (textViewW * screenWidth);
		head.init(R.string.titile_login, false, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.regiest);
		head.setRightActionWithText(new Action() {

			@Override
			public int getDrawable() {
				return R.string.regiest;
			}

			@Override
			public void performAction(View view) {
				Intent intent2 = new Intent(LoginActivity.this,
						RegiestActivity.class);
				startActivity(intent2);
			}
		});
		name_title.setWidth(textWidth);
		name_title.setHeight(textHeight);
		pass_title.setWidth(textWidth);
		pass_title.setHeight(textHeight);
		LinearLayout.LayoutParams lp_check = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp_check.width = checkboxHeight;
		lp_check.height = checkboxHeight;
		lp_check.leftMargin = checkboxLMar;
		lp_check.topMargin = checkboxTMar;
		remeberPassword.setLayoutParams(lp_check);
		LinearLayout.LayoutParams lp_bottom = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp_bottom.width = (int) (screenWidth * tabW);
		lp_bottom.height = (int) (screenHeight * (24 / 1136));

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, (int) (screenHeight * imgMrg), 0,
				(int) (screenHeight * imgMrg));

		LinearLayout.LayoutParams lp22 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp22.width = (int) (checkboxHeight * 3.3);
		lp22.topMargin = (int) (checkboxMesTM * screenHeight);
		lp22.leftMargin = (int) (mestitleLM * screenWidth);
		remember_mess.setLayoutParams(lp22);

		LinearLayout.LayoutParams lp23 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp23.width = (int) (textEditW * screenWidth);
		lp23.height = (int) (checkboxHeight * 2.2);
		lp23.topMargin = (int) (checkboxMesTM * screenHeight);
		mess_title.setLayoutParams(lp23); 
	}

	private ActionBar head;

	/**
	 * 初始化控件.
	 */
	private void init() {
		ActivityMeg.getInstance().addActivity(this);
		head = (ActionBar) findViewById(R.id.login_head);
		name_title = (TextView) findViewById(R.id.name_title);
		pass_title = (TextView) findViewById(R.id.pass_title);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		mess_title = (TextView) findViewById(R.id.mess_title);
		remeberPassword = (ImageView) findViewById(R.id.remember_check);
		nameText = (EditText) findViewById(R.id.inputName);
		passwordText = (EditText) findViewById(R.id.inputPass);
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		remember_mess = (TextView) findViewById(R.id.remember_mess);
		mess_title = (TextView) findViewById(R.id.mess_title);

		addCleanBtn(passwordText);
		addCleanBtn(nameText);
		adjustScreen();

		if (DEBUG) {
			debug.setVisibility(View.VISIBLE);
			debug.setText("当前版本是演示版本.");
		}
	}

	/**
	 * 绑定事件.
	 */
	private void prepareListener() {
		// 勾选是否记住密码调用.
		remeberPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean haveSelected = !"true".equals(arg0.getTag());
				savePass(haveSelected);
			}

		});
		remember_mess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean haveSelected = !"true".equals(remeberPassword.getTag());
				savePass(haveSelected);
			}

		});
	}

	/**
	 * 登陆控制.
	 */
	private void autoLogin() {
		// 网络不通
		if (!isNetworkConnected(this)) {
			myHandler.sendEmptyMessage(2);
		} else {
			// 如果选择了记住密码，就自动登陆.
			if ("true".equals(mSharedPreferences.getString("remeber", "false"))) {
				String _u = mSharedPreferences.getString("userId", "");
				String _p = mSharedPreferences.getString("pass", "");
				nameText.setText(_u);
				passwordText.setText(_p);
				remeberPassword.setSelected(true);
				remeberPassword.setTag("true"); 
				// new MyListLoader(false).execute("");
			} else {
				remeberPassword.setSelected(false);
				remeberPassword.setTag("false");
			}
		}
	}

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		ViewUtils.inject(this);
		init();

		prepareListener();

		autoLogin();
		
		Drawable mIconSearchClear = getResources()
				.getDrawable(R.drawable.txt_search_clear);
		// 如果有用户名，就显示出来清除按钮.
		if (!"".equals(nameText.getText().toString())) {
			nameText.setCompoundDrawablesWithIntrinsicBounds(null, null,
					mIconSearchClear, null);
		}
		if (!"".equals(passwordText.getText().toString())) {
			passwordText.setCompoundDrawablesWithIntrinsicBounds(null, null,
					mIconSearchClear, null);
		}
	}

	/**
	 * 勾选了记住密码的处理.
	 * 
	 * @param arg1
	 */
	private void savePass(boolean arg1) {
		if (arg1) {
			SharedPreferences.Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("remeber", "true");
			mEditor.putString("pass", passwordText.getText().toString());
			mEditor.putString("userId", nameText.getText().toString());
			mEditor.commit();
			remeberPassword.setSelected(true);
			remeberPassword.setTag("true");
		} else {
			SharedPreferences.Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("remeber", "false");
			mEditor.putString("pass", "");
			mEditor.putString("userId", "");
			mEditor.commit();
			remeberPassword.setSelected(false);
			remeberPassword.setTag("false");
		}
	}

	private void login(String uid, String pass) { 
		if (DEBUG) {
			Intent intent2 = new Intent(LoginActivity.this, NewHomePage.class);
			intent2.putExtra("phone", "18616818351");
			intent2.putExtra("userId", "123");
			String tk;
			try {
				tk = HttpRequire.getMD5(HttpRequire.getBase64("123"));
				intent2.putExtra("brandName", "李宁");
				intent2.putExtra("brandType", "运动鞋");
				intent2.putExtra("token", tk);
				intent2.putExtra("location", "12.23");
				intent2.putExtra("worktime", "10");
				intent2.putExtra("weekendNum", "50");
				startActivity(intent2);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				HttpUtils http = new HttpUtils();
				RequestParams p = new RequestParams();
				p.addBodyParameter("userId", uid);
				p.addBodyParameter("password", pass);
				String tk = HttpRequire.getMD5(HttpRequire.getBase64(uid));
				p.addBodyParameter("token", tk);
				System.out.println("token----" + tk);
				http.send(HttpRequest.HttpMethod.POST, url, p,
						new RequestCallBack<String>() {
							@Override
							public void onStart() {
								showDialog(DIALOG_KEY);
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// resultText.setText(current + "/" + total);
							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								removeDialog(DIALOG_KEY);
								System.out.println(responseInfo.result);
								Result r = (Result) JSON.parseObject(
										responseInfo.result, Result.class);
								if (r.getErrorCode() == 0) {
									String _res = r.getData().toString();
									JSONObject obj = JSON.parseObject(_res);
									Intent intent2 = new Intent(
											LoginActivity.this,
											NewHomePage.class);
									intent2.putExtra("phone",
											obj.getString("phone"));
									intent2.putExtra("city",
											obj.getString("city"));
									intent2.putExtra("price",
											obj.getString("price"));
									intent2.putExtra("userId",
											obj.getString("userId"));
									intent2.putExtra("brandName",
											obj.getString("brandName"));
									intent2.putExtra("brandType",
											obj.getString("brandType"));
									intent2.putExtra("token",
											obj.getString("token"));
									intent2.putExtra("location",
											obj.getString("location"));
									intent2.putExtra("worktime",
											obj.getString("worktime"));
									intent2.putExtra("weekendNum",
											obj.getString("weekendNum"));
									intent2.putExtra("workNum",
											obj.getString("workNum"));
									startActivity(intent2);
								} else {
									alert(r.getErrorMessage());
								}
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								removeDialog(DIALOG_KEY);
							}
						});
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} 
		}
	}

	/**
	 * 登录.
	 * 
	 * @param arg0
	 */
	public void login(View arg0) {
		String name = nameText.getText().toString();
		String pass = passwordText.getText().toString();
		login(name, pass);
	}

	/**
	 * 忘记密码.
	 * 
	 * @param arg0
	 */
	public void forgetPass(View arg0) {
		Intent intent2 = new Intent(LoginActivity.this,
				ForgetPassActivity.class);
		startActivity(intent2);
	}

	/**
	 * 忘记密码.
	 * 
	 * @param arg0
	 */
	public void regiest(View arg0) {
		Intent intent2 = new Intent(LoginActivity.this,
				ForgetPassActivity.class);
		startActivity(intent2);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在登陆,请稍候");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		}
		return null;
	}

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mess_title.setVisibility(View.VISIBLE);
				mess_title.setText("您输入的账号或密码有误,请重新输入!");
				break;
			case 2:
				mess_title.setVisibility(View.VISIBLE);
				mess_title.setText("请检查网络连接状况!");
				break;
			// 跳转到活动列表页面.
			case 3:
				break;
			case 6:
				mess_title.setVisibility(View.VISIBLE);
				mess_title.setText("您输入的账号或密码有误,请重新输入!");
				break;
			default:
				super.hasMessages(msg.what);
				break;
			}
		}
	};

}