package com.app.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author xiaowenwu
 * @version 创建时间：2019-11-11
 * 类说明
 */
public class WebViewProgressBar extends View {
	
	private static final int DEFAULT_COLOR_VALUE = Color.WHITE;
	
	private static final int DEFAULT_PROGRESS_VALUE = 0;
	
	private int color;
	
	private int progress;
	
	private Paint paint;

	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		
		if(progress < 0 || progress > 100){
			throw new IndexOutOfBoundsException("WebViewProgressBar progress out of bounds");
		}
		
		this.progress = progress;
		
		invalidate();
	}


	public int getColor() {
		return color;
	}


	public void setColor(int color) {
		this.color = color;
		
		invalidate();
	}


	public WebViewProgressBar(Context context, AttributeSet attrs,
							  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public WebViewProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public WebViewProgressBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		color = DEFAULT_COLOR_VALUE;
		
		progress = DEFAULT_PROGRESS_VALUE;
		
		paint = new Paint();
		
		paint.setAntiAlias(true);
		
		paint.setStyle(Style.FILL);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		float drawWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		
		float percent = ((float) progress) / 100f;
		
		float drawLength = drawWidth * percent;
		
		float drawXStart = getPaddingLeft();
		
		float drawXEnd = drawLength + drawXStart;
		
		float drawYStart = getPaddingTop();
		
		float drawYEnd = getHeight() - getPaddingBottom();
		
		paint.setColor(color);
		
		canvas.drawRect(drawXStart, drawYStart, drawXEnd, drawYEnd, paint);
	}

}
