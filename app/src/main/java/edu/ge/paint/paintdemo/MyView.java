package edu.ge.paint.paintdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Ge on 2015/5/26.
 */

public class MyView extends View {

    Bitmap backgroundBitmap;
    Canvas backgroundCanvas;
    Bitmap myBitmap;
    Canvas myCanvas;
    Bitmap backgroundBackup;

    Paint myPaint;
    Path myPath;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        myPath = new Path();

        myPaint = new Paint();

        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.BLACK);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStrokeWidth(5);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        //backgroundCanvas = new Canvas();
        //backgroundBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        //backgroundCanvas.setBitmap(backgroundBitmap);

        myCanvas = new Canvas();
        myBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        myCanvas.setBitmap(myBitmap);

        //set background color; we don't want it's opaque;
        //backgroundCanvas.drawColor(Color.WHITE);
        //backgroundBackup = Bitmap.createBitmap(backgroundBitmap);
        //mergeLayers();
        //postInvalidate();
    }


    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(myBitmap,0,0,null);
    }

    private float pointX, pointY;
    private static final float OFFSET_TOLERANCE = 4;

    private void actionDown(float x, float y){
        myPath = new Path();
        myPath.moveTo(x,y);
        pointX = x;
        pointY = y;
        myCanvas.drawPath(myPath,myPaint);
    }

    private void actionMove(float x, float y){
        float dx = Math.abs(x - pointX);
        float dy = Math.abs(y - pointY);
        if (dx >= OFFSET_TOLERANCE || dy >= OFFSET_TOLERANCE){
            myPath.quadTo(pointX, pointY, (x + pointX) / 2, (y + pointY) / 2);
            pointX = x;
            pointY = y;
            myCanvas.drawPath(myPath,myPaint);
        }
    }

    private void actionUp(float x, float y){
        myPath.quadTo(pointX, pointY, (x + pointX) / 2, (y + pointY) / 2);
        myCanvas.drawPath(myPath,myPaint);
    }

    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                actionDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(x,y);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(x,y);
                break;
        }
        postInvalidate();
        return true;
    }

    public void setPenColor(int color){
        myPaint.setColor(color);
    }


}