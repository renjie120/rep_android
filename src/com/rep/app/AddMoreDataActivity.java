package com.rep.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.MKSearch;
import com.example.jpushdemo.ExampleApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;
import com.rep.util.ActivityMeg;

/**
 * 完善信息.
 * 
 * @author Administrator
 * 
 */
public class AddMoreDataActivity extends BaseActivity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private static final String url = HOST + "/services/userService!regiest.do";
	@ViewInject(R.id.addmore_head)
	private ActionBar head;
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
	private String phone, validCode, pass;
	private ExampleApplication app;

	private void logMsg(String str) {
		System.out.println("输出日志：" + str);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}

			logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addmoredata);
		ViewUtils.inject(this);
		phone = getIntent().getStringExtra("phone");
		validCode = getIntent().getStringExtra("validCode");
		pass = getIntent().getStringExtra("password");

		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型为bd09ll
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setProdName("rep"); // 设置产品线名称
		option.setIsNeedAddress(true);
		option.setScanSpan(1000 * 60 * 60); // 定时定位，每隔5秒钟定位一次。
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null)
					return;
				city_v.setText(location.getCity());
			}

			public void onReceivePoi(BDLocation location) {
				// return ;
			}
		});

		init();
		if (mLocationClient != null) {
			if (mLocationClient.isStarted()) {
				mLocationClient.stop();
			} else {
				mLocationClient.start();
			}
		}else{
			initGPS();
		}
		ActivityMeg.getInstance().addActivity(this);
	}

	@Override
	public void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onDestroy();
	}

	@Override
	public void onPause() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onPause();
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
		head.init(R.string.titile_adddata, true, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.finish);
		head.setLeftAction(new ActionBar.BackAction(this));
		head.setRightActionWithText(new Action() {

			@Override
			public int getDrawable() {
				return R.string.makesure;
			}

			@Override
			public void performAction(View view) {

				String _price = price.getText().toString();
				String _worktime = worktime.getText().toString();
				String _weekend = weekend.getText().toString();
				String _worknum = worknum.getText().toString();
				String _brandtype = brandtype.getText().toString();
				String _brandname = brandname.getText().toString();

				// 注册
				addMore(_price, _worktime, _weekend, _worknum, _brandtype,
						_brandname, phone);
			}
		});
		
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
			dialog.setMessage("正在注册,请稍候");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		}
		return null;
	}

	private void initGPS() {
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// 判断GPS模块是否开启，如果没有则开启
		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(AddMoreDataActivity.this, "请打开GPS自动获取城市名!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void addMore(final String _price, String _worktime,
			String _weekend, String _worknum, String _brandtype,
			String _brandname, String phone) {

		// try {
		// HttpUtils http = new HttpUtils();
		// RequestParams p = new RequestParams();
		// p.addBodyParameter("masterPrice", _price);
		// p.addBodyParameter("brandName", _brandname);
		// p.addBodyParameter("brandType", _brandtype);
		// p.addBodyParameter("lng_north", lat + "");
		// p.addBodyParameter("lat_east", lng + "");
		// p.addBodyParameter("worktime", _worktime);
		// p.addBodyParameter("workNum", _worknum);
		// p.addBodyParameter("weekendNum", _weekend);
		// p.addBodyParameter("location", city);
		// p.addBodyParameter("phone", phone);
		// p.addBodyParameter("validCode", validCode);
		// p.addBodyParameter("password", pass);
		// String tk = HttpRequire.getMD5(HttpRequire.getBase64(phone));
		// p.addBodyParameter("token", tk);
		// http.send(HttpRequest.HttpMethod.POST, url, p,
		// new RequestCallBack<String>() {
		// @Override
		// public void onStart() {
		// showDialog(DIALOG_KEY);
		// }
		//
		// @Override
		// public void onLoading(long total, long current,
		// boolean isUploading) {
		// }
		//
		// @Override
		// public void onSuccess(ResponseInfo<String> responseInfo) {
		// removeDialog(DIALOG_KEY);
		// System.out.println(responseInfo.result);
		// Result r = (Result) JSON.parseObject(
		// responseInfo.result, Result.class);
		// if (r.getErrorCode() == 0) {
		// alert("注册成功,请登录");
		// } else {
		// alert(r.getErrorMessage());
		// }
		// }
		//
		// @Override
		// public void onFailure(HttpException error, String msg) {
		// removeDialog(DIALOG_KEY);
		// }
		// });
		// } catch (NoSuchAlgorithmException e) {
		// e.printStackTrace();
		// }
	}
}