package com.example.henry.relojcanvas_grafico2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Henry on 03/05/2018.
 */

public class ClockView extends View {

    private int altura,anchura = 0;
    private int padding = 0;
    private int fuente = 0;
    private int espacionumeros = 0;
    private int TruncaManecilla,HoraTruncaManecilla = 0;
    private int radio = 0;
    private Paint paint;
    private boolean isInit;
    private int[] numeros = {1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect = new Rect();

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initClock(){
        altura = getHeight();
        anchura = getWidth();
        padding = espacionumeros + 50;
        fuente = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
                getResources().getDisplayMetrics());
        int min = Math.min(altura, anchura);
        radio = min / 2 - padding;
        TruncaManecilla = min / 20;
        HoraTruncaManecilla = min / 7;
        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);
        drawCenter(canvas);
        drawNumeral(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }

    private void drawHand(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handradio = isHour ? radio - TruncaManecilla - HoraTruncaManecilla : radio - TruncaManecilla;
        canvas.drawLine(anchura / 2, altura / 2,
                (float) (anchura / 2 + Math.cos(angle) * handradio),
                (float) (altura / 2 + Math.sin(angle) * handradio),
                paint);
    }
    private void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHand(canvas, (hour + c.get(Calendar.MINUTE)) * 5f, true);
        drawHand(canvas, c.get(Calendar.MINUTE), false);
        drawHand(canvas, c.get(Calendar.SECOND), false);
    }

    private void drawNumeral(Canvas canvas) {
        paint.setTextSize(fuente);

        for (int number : numeros) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (anchura / 2 + Math.cos(angle) * radio - rect.width() / 2);
            int y = (int) (altura / 2 + Math.sin(angle) * radio + rect.height() / 2);
            canvas.drawText(tmp, x, y, paint);
        }
    }

    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(anchura / 2, altura / 2, 20, paint);
        canvas.drawCircle(anchura / 2, altura / 2, 35, paint);
    }

    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(anchura / 2, altura / 2, radio + padding - 10, paint);
        canvas.drawCircle(anchura / 2, altura / 2, radio + padding - 1, paint);
    }
}
