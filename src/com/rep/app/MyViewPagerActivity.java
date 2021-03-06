package com.rep.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rep.bean.DataSaved;
import com.rep.util.ActionBar;
import com.rep.util.TimeSpanTipAdapter;

/**
 * 仿优酷Android客户端图片左右滑动
 * 
 */
public class MyViewPagerActivity extends BaseActivity {
	private float screenHeight, screenWidth;
	// private List<ImageView> imageViews; // 滑动的图片集合
	private List<View> viewList;// 正文
	private String[] titles; // 图片标题
	private int currentItem = 0; // 当前图片的索引号
	// 要进行提醒的时间端的索引
	private String[] tips = new String[] { ",0,5,", ",0,6,", ",2,6,", ",4,5,",
			",3,6,", ",2,7,", ",2,8," };

	@ViewInject(R.id.vp)
	private ViewPager viewPager; // android-support-v4中的滑动组件
	@ViewInject(R.id.day_title)
	private TextView tv_title;

	@ViewInject(R.id.everyday_tip_head)
	private ActionBar head;

	@ViewInject(R.id.goon)
	private Button goon;
	@ViewInject(R.id.leftBtn)
	private ImageView leftBtn1;
	@ViewInject(R.id.rightBtn)
	private ImageView rightBtn1;

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 第一天
			case 1:
				leftBtn1.setVisibility(View.GONE);
				rightBtn1.setVisibility(View.VISIBLE);
				break;
			case 2:
				leftBtn1.setVisibility(View.VISIBLE);
				rightBtn1.setVisibility(View.VISIBLE);
				break;
			// 最后一天
			case 3:
				leftBtn1.setVisibility(View.VISIBLE);
				rightBtn1.setVisibility(View.GONE);
				break;
			default:
				super.hasMessages(msg.what);
				break;
			}
		}
	};

	@OnClick({ R.id.leftBtn })
	public void leftBtnClick(View v) {
		if (--currentItem < 0)
			currentItem = 0;
		if (currentItem == 0)
			myHandler.sendEmptyMessage(1);
		else
			myHandler.sendEmptyMessage(2);
		viewPager.setCurrentItem(currentItem);
	}

	@OnClick({ R.id.rightBtn })
	public void rightBtnClick(View v) {
		if (++currentItem > 6)
			currentItem = 6;
		if (currentItem == 6)
			myHandler.sendEmptyMessage(3);
		else
			myHandler.sendEmptyMessage(2);
		viewPager.setCurrentItem(currentItem);
	}

	@OnClick({ R.id.goon })
	public void clickMethod(View v) {
		Intent t = new Intent(MyViewPagerActivity.this, ConfigTipActivity.class);
		t.putExtras(b);
		startActivity(t);
	}

	/**
	 * 设置显示的listView的内容
	 * 
	 * @param view1
	 * @param j
	 */
	private void setListAdapter(View view1, int j,List<String> added) {
		ListView listview1 = (ListView) view1.findViewById(R.id.tip_dataes);
		//得到日期字符串.
		String date = getDateInThisWeek(j+1);
		System.out.println("当前检查的日期："+date);
		ArrayList<Map<String, String>> herolist_wu2 = new ArrayList<Map<String, String>>();
		for (int i = 0; i < SaveDataActivity.TIMESPANS.length; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("timeSpan", SaveDataActivity.TIMESPANS[i]);
			m.put("tixing", tips[j].indexOf("" + i) != -1 ? "true" : "false");
			System.out.println("对比："+date+"-"+SaveDataActivity.TIMESPANS[i]);
			if(added!=null)
				m.put("haveAdded", added.contains(date+"-"+SaveDataActivity.TIMESPANS[i]) ? "true" : "false");
			else
				m.put("haveAdded","false");
			herolist_wu2.add(m);
		}
		TimeSpanTipAdapter simpleAdapter_Wu = new TimeSpanTipAdapter(
				herolist_wu2, this);
		listview1.setAdapter(simpleAdapter_Wu);
	}

	private Bundle b;
	private String userId;
	private String startDate, endDate;

	/**
	 * 得到当前星期里面的第几天.
	 * @param d 从1开始.
	 * @return
	 */
	private String getDateInThisWeek(int d){
		int[] yearAndWeek = SaveDataActivity.getYearAndWeekOfYear(new Date());
		Date _start = SaveDataActivity.getDayInThisWeek(yearAndWeek[0],
				yearAndWeek[1], d);
		return SaveDataActivity.toDString(_start, "yyyy-MM-dd");
	}
	
	/**
	 * 得到指定人员添加了的全部的日期和时间段信息.
	 * 
	 * @param userId
	 * @return
	 */
	private List<String> getHadadded(String userId) { 
		startDate = getDateInThisWeek(1);
		endDate = getDateInThisWeek(7); 
		DbUtils db = DbUtils.create(MyViewPagerActivity.this);
		db.configAllowTransaction(true);
		List<String> result = new ArrayList<String>();
		try {
			List<DataSaved> objs = db.findAll(Selector.from(DataSaved.class)
					.where("phone", "=", userId)
					.and("indate", ">=", startDate)
					.and("indate", "<=", endDate));
			System.out.println("查询保存了的。。。"+objs+"--userId="+userId+",,startDate="+startDate+",,endDate="+endDate);
			if (objs != null && objs.size() > 0){
				for(DataSaved d:objs){ 
					result.add(d.getInDate()+"-"+d.getTimeSpan());
				}
			} 
			return result;
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewpage);
		ViewUtils.inject(this);
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		b = getIntent().getExtras();
		userId = b.getString("phone");
		List<String> added = getHadadded(userId);
		System.out.println("已经填写的日期："+added);
		head.init(R.string.benzhou_title, true, false, false, false,
				(int) (screenHeight * barH));
		head.setLeftAction(new ActionBar.BackAction(this));
		head.setTitleSize((int) (screenWidth * 0.6),
				(int) (screenHeight * titleH));
		titles = new String[7];
		titles[0] = "星期天";
		titles[1] = "星期一";
		titles[2] = "星期二";
		titles[3] = "星期三";
		titles[4] = "星期四";
		titles[5] = "星期五";
		titles[6] = "星期六";
		
		viewList = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.everyday_tips, null);
		View view2 = inflater.inflate(R.layout.everyday_tips, null);
		View view3 = inflater.inflate(R.layout.everyday_tips, null);
		View view4 = inflater.inflate(R.layout.everyday_tips, null);
		View view5 = inflater.inflate(R.layout.everyday_tips, null);
		View view6 = inflater.inflate(R.layout.everyday_tips, null);
		View view7 = inflater.inflate(R.layout.everyday_tips, null);
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);
		viewList.add(view5);
		viewList.add(view6);
		viewList.add(view7);
		setListAdapter(view1, 0,added);
		setListAdapter(view2, 1,added);
		setListAdapter(view3, 2,added);
		setListAdapter(view4, 3,added);
		setListAdapter(view5, 4,added);
		setListAdapter(view6, 5,added);
		setListAdapter(view7, 6,added);

		tv_title.setText(titles[0]);//

		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// 初始化左右按钮
		// initImageButtonView();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		// private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]); 
			if (currentItem >= 6) {
				myHandler.sendEmptyMessage(3);
			} else if (currentItem <= 0)
				myHandler.sendEmptyMessage(1);
			else
				myHandler.sendEmptyMessage(2);
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(viewList.get(arg1));
			return viewList.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}