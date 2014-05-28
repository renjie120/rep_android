package com.rep.util;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rep.app.NewHomePage;
import com.rep.app.R;

public class MyAlarmReceiver extends BroadcastReceiver {
	private static int COMMON = 1;

	/**
	 * 出现一个提示信息.
	 */
	private void showNotify(Context ct, String str) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager = (NotificationManager) ct
				.getSystemService(ns);
		Notification notification = new Notification(R.drawable.ic_launcher,
				ct.getString(R.string.app_name), System.currentTimeMillis());
		// 点击通知之后的操作，打开一个intent
		Intent intent = new Intent(ct, NewHomePage.class); 
		// intent.putExtra("content", random + "");
		// notification.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻 Flag
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
		notification.defaults = Notification.DEFAULT_SOUND;// 默认声音
		// 下面的1表示是自动进行的提醒要进行填报数据.。
		PendingIntent contextIntent = PendingIntent.getActivity(ct, COMMON,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(ct.getApplicationContext(),
				ct.getString(R.string.app_name), str, contextIntent);
		notificationManager.notify(R.string.app_name, notification);
	}

	@Override
	public void onReceive(Context context, Intent arg1) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(
//				"alarm_record", Activity.MODE_PRIVATE);
////		String hour = String.valueOf(Calendar.getInstance().get(
//				Calendar.HOUR_OF_DAY));
//		String minute = String.valueOf(Calendar.getInstance().get(
//				Calendar.MINUTE));
//
//		System.out.println("接受到了接收器。。。。。。。");
		showNotify(context, "请上报数据"); 
	}

}
