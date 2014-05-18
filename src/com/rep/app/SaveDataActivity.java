package com.rep.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kankan.wheel.widget.ScreenInfo;
import kankan.wheel.widget.WheelMain;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
	private LinearLayout indateBtn;
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
		View timepickerview = inflater.inflate(R.layout.selectbirthday, null);
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
		wheelMain.setTime(year, month, day);
		dialog = new AlertDialog.Builder(this).setView(timepickerview).show();

//		Window window = dialog.getWindow();
//		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
//		window.setWindowAnimations(R.style.mystyle); // 添加动画

		Button btn = (Button) timepickerview
				.findViewById(R.id.btn_datetime_sure);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				 
				dialog.dismiss();
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

	public static String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
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
				Intent intent2 = new Intent(SaveDataActivity.this,
						AddMoreDataActivity.class);
				startActivity(intent2);
			}
		});
		indateBtn =  (LinearLayout) findViewById(R.id.indateBtn);
		comeInNum = (TextView) findViewById(R.id.comein_v);
		intreNum = (TextView) findViewById(R.id.intre_v);
		tryNum = (TextView) findViewById(R.id.try_v);
		buyNum = (TextView) findViewById(R.id.buy_v);
		oldNum = (TextView) findViewById(R.id.old_v);
		timeSpan = (TextView) findViewById(R.id.timespan_v);
		indate = (TextView) findViewById(R.id.indate);
		indate_day = (TextView) findViewById(R.id.indate_day);
		Date t = new Date();
		indate.setText(toString(t));
		indate_day.setText(getDayOfWeek(t));
		
		indateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDateTimePicker();
			}
		});
	}
}