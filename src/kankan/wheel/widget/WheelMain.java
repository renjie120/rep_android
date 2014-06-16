package kankan.wheel.widget;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.view.View;

import com.rep.app.R;

public class WheelMain {

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	public int screenheight;
	// 是否是公历
	private boolean isLinter;
	private static int START_YEAR = 1942, END_YEAR = 2049;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelMain(View view) {
		super();
		this.view = view;
		setView(view);
		viewInit();
	}

	public void viewInit() {
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_hours = (WheelView) view.findViewById(R.id.hours);
		wv_mins = (WheelView) view.findViewById(R.id.min);

	}

	/**
	 * 时分选择器
	 * 
	 */
	public void showHours(int hours, int min) {
		wv_year.setVisibility(View.GONE);
		wv_month.setVisibility(View.GONE);
		wv_day.setVisibility(View.GONE);
		wv_hours.setVisibility(View.VISIBLE);
		wv_mins.setVisibility(View.VISIBLE);
		// 时

		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));// 设置"年"的显示数据
		wv_hours.setCyclic(true);// 可循环滚动
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(hours);// 初始化时显示的数据

		// 分

		wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
		wv_mins.setCyclic(true);
		wv_mins.setLabel("分");
		wv_mins.setCurrentItem(min);

		int textSize = 0;
		textSize = (screenheight / 100) * 4;
		// wv_hours.TEXT_SIZE = textSize;
		// wv_mins.TEXT_SIZE = textSize;
	}

	/***
	 * 
	 * 弹出农历日期时间选择器
	 * 
	 */
	public void showlunarTimePicker() {

		wv_year.setVisibility(View.VISIBLE);
		wv_month.setVisibility(View.VISIBLE);
		wv_day.setVisibility(View.VISIBLE);
		wv_hours.setVisibility(View.GONE);
		wv_mins.setVisibility(View.GONE);
		// String year[] = otherUtil.getYera();
		String year[] = new String[] { "2010", "2011" };
		final String monthOfAlmanac[] = { "正月", "二月", "三月", "四月", "五月", "六月",
				"七月", "八月", "九月", "十月", "冬月", "腊月" };
		final String daysOfAlmanac[] = { "初一", "初二", "初三", "初四", "初五", "初六",
				"初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六",
				"十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六",
				"廿七", "廿八", "廿九", "三十" }; // 农历的天数

		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setVisibleItems(5);
		wv_year.setLabel("");
		wv_year.setAdapter(new ArrayWheelAdapter<String>(year, 10));// 设置"年"的显示数据

		// 月

		wv_month.setCyclic(true);
		wv_month.setVisibleItems(5);
		wv_month.setLabel("");
		wv_month.setAdapter(new ArrayWheelAdapter<String>(monthOfAlmanac, 5));

		// 日

		wv_day.setCyclic(true);
		wv_day.setVisibleItems(5);
		wv_day.setLabel("");
		wv_day.setAdapter(new ArrayWheelAdapter<String>(daysOfAlmanac, 5));

	}

	public void setTime(final int year, final int month, final int day) {
		int textSize = 0;
		textSize = (screenheight / 100) * 3;
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		isLinter = true;
		initDateTimePicker(year, month, day);
	}

	public static int getDayOfWeek(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.DAY_OF_WEEK);
	}

	public void setDay( ) {
		int textSize = 0;
		textSize = (screenheight / 100) * 3;
		wv_day.TEXT_SIZE = textSize;
		int d = getDayOfWeek(new Date());  
		//弹出来默认选择当前星期.
		initDayPicker(d);
	}

	/**
	 * 得到小时数.
	 * 
	 * @param date
	 * @return
	 */
	public static String toHour(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		return sdf.format(date);
	}

	/**
	 * 时间段选择器.
	 */
	public void initTimeSpan() {
		wv_year.setVisibility(View.GONE);
		wv_month.setVisibility(View.GONE);
		wv_day.setVisibility(View.GONE);
		wv_hours.setVisibility(View.GONE);
		wv_mins.setVisibility(View.VISIBLE);
		String nowHour = toHour(new Date());
		Integer nh = Integer.parseInt(nowHour);
		wv_mins.setAdapter(new TimeSpanAdapter(0, 14));
		wv_mins.setCyclic(false);
		wv_mins.setCurrentItem((nh - 8) < 0 ? 0 : (nh - 8));
		wv_mins.setVisibleItems(5);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		textSize = (screenheight / 100) * 4;
		wv_mins.TEXT_SIZE = textSize;
	}

	/**
	 * 数字选择器
	 * 
	 * @param number
	 */
	public void initNumberPicker(int number) {

		wv_year.setVisibility(View.GONE);
		wv_month.setVisibility(View.GONE);
		wv_day.setVisibility(View.GONE);
		wv_hours.setVisibility(View.VISIBLE);
		wv_mins.setVisibility(View.VISIBLE);
		wv_hours.setAdapter(new MyNumberWheelAdapter());// 设置"年"的显示数据
		wv_hours.setCyclic(false);// 可循环滚动
		wv_hours.setCurrentItem(0);// 初始化时显示的数据
		wv_hours.setVisibleItems(5);

		int s = number / 100;
		int start = s * 100 + 1;
		int end = (s + 1) * 100 - 1;
		wv_mins.setAdapter(new NumericWheelAdapter(start, end));
		wv_mins.setCyclic(false);
		wv_mins.setCurrentItem(number - 1);
		wv_mins.setVisibleItems(5);

		// 添加按照人数区间的监听
		OnWheelChangedListener wheelListener_people = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int start = newValue * 100 + 1;
				int end = (newValue + 1) * 100 - 1;
				wv_mins.setAdapter(new NumericWheelAdapter(start, end));
				wv_mins.setCurrentItem(0);
			}
		};
		wv_hours.addChangingListener(wheelListener_people);
		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		textSize = (screenheight / 100) * 4;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;

	}

	/**
	 * 星期选择器.
	 */
	public void initDayPicker(int d) {
		wv_year.setVisibility(View.GONE);
		wv_month.setVisibility(View.GONE);
		wv_day.setVisibility(View.VISIBLE);
		wv_hours.setVisibility(View.GONE);
		wv_mins.setVisibility(View.GONE);
		wv_day.setAdapter(new DayAdapter(0, 6));
		//设置已经选择的时间
//		List<String> list = new ArrayList<String>();
//		list.add("星期一");
//		wv_day.setHasChecked(list);
		wv_day.setCyclic(false); 
		wv_day.setCurrentItem(d-1);
		wv_day.setVisibleItems(5);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		textSize = (screenheight / 100) * 4;
		wv_day.TEXT_SIZE = textSize;
	}

	/**
	 * @Description: TODO 弹出阳历日期时间选择器
	 */
	public void initDateTimePicker(int year, int month, int day) {

		wv_year.setVisibility(View.VISIBLE);
		wv_month.setVisibility(View.VISIBLE);
		wv_day.setVisibility(View.VISIBLE);
		wv_hours.setVisibility(View.GONE);
		wv_mins.setVisibility(View.GONE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year.setLabel("年");// 添加文字
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(false);// 可循环滚动
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		wv_year.setVisibleItems(5);
		// 月
		wv_month.setLabel("月");
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(false);
		wv_month.setCurrentItem(month);
		wv_month.setVisibleItems(5);

		// 日
		wv_day.setCyclic(false);
		wv_day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (isLinter) {
			if (list_big.contains(String.valueOf(month + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 31));

			} else if (list_little.contains(String.valueOf(month + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 30));
			} else {
				// 闰年
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 29));
				} else {
					wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		}
		wv_day.setCurrentItem(day - 1);
		wv_day.setVisibleItems(5);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (isLinter) {
					int year_num = newValue + START_YEAR;
					// 判断大小月及是否闰年,用来确定"日"的数据
					if (list_big.contains(String.valueOf(wv_month
							.getCurrentItem() + 1))) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					} else if (list_little.contains(String.valueOf(wv_month
							.getCurrentItem() + 1))) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					} else {
						if ((year_num % 4 == 0 && year_num % 100 != 0)
								|| year_num % 400 == 0)
							wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						else
							wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (isLinter) {
					int month_num = newValue + 1;
					// 判断大小月及是否闰年,用来确定"日"的数据
					if (list_big.contains(String.valueOf(month_num))) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					} else if (list_little.contains(String.valueOf(month_num))) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					} else {
						if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
								.getCurrentItem() + START_YEAR) % 100 != 0)
								|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
							wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						else
							wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		};

		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		textSize = (screenheight / 100) * 4;
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

	}

	public String getTime() {
		StringBuffer sb = new StringBuffer();
		sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
				.append((wv_month.getCurrentItem() + 1)).append("-")
				.append((wv_day.getCurrentItem() + 1));
		return sb.toString();
	}

	public int getYear() {

		return (wv_year.getCurrentItem() + START_YEAR);
	}

	public int getMonth() {
		return (wv_month.getCurrentItem() + 1);
	}

	public int getDay() {
		return (wv_day.getCurrentItem() + 1);
	}

	public int getHours() {
		return (wv_hours.getCurrentItem());
	}

	public int getMin() {
		return (wv_mins.getCurrentItem());
	}
}
