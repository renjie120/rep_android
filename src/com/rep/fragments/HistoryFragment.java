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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.rep.app.BaseActivity;
import com.rep.app.R;
import com.rep.bean.Result;
import com.rep.util.ActionBar;
import com.rep.util.ActionBar.AbstractAction;
import com.rep.util.BaseFragment;
import com.rep.util.HistoryAdapter;

/**
 * 历史数据
 * 
 * @author Administrator
 * 
 */
public class HistoryFragment extends BaseFragment {
	private static final String url = BaseActivity.HOST
			+ "/services/statisService!getStatisDates.do";
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

	private String userId, token;
	private static final int DIALOG_KEY = 0;
	private String[] dates;

	/**
	 * 根据json串返回的信息进行日期字符串的拼接.
	 * 
	 * @param json
	 */
	private void getStrArr(String json) {
		dates = null;
		JSONArray obj = JSON.parseArray(json);
		if (obj != null) {
			dates = new String[obj.size()];
			int i = 0;
			for (Object o : obj) {
				dates[i++] = o.toString();
			}
		}
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (String s : dates) {
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
		getActivity().removeDialog(DIALOG_KEY);
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

	/**
	 * 查询远程数据库返回数据进行处理.
	 */
	private void getList() {
		HttpUtils http = new HttpUtils();
		RequestParams p = new RequestParams();
		p.addBodyParameter("userId", userId);
		p.addBodyParameter("token", token);
		http.send(HttpRequest.HttpMethod.POST, url, p,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						getActivity().showDialog(DIALOG_KEY);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// resultText.setText(current + "/" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Result r = (Result) JSON.parseObject(
								responseInfo.result, Result.class);
						if (r.getErrorCode() == 0) {
							String _res = r.getData().toString();
							getStrArr(_res);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						getActivity().removeDialog(DIALOG_KEY);
					}
				});
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
		userId = b.getString("userId");
		token = b.getString("token"); 
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
		getList();
	}
}