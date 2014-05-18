package com.rep.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rep.util.ScrollLayout;
import com.rep.util.Tool;

public class ScrollLayoutActivity extends Activity implements
		OnViewChangeListener {

	private ScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private SharedPreferences settings;
	private int currentItem;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	private RelativeLayout ly;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		settings = getSharedPreferences(Tool.CONFIG, 0);
		String firstStart = settings.getString("firstStart", "false");
		if ("true".equals(firstStart)) {
			Intent intent = new Intent(ScrollLayoutActivity.this,
					LoginActivity.class);
			ScrollLayoutActivity.this.startActivity(intent);
			ScrollLayoutActivity.this.finish();
		} else {
			initView();
			settings.edit().putString("firstStart", "true").commit();
		}
	}

	private void initView() {
		ly = (RelativeLayout) findViewById(R.id.lastPage);
		mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		ly.setOnClickListener(onClick);
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}

	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ScrollLayoutActivity.this,
					LoginActivity.class);
			ScrollLayoutActivity.this.startActivity(intent);
			ScrollLayoutActivity.this.finish();
		}
	};

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}
}