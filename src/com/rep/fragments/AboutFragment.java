package com.rep.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rep.app.R;
import com.rep.util.ActionBar;
import com.rep.util.BaseFragment;

/**
 * 保存基础数据.
 * 
 * @author Administrator
 * 
 */
public class AboutFragment extends BaseFragment {
	private ActionBar head;
	private LinearLayout r1;
	private LinearLayout r2;
	private LinearLayout r3;
	private LinearLayout r4;
	private float screenHeight, screenWidth; 

	private OnAboutSelectedListener listener;

	public interface OnAboutSelectedListener {
		public void back();

		public void openDevice();

		public void openWodeziliao();

		public void openHuizhen();

		public void openZaixian();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnAboutSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnAboutSelectedListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPage();
	}

	private Bundle b;

	private void initPage() {
		head = (ActionBar) findViewById(R.id.about_head);
		r1 = (LinearLayout) findViewById(R.id.r1);
		r2 = (LinearLayout) findViewById(R.id.r2);
		r3 = (LinearLayout) findViewById(R.id.r3);
		r4 = (LinearLayout) findViewById(R.id.r4);
		b = getArguments();
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		head.init(R.string.about_title, false, false, false, true,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));

		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.openDevice();
			}
		});

		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.openZaixian();
			}
		});

		r3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.openHuizhen();
			}
		});

		r4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.openWodeziliao();
			}
		});
	}

}