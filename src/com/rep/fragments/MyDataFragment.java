package com.rep.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jpushdemo.ExampleApplication;
import com.rep.app.R;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;
import com.rep.util.TextCleanTouch;
import com.rep.util.TextCleanWatcher;

/**
 * 我的资料
 * 
 * @author Administrator
 * 
 */
public class MyDataFragment extends BaseFragment {
	private OnMydataListener listener;

	public interface OnMydataListener {
		public void back();
	}

	private ActionBar head;
	private TextView userid_v;
	private TextView score_v;
	private EditText price;
	private TextView city_v;
	private EditText worktime;
	private EditText weekend;
	private EditText worknum;
	private EditText brandtype;
	private EditText brandname;
	private String phone;
	private ExampleApplication app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.mydata, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnMydataListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnMydataListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private float screenHeight, screenWidth;
	private Bundle b;
	private Button updateMydata;
	/**
	 * 初始化控件.
	 */
	private void init() {
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		b = getArguments();
		head = (ActionBar) findViewById(R.id.mydata_head);
		userid_v = (TextView) findViewById(R.id.userid_v);
		userid_v.setText(b.getString("phone"));
		price = (EditText) findViewById(R.id.price_v);
		price.setText(b.getString("price"));
		city_v = (EditText) findViewById(R.id.city_v);
		city_v.setText(b.getString("city"));
		worktime = (EditText) findViewById(R.id.worktime_v);
		worktime.setText(b.getString("worktime"));
		weekend = (EditText) findViewById(R.id.weekend_v);
		weekend.setText(b.getString("weekendNum"));
		worknum = (EditText) findViewById(R.id.worknum_v);
		worknum.setText(b.getString("workNum"));
		brandtype = (EditText) findViewById(R.id.brandtype_v);
		brandtype.setText(b.getString("brandType"));
		brandname = (EditText) findViewById(R.id.brandname_v);
		brandname.setText(b.getString("brandName"));
		score_v = (TextView) findViewById(R.id.score_v);
		score_v.setText("待计算");
		updateMydata = (Button) findViewById(R.id.updateMydata);
		head.init(R.string.about4, true, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));
		head.setLeftAction(new AbstractAction(R.drawable.back) {
			@Override
			public void performAction(View view) {
				// 调用父亲acitivty中的回退操作.
				listener.back();
			}
		});
		addCleanBtn(price);
		addCleanBtn(worktime);
		addCleanBtn(weekend);
		addCleanBtn(worknum);
		addCleanBtn(brandtype);
		addCleanBtn(brandname);
	}

	public void addCleanBtn(EditText et) {
		final Resources res = getResources();
		Drawable mIconSearchClear = res
				.getDrawable(R.drawable.txt_search_clear);
		et.addTextChangedListener(new TextCleanWatcher(et, mIconSearchClear,
				null));
		et.setOnTouchListener(new TextCleanTouch(et));
	}

}