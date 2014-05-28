package com.rep.app;

import java.security.NoSuchAlgorithmException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rep.bean.Result;
import com.rep.util.ActionBar;
import com.rep.util.ActivityMeg;
import com.rep.util.HttpRequire;
import com.rep.util.ActionBar.Action;

/**
 * 忘记密码.
 * 
 * @author Administrator
 * 
 */
public class RegiestActivity extends BaseActivity {
	private static final String url = HOST + "/services/userService!regiest.do";
	@ViewInject(R.id.regiest_head)
	private ActionBar head;
	@ViewInject(R.id.pass)
	private EditText pass;
	@ViewInject(R.id.confirm_pass_v)
	private EditText pass_confirm;
	@ViewInject(R.id.code)
	private EditText code;
	@ViewInject(R.id.phone_v)
	private EditText phone; 

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regiest);
		ViewUtils.inject(this);
		init();

	}

	private float screenHeight, screenWidth;
	private static final int DIALOG_KEY = 0;
	private ProgressDialog dialog;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在注册,请稍候");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		}
		return null;
	}

	private void regiest(String phone, String valicode, String ps) {
		try {
			HttpUtils http = new HttpUtils();
			RequestParams p = new RequestParams();
			p.addBodyParameter("userId", phone);
			p.addBodyParameter("password", ps);
			p.addBodyParameter("validCode", valicode);
			String tk = HttpRequire.getMD5(HttpRequire.getBase64(phone));
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
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							removeDialog(DIALOG_KEY);
							System.out.println(responseInfo.result);
							Result r = (Result) JSON.parseObject(
									responseInfo.result, Result.class);
							if (r.getErrorCode() == 0) {
								String _res = r.getData().toString();
								JSONObject obj = JSON.parseObject(_res);
								Intent intent2 = new Intent(RegiestActivity.this,
										AddMoreDataActivity.class);
								intent2.putExtra("phone",
										obj.getString("phone"));
								intent2.putExtra("userId",
										obj.getString("userId")); 
								startActivity(intent2);
							} else {
								alert(r.getErrorMessage());
							}
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							removeDialog(DIALOG_KEY);
						}
					});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		ActivityMeg.getInstance().addActivity(this);
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
				// Intent intent2 = new Intent(RegiestActivity.this,
				// AddMoreDataActivity.class);
				// startActivity(intent2);
				String _p = pass.getText().toString();
				String _p2 = pass_confirm.getText().toString();
				String ph = phone.getText().toString();
				String validCode = code.getText().toString();
				if (!_p.equals(_p2)) {
					alert("确认密码和密码不一致,请重新输入");
				} else {
					//注册
					regiest(ph,validCode,_p);
				}
			}
		});
		head.setLeftAction(new ActionBar.BackAction(this));
	}

	@OnClick({ R.id.getcode })
	public void getCode(View arg0) {

	}
}