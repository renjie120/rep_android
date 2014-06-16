package com.rep.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kankan.wheel.widget.ScreenInfo;
import kankan.wheel.widget.WheelMain;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rep.bean.DataSaved;
import com.rep.bean.Result;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;
import com.rep.util.ActivityMeg;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class SaveDataActivity extends BaseActivity {
	private static final String url = HOST + "/services/dataService!addData.do";
	private ActionBar head;
	private LinearLayout indateBtn, curentTime, comeinBtn, intrestBtn, tryBtn,
			buyBtn, oldBtn;
	private TextView comeInNum, intreNum, tryNum, buyNum, oldNum, timeSpan,
			indate, indate_day;
	private static final String[] weeks = new String[] { "星期日", "星期一", "星期二",
			"星期三", "星期四", "星期五", "星期六" };
	private WheelMain wheelMain;
	private AlertDialog dialog;
	private ProgressDialog dialog2;
	private Date inDate = new Date();

	/***
	 * 
	 * 时间滚动器
	 * 
	 */
	public void showDateTimePicker() {
		LayoutInflater inflater = LayoutInflater.from(SaveDataActivity.this);
		View timepickerview = inflater.inflate(R.layout.selectday, null);
		timepickerview.setMinimumWidth(getWindowManager().getDefaultDisplay()
				.getWidth());
		ScreenInfo screenInfo = new ScreenInfo(SaveDataActivity.this);
		wheelMain = new WheelMain(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(dateFormat.parse(time));设置指定时间
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		// 使用时间选择器.
		wheelMain.setTime(year, month, day);
		dialog = new AlertDialog.Builder(this).setView(timepickerview).show();

		// 此处可以设置dialog显示的位置
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.mystyle); // 添加动画

		Button btn = (Button) timepickerview
				.findViewById(R.id.btn_datetime_sure);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				inDate = getDate(
						wheelMain.getYear() + "-" + wheelMain.getMonth() + "-"
								+ wheelMain.getDay(), "yyyy-MM-dd");
				indate.setText(toDString(inDate, "yyyy年MM月dd日"));
				indate_day.setText(getDayOfWeek(inDate));

			}
		});

	}

	/**
	 * 得到一周里面的指定星期几的日期.
	 * 
	 * @param year
	 * @param week
	 * @param day
	 * @return
	 */
	public static Date getDayInThisWeek(int year, int week, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, day);
		return cal.getTime();
	}

	/**
	 * 得到日期是在一年的第几个星期.
	 * 
	 * @param date
	 * @return
	 */
	public static int[] getYearAndWeekOfYear(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		return new int[] { ca.get(Calendar.YEAR), ca.get(Calendar.WEEK_OF_YEAR) };
	}

	/**
	 * 显示星期的选择器.
	 */
	public void showDayPicker() {
		LayoutInflater inflater = LayoutInflater.from(SaveDataActivity.this);
		View timepickerview = inflater.inflate(R.layout.selectday, null);
		timepickerview.setMinimumWidth(getWindowManager().getDefaultDisplay()
				.getWidth());
		ScreenInfo screenInfo = new ScreenInfo(SaveDataActivity.this);
		wheelMain = new WheelMain(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();

		final int[] yearAndWeek = getYearAndWeekOfYear(new Date());
		// 使用星期选择器.
		wheelMain.setDay();
		dialog = new AlertDialog.Builder(this).setView(timepickerview).show();

		// 此处可以设置dialog显示的位置
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.mystyle); // 添加动画

		Button btn = (Button) timepickerview
				.findViewById(R.id.btn_datetime_sure);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				inDate = getDayInThisWeek(yearAndWeek[0], yearAndWeek[1],
						wheelMain.getDay());
				indate.setText(toDString(inDate, "yyyy年MM月dd日"));
				indate_day.setText(getDayOfWeek(inDate));
			}
		});

	}

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			default:
				super.hasMessages(msg.what);
				break;
			}
		}
	};

	/**
	 * 显示数值的下拉菜单.
	 * 
	 * @param type
	 */
	public void showNumberPicker(final int type) {
		LayoutInflater inflater = LayoutInflater.from(SaveDataActivity.this);
		View timepickerview = inflater.inflate(R.layout.selectday, null);
		timepickerview.setMinimumWidth(getWindowManager().getDefaultDisplay()
				.getWidth());
		ScreenInfo screenInfo = new ScreenInfo(SaveDataActivity.this);
		wheelMain = new WheelMain(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();
		dialog = new AlertDialog.Builder(this).setView(timepickerview).show();

		wheelMain.initNumberPicker(0);
		// 此处可以设置dialog显示的位置
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.mystyle); // 添加动画

		Button btn = (Button) timepickerview
				.findViewById(R.id.btn_datetime_sure);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				int b = wheelMain.getHours() * 100;
				if (type == 1)
					comeInNum.setText("" + (b + 1 + wheelMain.getMin()));
				else if (type == 2)
					intreNum.setText("" + (b + 1 + wheelMain.getMin()));
				else if (type == 3)
					tryNum.setText("" + (b + 1 + wheelMain.getMin()));
				else if (type == 4)
					buyNum.setText("" + (b + 1 + wheelMain.getMin()));
				else if (type == 5)
					oldNum.setText("" + (b + 1 + wheelMain.getMin()));
			}
		});

	}

	public static final String[] TIMESPANS = { "08:00-09:00", "09:00-10:00",
			"10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00",
			"14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00",
			"18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00",
			"22:00-23:00" };

	public static final String[] DAYS = { "星期天", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };

	/**
	 * 显示当前时间段.
	 */
	public void showTimeSpan() {
		LayoutInflater inflater = LayoutInflater.from(SaveDataActivity.this);
		View timepickerview = inflater.inflate(R.layout.selectday, null);
		timepickerview.setMinimumWidth(getWindowManager().getDefaultDisplay()
				.getWidth());
		ScreenInfo screenInfo = new ScreenInfo(SaveDataActivity.this);
		wheelMain = new WheelMain(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();
		dialog = new AlertDialog.Builder(this).setView(timepickerview).show();

		wheelMain.initTimeSpan();
		// 此处可以设置dialog显示的位置
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.mystyle); // 添加动画

		Button btn = (Button) timepickerview
				.findViewById(R.id.btn_datetime_sure);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				timeSpan.setText(TIMESPANS[wheelMain.getMin()]);
			}
		});

	}

	private Bundle bund;

	@ViewInject(R.id.goon)
	private Button goon;

	@OnClick({ R.id.goon })
	public void goSaveData(View v) {
		setContentView(R.layout.savedata);
		bund = getIntent().getExtras();
		init();
	}

	private Bundle b ;
	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tip);
		ViewUtils.inject(this);
		b = getIntent().getExtras();
		head = (ActionBar) findViewById(R.id.tip_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.tip_title, false, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * 1),
				(int) (screenHeight * titleH));
		ActivityMeg.getInstance().addActivity(this);
	}

	private float screenHeight, screenWidth;

	/**
	 * 将日期格式打印出来.
	 * @param date
	 * @param str
	 * @return
	 */
	public static String toDString(Date date, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	/**
	 * 日期转换.
	 * @param dateStr
	 * @param formateStr
	 * @return
	 */
	public static Date getDate(String dateStr, String formateStr) {
		SimpleDateFormat formatter2 = new SimpleDateFormat(formateStr);
		Date date = new Date();
		try {
			date = formatter2.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 得到指定日期对应的字符串.
	 * @param date
	 * @return
	 */
	public static String getDayOfWeek(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		int i = ca.get(Calendar.DAY_OF_WEEK);
		return weeks[i - 1];
	}

	private static final int DIALOG_KEY = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			dialog2 = new ProgressDialog(this);
			dialog2.setMessage("正在保存数据,请稍候");
			dialog2.setIndeterminate(true);
			dialog2.setCancelable(false);
			return dialog2;
		}
		}
		return null;
	}

	/**
	 * 保存到数据库里面.
	 * @param userId 操作人员
	 * @param indate 日期
	 * @param timeSpan  时间段
	 */
	private void saveDataSaved(String userId, String indate, String timeSpan,String phone) {
		DbUtils db = DbUtils.create(SaveDataActivity.this);
		db.configAllowTransaction(true);
		DataSaved r = new DataSaved();
		r.setUserId(userId);
		r.setPhone(phone);
		r.setInDate(indate);
		r.setTimeSpan(timeSpan);
		System.out.println("保存数据："+JSON.toJSONString(r));
		try {
			db.save(r);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算当前时间段是否已经填写过数据.
	 * @param userId
	 * @param indate
	 * @param timeSpan
	 * @return
	 */
	private long countDataSaved(String userId, String indate, String timeSpan) {
		DbUtils db = DbUtils.create(SaveDataActivity.this);
		db.configAllowTransaction(true);
		DataSaved r = new DataSaved();
		r.setUserId(userId);
		r.setInDate(indate);
		r.setTimeSpan(timeSpan);
		try {
			return db.count(Selector.from(DataSaved.class)
					.where("userId", "=", userId).and("indate", "=", indate)
					.and("timeSpan", "=", timeSpan));
		} catch (DbException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 保存到远程服务端当前的填写的数据.
	 * @param uid
	 * @param tk
	 */
	private void saveData(final String uid, String tk,final String phone) {
		if (DEBUG) {
			alert("添加成功");
			initData();
			Intent t = new Intent(SaveDataActivity.this,
					MyViewPagerActivity.class);
			t.putExtras(b);
			startActivity(t);
		} else {
			final String stimeSpan = timeSpan.getText().toString();
			String icomeNum = comeInNum.getText().toString();
			String intrestNum = intreNum.getText().toString();
			String itryNum = tryNum.getText().toString();
			String ibuyNum = buyNum.getText().toString();
			String ioldNum = oldNum.getText().toString();
			HttpUtils http = new HttpUtils();
			RequestParams p = new RequestParams();
			final String in_date = toDString(inDate, "yyyy-MM-dd");
			p.addBodyParameter("indate", in_date);
			p.addBodyParameter("userId", uid);
			p.addBodyParameter("token", tk);
			p.addBodyParameter("dataType", "1");
			p.addBodyParameter("comeNum", icomeNum);
			p.addBodyParameter("timeSpan", stimeSpan);
			p.addBodyParameter("intrestNum", intrestNum);
			p.addBodyParameter("tryNum", itryNum);
			p.addBodyParameter("buyNum", ibuyNum);
			p.addBodyParameter("oldNum", ioldNum);
			if (countDataSaved(uid, in_date, stimeSpan) == 0) {
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
								Result r = (Result) JSON.parseObject(
										responseInfo.result, Result.class);
								if (r.getErrorCode() == 0) {
									alert("添加成功");
									saveDataSaved(uid, in_date, stimeSpan,phone);
									initData();
									tip();
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
			} else {
				alert("已经保存过当天时间端的数据.");
			}
		}
	}

	/**
	 * 模拟定时提醒.
	 */
	private void tip(){
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("com.renjie.rep");
		intent.putExtra("tipContent", "查看上周的rep数据");
		intent.putExtra("time", "2014-6-16");   

		PendingIntent sender = PendingIntent.getBroadcast(
				SaveDataActivity.this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		// 闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000*10, sender);
	}
	
	/**
	 * 将填写的数据清空.
	 */
	private void initData() {
		Date t = new Date();
		indate.setText(toDString(t, "yyyy年MM月dd日"));
		indate_day.setText(getDayOfWeek(t));

		String nowHour = WheelMain.toHour(new Date());
		Integer nh = Integer.parseInt(nowHour);

		int _index = nh - 8;
		if (_index < 0)
			_index = 0;
		if (_index >= SaveDataActivity.TIMESPANS.length)
			_index = SaveDataActivity.TIMESPANS.length - 1;
		timeSpan.setText(SaveDataActivity.TIMESPANS[_index]);
		comeInNum.setText("0");
		intreNum.setText("0");
		tryNum.setText("0");
		buyNum.setText("0");
		oldNum.setText("0");

		//显示完成之后，弹出一下当前已经填写的时间.
		Intent tt = new Intent(SaveDataActivity.this,
				MyViewPagerActivity.class);
		tt.putExtras(b);
		startActivity(tt);
	}

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.savedata_head);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.titile_savadata, false, true, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setRightSize((int) (screenWidth * rgtBtnW),
				(int) (screenHeight * rgtBtnH));
		head.setRightText(R.string.finish);
		head.setRightActionWithText(new Action() {
			@Override
			public int getDrawable() {
				return R.string.finish;
			}

			@Override
			public void performAction(View view) {
				String userId = bund.getString("userId");
				String phone = bund.getString("phone");
				String token = bund.getString("token");
				saveData(userId, token,phone);
			}
		});
		indateBtn = (LinearLayout) findViewById(R.id.indateBtn);
		curentTime = (LinearLayout) findViewById(R.id.curentTime);
		comeinBtn = (LinearLayout) findViewById(R.id.comeinBtn);
		intrestBtn = (LinearLayout) findViewById(R.id.intrestBtn);
		tryBtn = (LinearLayout) findViewById(R.id.tryBtn);
		buyBtn = (LinearLayout) findViewById(R.id.buyBtn);
		oldBtn = (LinearLayout) findViewById(R.id.oldBtn);
		comeInNum = (TextView) findViewById(R.id.comein_v);
		intreNum = (TextView) findViewById(R.id.intre_v);
		tryNum = (TextView) findViewById(R.id.try_v);
		buyNum = (TextView) findViewById(R.id.buy_v);
		oldNum = (TextView) findViewById(R.id.old_v);
		timeSpan = (TextView) findViewById(R.id.timespan_v);
		indate = (TextView) findViewById(R.id.indate);
		indate_day = (TextView) findViewById(R.id.indate_day);
		indateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// showDateTimePicker();
				showDayPicker();
			}
		});
		curentTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimeSpan();
			}
		});
		comeinBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberPicker(1);
			}
		});
		intrestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberPicker(2);
			}
		});
		tryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberPicker(3);
			}
		});
		buyBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberPicker(4);
			}
		});
		oldBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberPicker(5);
			}
		});
		initData();
	}
}