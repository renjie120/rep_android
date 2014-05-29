package com.rep.app;

import java.security.NoSuchAlgorithmException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.BMapManager;
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
 * 完善信息.
 * 
 * @author Administrator
 * 
 */
public class AddMoreDataActivity extends BaseActivity {
	private static final String url = HOST + "/services/userService!regiest.do";
	@ViewInject(R.id.addmore_head)
	private ActionBar head;
	@ViewInject(R.id.price_v)
	private EditText price;
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
	private String phone,validCode,pass;
	private ExampleApplication app;

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

		init();
		ActivityMeg.getInstance().addActivity(this);
	}

	private float screenHeight, screenWidth;

	/**
	 * 初始化控件.
	 */
	private void init() {
		initMap();
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
	}

	private double lat, lng;
	private String city;
	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	public void getLocationInfo(Location location) {
		// TODO Auto-generated method stub
		String latLongInfo;

		if (location == null) {
			latLongInfo = "定位失败";
		} else {
			lat = location.getLatitude(); // 维度
			lng = location.getLongitude(); // 精度
			int longitude = (int) (1000000 * lat);
			int latitude = (int) (1000000 * lng);
			System.out.println("经度" + longitude + ",," + latitude);
			System.out.println("经度" + (int) (39.915 * 1E6) + ",,"
					+ (int) (116.404 * 1E6));
			mSearch.reverseGeocode(new GeoPoint(longitude, latitude));
		}
		// HandlerThread thread = new HandlerThread(location);
	}

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

	private void initMap() {

		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (res == null) {
					return;
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					// 反地理编码：通过坐标点检索详细地址及周边poi
					String strInfo = res.strAddr;
					city = res.addressComponents.city;
					System.out.println("当前城市是:" + city);
					System.out.println(strInfo + "---查询结果" + ",,"
							+ JSON.toJSONString(res));
					Toast.makeText(AddMoreDataActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}
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
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.GPS_PROVIDER;
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			getLocationInfo(location);
		} else {
		}
	}

	private void addMore(final String _price, String _worktime,
			String _weekend, String _worknum, String _brandtype,
			String _brandname, String phone) {

		try {
			HttpUtils http = new HttpUtils();
			RequestParams p = new RequestParams();
			p.addBodyParameter("masterPrice", _price);
			p.addBodyParameter("brandName", _brandname);
			p.addBodyParameter("brandType", _brandtype);
			p.addBodyParameter("lng_north", lat + "");
			p.addBodyParameter("lat_east", lng + "");
			p.addBodyParameter("worktime", _worktime);
			p.addBodyParameter("workNum", _worknum);
			p.addBodyParameter("weekendNum", _weekend);
			p.addBodyParameter("location", city);
			p.addBodyParameter("phone", phone);
			p.addBodyParameter("validCode", validCode);
			p.addBodyParameter("password", pass);  
			String tk = HttpRequire.getMD5(HttpRequire.getBase64(phone));
			p.addBodyParameter("token", tk); 
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
								alert("注册成功,请登录");
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
}