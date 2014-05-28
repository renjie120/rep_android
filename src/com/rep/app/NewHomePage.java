package com.rep.app;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.rep.util.Tool;

/**
 * 普通的主界面.测试打电话和打开网页的intent程序.
 * 
 * @author lsq
 * 
 */
public class NewHomePage extends TabActivity implements OnCheckedChangeListener {
	AlertDialog menuDialog;// menu菜单Dialog

	public static final String TAB_ITEM_1 = "firstpage";
	public static final String TAB_ITEM_2 = "diary";
	public static final String TAB_ITEM_3 = "report";
	public static final String TAB_ITEM_4 = "config";
	private boolean isSuper;
	private RadioGroup group;
	private TabHost tabHost;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_tab);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			System.out.println("结果:" + b.getString("content") + ",brandName="
					+ b.getString("brandName"));
		}
		group = (RadioGroup) findViewById(R.id.main_radio);
		group.setOnCheckedChangeListener(this);
		isSuper = getIntent().getBooleanExtra(Tool.SUPERPASS, false);
		tabHost = this.getTabHost();
		TabSpec tab1 = tabHost.newTabSpec(TAB_ITEM_1);
		TabSpec tab2 = tabHost.newTabSpec(TAB_ITEM_2);
		TabSpec tab3 = tabHost.newTabSpec(TAB_ITEM_3);
		TabSpec tab4 = tabHost.newTabSpec(TAB_ITEM_4);
		tab1.setIndicator(TAB_ITEM_1).setContent(
				new Intent(NewHomePage.this, SaveDataActivity.class)
						.putExtras(b));
		tab2.setIndicator(TAB_ITEM_3).setContent(
				new Intent(NewHomePage.this, ItimeActivity.class).putExtras(b));
		tab3.setIndicator(TAB_ITEM_2)
				.setContent(
						new Intent(NewHomePage.this, IchartActivity.class)
								.putExtras(b));
		tab4.setIndicator(TAB_ITEM_2).setContent(
				new Intent(NewHomePage.this, HistoryActivity.class)
						.putExtras(b));
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		tabHost.addTab(tab4);
		tabHost.setCurrentTab(0);
	}

	public String getToday() {
		final Calendar today = Calendar.getInstance();
		int myyear = today.get(Calendar.YEAR);
		int mymonth = today.get(Calendar.MONTH) + 1;
		int myday = today.get(Calendar.DAY_OF_MONTH);
		return new StringBuilder().append(myyear).append("-").append(mymonth)
				.append("-").append(myday).toString();
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button1:
			tabHost.setCurrentTabByTag(TAB_ITEM_1);
			break;
		case R.id.radio_button2:
			tabHost.setCurrentTabByTag(TAB_ITEM_2);
			break;
		case R.id.radio_button3:
			tabHost.setCurrentTabByTag(TAB_ITEM_3);
			break;
		case R.id.radio_button4:
			tabHost.setCurrentTabByTag(TAB_ITEM_4);
			break;
		default:
			break;
		}
	}
}