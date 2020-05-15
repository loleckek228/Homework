package com.geekbrains.android.homework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ThermometerView extends View {
    private float thermometerCircleRadius, thermometerRectRadius;
    private Paint thermometerPaint;

    private float innerCircleRadius, innerRectRadius;
    private Paint innerPaint;

    private float temperatureCircleRadius, temperatureRectRadius;
    private Paint temperaturePaint;

    private Paint degreePaint, graduationPaint;

    private static final int GRADUATION_TEXT_SIZE = 5;
    private static float DEGREE_WIDTH = 30;
    private static final int NB_GRADUATIONS = 8;
    private static final float MAX_TEMP = 50, MIN_TEMP = -90;
    private static final float RANGE_TEMP = 80;
    private int nbGraduations = NB_GRADUATIONS;
    private float maxTemp = MAX_TEMP;
    private float minTemp = MIN_TEMP;
    private float rangeTemp = RANGE_TEMP;
    private float currentTemp = MIN_TEMP;

    private Rect rect = new Rect();


    private int thermometerColor = Color.BLACK;
    private int innerColor = Color.GREEN;

    private int temperaturePositiveColor = Color.RED;
    private int temperatureNegativeColor = Color.BLUE;

    private RectF thermometerRect = new RectF();
    private RectF innerRect = new RectF();

    private int width = 0;
    private int height = 0;

    private int circleCenterX;
    private float circleCenterY;
    private float temperatureStartY;
    private float temperatureEffectStartY;
    private float temperatureEffectEndY;

    private final static int padding = 10;

    public ThermometerView(Context context) {
        super(context);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    public void setCurrentTemp(float currentTemp) {
        if (currentTemp > maxTemp) {
            this.currentTemp = maxTemp;
        } else if (currentTemp < minTemp) {
            this.currentTemp = minTemp;
        } else {
            this.currentTemp = currentTemp;
        }

        invalidate();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThermometerView,
                0, 0);

        thermometerCircleRadius = typedArray.getDimension(R.styleable.ThermometerView_radius, 20f);

        thermometerColor = typedArray.getColor(R.styleable.ThermometerView_thermometer_color, Color.BLACK);
        innerColor = typedArray.getColor(R.styleable.ThermometerView_inner_color, Color.GREEN);

        temperaturePositiveColor = typedArray.getColor(R.styleable.ThermometerView_temperature_positive_color, Color.RED);

        temperatureNegativeColor = typedArray.getColor(R.styleable.ThermometerView_temperature_negative_color, Color.BLUE);


        typedArray.recycle();
    }

    private void init() {
        thermometerPaint = new Paint();
        thermometerPaint.setColor(thermometerColor);
        thermometerPaint.setStyle(Paint.Style.FILL);

        innerPaint = new Paint();
        innerPaint.setColor(innerColor);
        innerPaint.setStyle(Paint.Style.FILL);

        temperaturePaint = new Paint();
        temperaturePaint.setStyle(Paint.Style.FILL);

        degreePaint = new Paint();
        degreePaint.setStrokeWidth(innerCircleRadius / 16);
        degreePaint.setColor(thermometerColor);
        degreePaint.setStyle(Paint.Style.FILL);

        graduationPaint = new Paint();
        graduationPaint.setColor(thermometerColor);
        graduationPaint.setStyle(Paint.Style.FILL);
        graduationPaint.setAntiAlias(true);
        graduationPaint.setTextSize(ScreenHelper.convertDpToPixels(GRADUATION_TEXT_SIZE, getContext()));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w - getPaddingLeft() - getPaddingRight();
        height = h - getPaddingTop() - getPaddingBottom();

        thermometerRectRadius = thermometerCircleRadius / 2;

        innerCircleRadius = thermometerCircleRadius - 5;
        innerRectRadius = thermometerRectRadius - 5;

        temperatureCircleRadius = innerCircleRadius - innerCircleRadius / 6;
        temperatureRectRadius = innerRectRadius - innerRectRadius / 6;

        DEGREE_WIDTH = innerCircleRadius / 8;

        circleCenterX = width / 2;
        circleCenterY = height - thermometerCircleRadius;

        float thermometerStartY = padding;
        float innerStartY = thermometerStartY + 5;

        temperatureEffectStartY = innerStartY + innerRectRadius + 10;
        temperatureEffectEndY = circleCenterY - thermometerCircleRadius - 10;
        float temperatureRectHeight = temperatureEffectEndY - temperatureEffectStartY;

        temperatureStartY = temperatureEffectStartY + (maxTemp - currentTemp) / rangeTemp * temperatureRectHeight;

        thermometerRect = new RectF(circleCenterX - thermometerRectRadius,
                thermometerStartY,
                circleCenterX + thermometerRectRadius,
                circleCenterY);

        innerRect = new RectF(circleCenterX - innerRectRadius,
                innerStartY,
                circleCenterX + innerRectRadius,
                circleCenterY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentTemp >= 0) {
            temperaturePaint.setColor(temperaturePositiveColor);
        } else {
            temperaturePaint.setColor(temperatureNegativeColor);
        }

        canvas.drawRoundRect(thermometerRect, thermometerRectRadius, thermometerRectRadius, thermometerPaint);
        canvas.drawCircle(circleCenterX, circleCenterY, thermometerCircleRadius, thermometerPaint);

        canvas.drawRoundRect(innerRect, innerRectRadius, innerRectRadius, innerPaint);
        canvas.drawCircle(circleCenterX, circleCenterY, innerCircleRadius, innerPaint);

        canvas.drawRect(circleCenterX - temperatureRectRadius, temperatureStartY,
                circleCenterX + temperatureRectRadius, circleCenterY, temperaturePaint);
        canvas.drawCircle(circleCenterX, circleCenterY, temperatureCircleRadius, temperaturePaint);

        float tmp = temperatureEffectStartY;
        float startGraduation = maxTemp;
        float inc = rangeTemp / nbGraduations;

        while (tmp <= temperatureEffectEndY) {
            canvas.drawLine(circleCenterX - thermometerRectRadius - DEGREE_WIDTH, tmp,
                    circleCenterX - thermometerRectRadius, tmp, degreePaint);

            String txt = ((int) startGraduation) + "°";

            graduationPaint.getTextBounds(txt, 0, txt.length(), rect);

            float textWidth = rect.width();
            float textHeight = rect.height();

            canvas.drawText((int) startGraduation + "°",
                    circleCenterX - thermometerRectRadius - DEGREE_WIDTH
                            - textWidth - DEGREE_WIDTH * 1.5f,
                    tmp + textHeight / 2, graduationPaint);

            tmp += (temperatureEffectEndY - temperatureEffectStartY) / nbGraduations;
            startGraduation -= inc;
        }
    }
}