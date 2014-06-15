package com.rep.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.util.ActionBar;
import com.rep.util.BaseFragment;
import com.rep.util.HistoryAdapter;
import com.rep.util.ActionBar.AbstractAction;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class HistoryFragment extends BaseFragment {
	private ActionBar head;
	private float screenHeight, screenWidth;
	private ListView historyList;

	public interface OnHistorySelectedListener {
		public void onHistorySelected(String indate);

		/**
		 * 页面的向右滑动进行页面的退回.
		 * 
		 * @param event
		 */
		public void leftBack(MotionEvent event);

		public void back();
	}

	private OnHistorySelectedListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.history, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listener = (OnHistorySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHistorySelectedListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	public void goHistory(String r) {
		listener.onHistorySelected(r);
	}

	private String userId;

	/**
	 * 初始化控件.
	 */
	private void init() {
		head = (ActionBar) findViewById(R.id.history_head);
		historyList = (ListView) findViewById(R.id.historyList);
		// 得到屏幕大小.
		float[] screen2 = getScreen2();
		screenHeight = screen2[1];
		screenWidth = screen2[0];
		Bundle b = getArguments();
		userId = b.getString("userId");
		System.out.println("当前查看的用户是：" + userId);
		head.init(R.string.history_title, true, false, false, false,
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
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		String[] names = { "2014-5-1", "2014-5-7", "2014-5-14", "2014-5-21" };
		for (String s : names) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("indate", s);
			listItem.add(m);
		}
		HistoryAdapter adapter = new HistoryAdapter(listItem, getActivity());
		historyList.setAdapter(adapter);
		// 去掉分割线。。
		historyList.setDivider(null);
		historyList.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				listener.leftBack(event);
				return false;
			}
		});
		historyList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							TextView txt = (TextView) arg1
									.findViewById(R.id.indate);
							goHistory(txt.getText().toString());
						}
					}
				});
	}
}