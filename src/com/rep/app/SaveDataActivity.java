package com.rep.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kankan.wheel.widget.ScreenInfo;
import kankan.wheel.widget.WheelMain;
import android.app.AlertDialog;
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

import com.rep.util.ActionBar;
import com.rep.util.ActionBar.Action;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class SaveDataActivity extends BaseActivity {
	private ActionBar head;
	private LinearLayout indateBtn, curentTime, comeinBtn, intrestBtn, tryBtn,
			buyBtn, oldBtn;
	private TextView comeInNum, intreNum, tryNum, buyNum, oldNum, timeSpan,
			indate, indate_day;
	private static final String[] weeks = new String[] { "星期日", "星期一", "星期二",
			"星期三", "星期四", "星期五", "星期六" };
	private WheelMain wheelMain;
	private AlertDialog dialog;

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
				Date d = getDate(
						wheelMain.getYear() + "-" + wheelMain.getMonth() + "-"
								+ wheelMain.getDay(), "yyyy-MM-dd");
				indate.setText(toDString(d));
				indate_day.setText(getDayOfWeek(d));

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
				Date nDate = getDayInThisWeek(yearAndWeek[0], yearAndWeek[1],
						wheelMain.getDay());
				indate.setText(toDString(nDate));
				indate_day.setText(getDayOfWeek(nDate));
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

	/**
	 * 界面初始化函数.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.savedata);
		init();

	}

	private float screenHeight, screenWidth;

	public static String toDString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}

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

	public static String getDayOfWeek(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		int i = ca.get(Calendar.DAY_OF_WEEK);
		return weeks[i - 1];
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
		head.init(R.string.titile_savadata, true, true, false, true,
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
				return R.string.finish;
			}

			@Override
			public void performAction(View view) {
				// Intent intent2 = new Intent(SaveDataActivity.this,
				// AddMoreDataActivity.class);
				// startActivity(intent2);
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
		Date t = new Date();
		indate.setText(toDString(t));
		indate_day.setText(getDayOfWeek(t));

		String nowHour = WheelMain.toHour(new Date());
		Integer nh = Integer.parseInt(nowHour);

		timeSpan.setText(SaveDataActivity.TIMESPANS[(nh - 8) < 0 ? 0 : (nh - 8)]);
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
	}
}