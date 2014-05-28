package com.rep.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rep.util.MyAlarmReceiver;
import com.rep.util.Reminder;

/**
 * 提醒配置界面.
 * 
 * @author 130126
 * 
 */
public class ConfigActivity extends BaseActivity {
	@ViewInject(R.id.tvSex)
	private TextView tvSex;

	@ViewInject(R.id.config)
	private Button config;

	private boolean tongzhi = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.config);
		ViewUtils.inject(this);
	}

	@OnClick({ R.id.config })
	public void clickMethod(View v) {
		if (tongzhi) {
			tongzhi = !tongzhi;
			cancelNotification();
		} else {
			tongzhi = !tongzhi;
			setNotification();
		}
		Toast.makeText(ConfigActivity.this, "you clicked button!" + tongzhi,
				Toast.LENGTH_SHORT).show();

	}

	// 一周的毫秒数.
	private static final int SevenDAY = 1000 * 60 * 60 * 24 * 7;

	/**
	 * 得到当前星期的下一个星期几，几点，几分.
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getWeekOfYear(int weekDay, int hour, int minutue) {
		// 得到是一年里面的第几个星期
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(new Date());
		Calendar calendar = Calendar.getInstance();
		int week = ca.get(Calendar.WEEK_OF_YEAR);
		int day = ca.get(Calendar.DAY_OF_WEEK);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (weekDay > day) {
			calendar.set(Calendar.WEEK_OF_YEAR, week);
			calendar.set(Calendar.DAY_OF_WEEK, weekDay);
			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, minutue);
		} else {
			calendar.set(Calendar.WEEK_OF_YEAR, week + 1);
			calendar.set(Calendar.DAY_OF_WEEK, weekDay);
			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, minutue);
		}
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 设置到了指定的星期几，几点，几分进行通知！
	 * 
	 * @param ct
	 * @param weekDay
	 *            星期几
	 * @param hour
	 *            几点
	 * @param minute
	 *            几分
	 */
	private void setNotifyAtTime(Context ct, int weekDay, int hour, int minute) {
		Intent intent = new Intent(ct, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(ct, 0, intent, 0);

		long firstTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
		long systemTime = System.currentTimeMillis();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// 这里时区需要设置一下，不然会有8个小时的时间差
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 选择的定时时间
		long selectTime = calendar.getTimeInMillis();
		// 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
		// 下面都是计算第一次需要进行提醒的时间间隔，因为是每隔一周提醒一次，所如果第一次时间已经过去了，下次就要等七天.
		if (systemTime > selectTime) {
			// Toast.makeText(MainActivity.this,"设置的时间小于当前时间",
			// Toast.LENGTH_SHORT).show();
			// 如果当前时间已经过了，就设置到下一周的这个时间提醒.
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			selectTime = calendar.getTimeInMillis();
		}
		// 计算现在时间到设定时间的时间差
		long time = selectTime - systemTime;
		firstTime += time;
		// 进行闹铃注册.
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
				SevenDAY, sender);
		Toast.makeText(ct, "设置重复闹铃成功! " + hour + "-" + minute,
				Toast.LENGTH_LONG).show();
	}

	// 添加常驻通知
	private void setNotification() {
		DbUtils db = DbUtils.create(ConfigActivity.this);
		db.configAllowTransaction(true);
		db.configDebug(true);
		Reminder r = new Reminder();
		r.setFlg("1");
		r.setHour(1);
		r.setMinute(12);
		r.setUser("130126");
		r.setLastRemindedTime("130126");
		try {
			db.save(r);
			
			List<Reminder> list = db.findAll(Selector.from(Reminder.class)
					.orderBy("id").limit(10)); 
			if (list.size() > 0) {
				System.out.println(list);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// setNotifyAtTime(this, 20, 43);
		// System.out.println("准备设置通知了。。。。。。。。。。");
		// // 下面是一个定时器，每一分钟进行一次调用通知对象：AlarmReceiver
		// AlarmManager am = (AlarmManager)
		// getSystemService(Context.ALARM_SERVICE);
		//
		// Intent intent2 = new Intent(this, MyAlarmReceiver.class);
		// // 创建封装 BroadcastReceiver 的 pendingIntent 对象
		// PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
		// intent2, 0);
		// // 过10s 执行这个闹铃
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTimeInMillis(System.currentTimeMillis());
		// calendar.add(Calendar.SECOND, 10);
		// // 过10秒弹出一个提示.
		// // am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
		// // pendingIntent);
		// // 开始定时器, 每 1分钟执行一次
		// am.setRepeating(AlarmManager.RTC, 0, 1000, pendingIntent);

		// // 进行闹铃注册
		// Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
		// PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
		// 0, intent, 0);

		// AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);

	}

	// 取消通知
	private void cancelNotification() {
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent2 = new Intent(this, MyAlarmReceiver.class);
		// 创建封装 BroadcastReceiver 的 pendingIntent 对象
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent2, 0);
		am.cancel(pendingIntent);
	}
}