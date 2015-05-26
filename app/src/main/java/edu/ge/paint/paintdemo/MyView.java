package edu.ge.paint.paintdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Ge on 2015/5/26.
 */

public class MyView extends View {

    Paint myPaint;
    Path myPath;
    ArrayList<Path> paths = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        myPath = new Path();
        initialPaint();
    }


    public void initialPaint(){
        myPaint = new Paint();

        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.BLACK);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStrokeWidth(5);
    }




    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (Path p:paths){
            canvas.drawPath(p,myPaint);
        }
        canvas.drawPath(myPath,myPaint);
    }

    private float pointX, pointY;
    private static final float OFFSET_TOLERANCE = 4;

    private void actionDown(float x, float y){
        myPath = new Path();
        myPath.moveTo(x,y);
        pointX = x;
        pointY = y;
    }

    private void actionMove(float x, float y){
        float dx = Math.abs(x - pointX);
        float dy = Math.abs(y - pointY);
        if (dx >= OFFSET_TOLERANCE || dy >= OFFSET_TOLERANCE){
            myPath.quadTo(pointX, pointY, (x + pointX) / 2, (y + pointY) / 2);
            pointX = x;
            pointY = y;
        }
    }

    private void actionUp(float x, float y){
        paths.add(myPath);
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
}