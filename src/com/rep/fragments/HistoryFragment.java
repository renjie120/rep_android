package com.rep.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rep.app.R;
import com.rep.app.R.id;
import com.rep.app.R.layout;
import com.rep.app.R.string;
import com.rep.util.ActionBar;
import com.rep.util.BaseFragment;
import com.rep.util.HistoryAdapter;

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
	private long exitTime = 0;

	public interface OnHistorySelectedListener {
		public void onHistorySelected(String indate);

		public void back();
	}
	private OnHistorySelectedListener listener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("HomeGridviewFragement____onCreateView");
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

	/**
	 * 界面初始化函数.
	 */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.history);
//		
//		ActivityMeg.getInstance().addActivity(this);
//	}

	// private String userId;
	// private SharedPreferences mSharedPreferences;

	public void goHistory(String r) {
		listener.onHistorySelected(r);
	}
	
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
		// userId = b.getString("userId");
		head.init(R.string.history_title, false, false, false, false,
				(int) (screenHeight * barH));
		head.setTitleSize((int) (screenWidth * titleW4),
				(int) (screenHeight * titleH));

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
		historyList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						{
							TextView txt = (TextView) arg1
									.findViewById(R.id.indate);
							goHistory( txt.getText().toString());
//							Intent tent = new Intent(HistoryFragment.this,
//									IchartActivity.class);
//							tent.putExtra("inDate", txt.getText().toString());
//							startActivity(tent);
						}
					}
				});

		// Date d = new Date();
		// String today = SaveDataActivity.toDString(d, "yyyy-MM-dd");
		// mSharedPreferences = PreferenceManager
		// .getDefaultSharedPreferences(this);
		// if ("false".equals(mSharedPreferences.getString(today + "," + userId
		// + "_firstOpen", "false"))) {
		// mSharedPreferences.edit().putString(
		// today + "," + userId + "_firstOpen", "true");
		// Intent t = new Intent(HistoryActivity.this,
		// MyViewPagerActivity.class);
		// t.putExtras(b);
		// startActivity(t);
		// }
	}
}