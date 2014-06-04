package com.rep.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragemntMainActivity extends FragmentActivity implements
		OnClickListener {
	private Button addFirstFragmentBtn;
	private Button replaceFirstFragmentBtn;
	private Button removeFirstFragmentBtn;
	private FirstFragment firstFragment;
	private SecondFragment secondFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_fragment);
		addFirstFragmentBtn = (Button) findViewById(R.id.main_btn_add);
		replaceFirstFragmentBtn = (Button) findViewById(R.id.main_btn_replace);
		removeFirstFragmentBtn = (Button) findViewById(R.id.main_btn_remove);
		addFirstFragmentBtn.setOnClickListener(this);
		replaceFirstFragmentBtn.setOnClickListener(this);
		removeFirstFragmentBtn.setOnClickListener(this);
		addFirstFragmentBtn.setEnabled(true);
		removeFirstFragmentBtn.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		if (v == addFirstFragmentBtn) {
			addFirstFragmentBtn.setEnabled(false);
			removeFirstFragmentBtn.setEnabled(true);
			if (findViewById(R.id.main_container) != null) {
				firstFragment = new FirstFragment();
				FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.main_container, firstFragment);
				// 把当前Fragment添加至回退栈，通过返回键返回时可以导航到上一个Fragment状态
				transaction.addToBackStack(null);
				// 提交
				transaction.commit();
			}
		}
		if (v == replaceFirstFragmentBtn) {
			addFirstFragmentBtn.setEnabled(true);
			removeFirstFragmentBtn.setEnabled(false);
			secondFragment = new SecondFragment();
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.main_container, secondFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		if (v == removeFirstFragmentBtn) {
			addFirstFragmentBtn.setEnabled(true);
			removeFirstFragmentBtn.setEnabled(false);
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.remove(firstFragment);
			transaction.remove(secondFragment);
			// 如果移除的时候也添加到回退栈，表示当前Fragment只是被停止而没有被摧毁，返回时它将恢复；
			// 那么如果不添加到回退栈则表示完全摧毁
			// transaction.addToBackStack(null);
			transaction.commit();
		}
	}
}