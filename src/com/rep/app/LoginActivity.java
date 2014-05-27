package com.rep.app;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.jpushdemo.ExampleApplication;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;
import com.rep.util.ActivityMeg;
import com.rep.util.Constant;
import com.rep.util.HttpRequire;
import com.rep.util.ServerResult;

/**
 * 首页登录界面.
 * 
 * @author 130126
 * 
 */
public class LoginActivity extends BaseActivity {
	private String name;
	private String pass;
	private Button buttonLogin;
	private ImageView remeberPassword;
	private static final int DIALOG_KEY = 0;
	private EditText nameText;
	private EditText passwordText;
	private SharedPreferences mSharedPreferences;
	private ProgressDialog dialog;
	private TextView remember_mess;
	private float screenHeight = 0;
	private float screenWidth = 0;
	// private LinearLayout titile_gre_ym;
	// 登陆框的高度
	private float tabH = 0.38f;
	// 登陆框的宽度
	private float tabW = 0.86f;
	// 图标的上下空白
	private float imgMrg = 0.05f;
	private TextView name_title, mess_title;
	private TextView pass_title;
	private LinearLayout buttonWrap;
	// 登陆框提示文本的宽度.
	private float textViewW = 57 / 265f;
	private float textEditW = 150 / 265f;
	private float textViewH = 27 / 471f;
	private float checkboxH = 15 / 471f;
	private float checkboxTM = 10 / 471f;
	private float checkboxMesTM = 4 / 471f;
	private float checkboxLM = 8 / 170f;
	private float mestitleLM = 4 / 170f;
	private float btnW = 75 / 268f;
	private float wrapH = 30 / 471f;
	private float rowH = 32 / 469f;

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

		// LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT);
		// lp2.width = (int) (btnW * screenWidth);
		// lp2.height = (int) (wrapH * screenHeight);
		// buttonWrap.setLayoutParams(lp2);

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
		passwordText.setText("123");
		// nameText.setText("taobaotmall");
		// passwordText.setText("123123");
	}

	private ActionBar head;

	private void initMap() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				// if (error != 0) {
				// String str = String.format("错误号：%d", error);
				// Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG)
				// .show();
				// return;
				// }
				// StringBuffer sb = new StringBuffer();
				// // 经纬度所对应的位置
				// sb.append(res.strAddr).append("/n");
				//
				// // 判断该地址附近是否有POI（Point of Interest,即兴趣点）
				// if (null != res.poiList) {
				// // 遍历所有的兴趣点信息
				// for (MKPoiInfo poiInfo : res.poiList) {
				// sb.append("----------------------------------------")
				// .append("/n");
				// sb.append("城市：").append(poiInfo.city).append("/n");
				// sb.append("名称：").append(poiInfo.name).append("/n");
				// sb.append("地址：").append(poiInfo.address).append("/n");
				// sb.append("经度：")
				// .append(poiInfo.pt.getLongitudeE6() / 1000000.0f)
				// .append("/n");
				// sb.append("纬度：")
				// .append(poiInfo.pt.getLatitudeE6() / 1000000.0f)
				// .append("/n");
				// sb.append("电话：").append(poiInfo.phoneNum).append("/n");
				// sb.append("邮编：").append(poiInfo.postCode).append("/n");
				// // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
				// sb.append("类型：").append(poiInfo.ePoiType).append("/n");
				// }
				// }
				// alert("搜索到的结果:"+sb.toString());
				if (res == null) {
					return;
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					// 反地理编码：通过坐标点检索详细地址及周边poi
					String strInfo = res.strAddr; 
					System.out.println(strInfo + "---查询结果"+",,"+JSON.toJSONString(res));
					Toast.makeText(LoginActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}

				// StringBuffer sb = new StringBuffer();
				// // 经纬度所对应的位置
				// sb.append(result.strAddr).append("/n");
				//
				// // 判断该地址附近是否有POI（Point of Interest,即兴趣点）
				// if (null != result.poiList) {
				// // 遍历所有的兴趣点信息
				// for (MKPoiInfo poiInfo : result.poiList) {
				// sb.append("----------------------------------------")
				// .append("/n");
				// sb.append("名称：").append(poiInfo.name).append("/n");
				// sb.append("地址：").append(poiInfo.address).append("/n");
				// sb.append("经度：")
				// .append(poiInfo.pt.getLongitudeE6() / 1000000.0f)
				// .append("/n");
				// sb.append("纬度：")
				// .append(poiInfo.pt.getLatitudeE6() / 1000000.0f)
				// .append("/n");
				// sb.append("电话：").append(poiInfo.phoneNum).append("/n");
				// sb.append("邮编：").append(poiInfo.postCode).append("/n");
				// // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
				// sb.append("类型：").append(poiInfo.ePoiType).append("/n");
				// }
				// }
				// System.out.println("搜索到的结果:" + sb.toString());
			}

			public void onGetPoiResult(MKPoiResult res, int type, int error) {

			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		initMap();
		ActivityMeg.getInstance().addActivity(this);
		head = (ActionBar) findViewById(R.id.login_head);
		buttonWrap = (LinearLayout) findViewById(R.id.row4);
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
		adjustScreen();
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
				nameText.setText(mSharedPreferences.getString("userId", ""));
				passwordText.setText(mSharedPreferences.getString("pass", ""));
				remeberPassword.setSelected(true);
				remeberPassword.setTag("true");
				new MyListLoader(false).execute("");
			} else {
				remeberPassword.setSelected(false);
				remeberPassword.setTag("false");
			}
		}
	}

	private ExampleApplication app;

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		app = (ExampleApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new ExampleApplication.MyGeneralListener());
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		init();

		prepareListener();

		autoLogin();
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

	/**
	 * 开启异步任务登陆.
	 */
	private class MyListLoader extends AsyncTask<String, String, String> {

		private boolean showDialog;

		public MyListLoader(boolean showDialog) {
			this.showDialog = showDialog;
		}

		@Override
		protected void onPreExecute() {
			if (showDialog) {
				showDialog(DIALOG_KEY);
			}
			buttonLogin.setEnabled(false);
		}

		public String doInBackground(String... p) {
			name = nameText.getText().toString();
			pass = passwordText.getText().toString();
			login(name, pass);
			return "";
		}

		@Override
		public void onPostExecute(String Re) {
			if (showDialog) {
				removeDialog(DIALOG_KEY);
			}
			buttonLogin.setEnabled(true);
		}

		@Override
		protected void onCancelled() {
			if (showDialog) {
				removeDialog(DIALOG_KEY);
			}
			buttonLogin.setEnabled(true);
		}
	}

	/**
	 * 登录.
	 * 
	 * @param arg0
	 */
	public void login(View arg0) {
		// new MyListLoader(false).execute("");
		String serviceString = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(serviceString);
		String provider = locationManager.GPS_PROVIDER;
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			getLocationInfo(location);
		} else {
		}

		Intent intent2 = new Intent(LoginActivity.this, NewHomePage.class);
		startActivity(intent2);
	}

	// 地图相关
	MapView mMapView = null; // 地图View
	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

 

	public void getLocationInfo(Location location) {
		// TODO Auto-generated method stub
		String latLongInfo;

		if (location == null) {

			latLongInfo = "No Location Found";
		} else {
			double lat = location.getLatitude(); // 维度
			double lng = location.getLongitude(); // 精度
			int longitude = (int) (1000000 * lat);
			int latitude = (int) (1000000 * lng);
			System.out.println("经度" + longitude + ",," + latitude);
			System.out.println("经度" + (int)(39.915 * 1E6) + ",," + (int)(116.404 * 1E6));
			mSearch.reverseGeocode(new GeoPoint(longitude, latitude));
		}
		// HandlerThread thread = new HandlerThread(location);
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
			dialog.setCancelable(true);
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
			case 9:
				// mess_title.setVisibility(View.GONE);
				// Intent intent2 = new Intent(LoginActivity.this,
				// ActivitesList.class);
				// intent2.putExtra("name", "debug");
				// intent2.putExtra("uid", "debug");
				// intent2.putExtra("token", "debug");
				// startActivity(intent2);
				break;
			default:
				super.hasMessages(msg.what);
				break;
			}
		}
	};

	/**
	 * 登陆请求服务器数据
	 * 
	 * @param userName
	 * @param password
	 */
	public void login(final String userName, final String password) {
		// 得到url请求.
		DefaultHttpClient httpclient = new DefaultHttpClient();
		System.out.println("是否调试：" + Constant.debug);
		if (Constant.debug) {
			Message mes = new Message();
			mes.what = 9;
			myHandler.sendMessage(mes);
		} else {
			try {
				ServerResult result = HttpRequire.login(userName, password);
				if (result != null && 1 != result.getErrorcode()) {
					myHandler.sendEmptyMessage(1);
				}
				// 成功了就跳转到活动列表页面.
				else {
					Message mes = new Message();
					mes.obj = result.getData();
					mes.what = 3;
					myHandler.sendMessage(mes);
				}
			} catch (Exception e) {
				e.printStackTrace();
				myHandler.sendEmptyMessage(6);
			}
		}
	}
}