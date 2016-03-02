package com.example.android.sunshine.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

/**
 * Created by Valeri on 10.02.2016.
 */
public class NeedleView extends ImageView {

    private Paint mLinePaint;
    private Path mLinePath;
    private Paint mCirclePaint;

    private int mHight;
    private int mWidth ;
    private int xCenter;
    private int yCenter;

    public NeedleView(Context context) {
        super(context);
    }

    public NeedleView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public NeedleView(Context context, AttributeSet attr, int defaultStyle) {
        super(context, attr, defaultStyle);
    }

//    @Override
//    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
//        return super.dispatchPopulateAccessibilityEvent(event);
//        event.getText().add(widSpeedDir);
//        return true;
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init() {

        xCenter = mWidth/2;
        yCenter = mHight/2;

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(5.0f);
//        mLinePaint.setShadowLayer(8.0f, 0.1f, 0.1f, Color.GRAY);

        mLinePath = new Path();
//        mLinePath.moveTo(50.0f, 50.0f);
        mLinePath.moveTo(xCenter - 5, yCenter);
///        mLinePath.lineTo(130.0f, 40.0f);
        mLinePath.lineTo(xCenter, mHight/4);
        mLinePath.lineTo(xCenter + 5, yCenter);
        mLinePath.lineTo(xCenter, yCenter - mHight/4);
        mLinePath.lineTo(xCenter - 5, yCenter);
//        mLinePath.lineTo(600.0f, 50.0f);
//        mLinePath.lineTo(130.0f, 60.0f);
//        mLinePath.lineTo(50.0f, 50.0f);
        mLinePath.addCircle(xCenter, yCenter, 10.0f, Path.Direction.CW);
        mLinePath.close();

//        mCirclePaint = new Paint();
//        mCirclePaint.setColor(Color.BLACK);
//        mCirclePaint.setAntiAlias(true);
//        mCirclePaint.setShader(new RadialGradient(xCenter, yCenter, 10.0f,
//                Color.DKGRAY, Color.BLACK, Shader.TileMode.CLAMP));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mLinePath, mLinePaint);
    }

    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {

        int hSpecMode = MeasureSpec.getMode(hMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(hMeasureSpec);
        mHight = hSpecSize;

        int wSpecMode = MeasureSpec.getMode(wMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(wMeasureSpec);
        mWidth = wSpecSize;

        setMeasuredDimension(mWidth, mHight);
    }

}
