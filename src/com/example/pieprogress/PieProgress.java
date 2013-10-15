package com.example.pieprogress;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class PieProgress extends View {

	private boolean mIndeterminate;
	private float startAngle = 0f;
	private int progress;
	private Handler h;
	private float drawTo = 12f;
    Timer t = null;
    TimerTask task = null;
    int color = Color.parseColor("#FF7C7C7C");
    private int Max = 100;
    
	public PieProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		h = new Handler();
	}
	
	public PieProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public PieProgress(Context context) {
		super(context);
	}
	
	public boolean getIndeterminate() {
		return this.mIndeterminate;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int value) {
		color = value;
	}
	
	public int getMax() {
		return Max;
	}
	
	public void setMax(int value) {
		Max = value;
	}
	
	boolean n = false;
	public void setIndeterminate(boolean value) {
		this.mIndeterminate = value;
		startAngle = 0f;
		drawTo = 0f;
		
    	final int TIMER_ONE_SECOND = 4;

        if (mIndeterminate) {
        t = new Timer();
        task = new TimerTask(){
			public void run() {
				if (drawTo < 360f && !n) {
					drawTo++;
				} else if (drawTo == 360f || n) {
					n = true;
					if (startAngle < 360f){
						drawTo--;
						startAngle++;
					} else if (startAngle == 360f) {
						startAngle = 0f;
						drawTo = 0f;
						n = false;
					}
				}
				h.post(new Runnable() {
					@Override
					public void run() {
						invalidate();
					}
					
				});

			}};
			t.scheduleAtFixedRate(task, 0, TIMER_ONE_SECOND);
        } else {
        	if (task != null)
        		task.cancel();
        	if (t != null) {
        		t.cancel();
        		t.purge();
        		t = null;
        	}
			startAngle = 0f;
			drawTo = 0f;
        }

		
	}
	
	public void setProgress(int value) {
		startAngle = 0f;
		this.progress = value;
		invalidate();
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Calculate the radius. Remember, we don't want it to outside the View.
        float center_x, center_y;
        float radius = 0;
		if (getHeight() < getWidth()) {
			radius = getHeight() / 2.4f;
		} else {
			radius = getWidth() / 2.4f;
		}
		//Set center points
		center_x = getWidth()/2;
		center_y = getHeight()/2;
        //Set up RectF
        RectF rect = new RectF();
        rect.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
        //Rotate the canvas to Angle = 0 is that the top
        canvas.rotate(-90f, rect.centerX(), rect.centerY());
        //Set up paint
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5f);

		//Draw circle that's our border
        canvas.drawCircle(rect.centerX(), rect.centerY(), radius, p);
        p = new Paint();
        p.setColor(color);
        if (this.mIndeterminate) {
        	canvas.drawArc(rect, startAngle, drawTo, true, p);
        } else {
        	float mdrawTo = (int)Math.round(((double)360/(double)Max) *progress);
        	canvas.drawArc(rect, startAngle, mdrawTo, true, p);
        }
	}

}
