package com.rep.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {
	private final Paint paint;
	private final Context context;
	private int ringWidth = 30;
	private float score = 40.0f;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	private int textSize1 = 30, textSize2 = 50;

	public RingView(Context context) {
		this(context, null);
	}

	public void reset() {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// delay some minutes you desire.
				/*
				 * try { Thread.sleep(3000); } catch (InterruptedException e) {
				 * }
				 */
				handler.post(new Runnable() {
					public void run() {
						invalidate();
					}
				});
			}
		}).start();
	}

	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.MyRing);
		score = a.getFloat(R.styleable.MyRing_score, 40.0f);
		ringWidth = a.getInteger(R.styleable.MyRing_ringWidth, 30);
		System.out.println("自定义属性:" + ringWidth);
		textSize1 = a.getInteger(R.styleable.MyRing_textSize1, 30);
		textSize2 = a.getInteger(R.styleable.MyRing_textSize2, 50);
		this.paint = new Paint();
		this.paint.setAntiAlias(true); // 消除锯齿
		// this.paint.setStyle(Paint.Style.STROKE); //
		// 绘制空心圆(在画环形的时候要空心，写文字的时候，不需要！)
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int center = getWidth() / 2;
		int centerH = getHeight() / 2;
		int rw = dip2px(context, ringWidth); // 设置圆环宽度
		// 先写两行文字
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);
		// 一定注意！！！这里画文字的时候，要设置为填充！！
		this.paint.setStyle(Paint.Style.FILL);
		canvas.drawText("得分", center - textSize1, centerH - textSize1, paint);
		paint.setColor(Color.RED);
		canvas.drawText(score + "分", center - (int) (textSize2), centerH + 10,
				paint);
		// 绘制圆环，两端圆环，注意设置为空心！！
		this.paint.setStyle(Paint.Style.STROKE); // 绘制空心圆
		this.paint.setARGB(255, 255, 0, 0);
		// 设置画笔的宽度
		this.paint.setStrokeWidth(rw);
		RectF oval1 = new RectF(ringWidth, ringWidth, getWidth() - ringWidth,
				getHeight() - ringWidth);
		// 计算圆弧的度数
		int end = (int) (score / 100.0 * 360.0);
		// 先画一段圆弧，角度从12点方向开始画起，角度为-90度
		canvas.drawArc(oval1, -90, end, false, paint);

		this.paint.setARGB(155, 170, 204, 63);
		this.paint.setStrokeWidth(rw);
		// 再补全另外一段圆弧
		canvas.drawArc(oval1, -90 + end, 360 - end, false, paint);

		super.onDraw(canvas);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
