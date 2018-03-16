package ar.com.andino.pablo.burbugebra.elementos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.HashMap;

import ar.com.andino.pablo.burbugebra.bubbles.InterfazBurbuja;
import ar.com.andino.pablo.burbugebra.numeros.InterfazEntero;

public abstract class BurbujaEntero implements InterfazEntero, InterfazBurbuja {


    public static int scale = 120;

    Context context;
    boolean isShowing;
    int valor = 1;

    public float t = 0;
    final float A = 5;
    final float T = 1.5f;
    final float w = (float) (2 * Math.PI / T);

    public boolean isPressed;
    private boolean isExploited;

    public static BurbujaEntero getInstance(Context context, int entero) {

        return new BurbujaEntero(context, entero) {

            @Override
            public float getCenterX() {
                return 0;
            }

            @Override
            public float getCenterY() {
                return 0;
            }

            @Override
            public float getRadius() {
                return 0;
            }

            @Override
            public Bitmap getBitmap() {
                return null;
            }


            @Override
            public void setRadius(float radius) {

            }

            @Override
            public boolean isTouched(float xCoor, float yCoor) {
                return false;
            }


        };
    }

    public BurbujaEntero(Context context, int valor) {
        this.context = context;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int dividirPor(int divisor) {
        int resto = valor % divisor;
        valor = valor / divisor;
        return resto;
    }

    public static Bitmap getBitmap(String text) {

        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(scale);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Typeface typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD);
        textPaint.setTypeface(typeface);

        Rect bound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bound);
        int width = bound.width();
        int height = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(
                text,
                canvas.getWidth()/2,
                canvas.getHeight()/2 - textPaint.ascent() / 2 - textPaint.descent() / 2,
                textPaint
        );

        return bitmap;
    }

    public static HashMap<Integer, Integer> factoresPrimos(int valor) {

        @SuppressLint("UseSparseArrays")
        HashMap<Integer, Integer> factoresPrimos = new HashMap<>();

        valor = Math.abs(valor);

        int dividendo = Math.abs(valor);

        int limit;
        if (dividendo % 2 == 0){
            limit = dividendo / 2;
        } else {
            limit = (dividendo - 1) / 2;
        }

        for (int factor = 2 ; factor <= limit ; factor++) {

            if (dividendo % factor != 0)
                continue;

            int potencia = 0;

            while (dividendo % factor == 0){
                dividendo = dividendo / factor;
                potencia++;
            }

            factoresPrimos.put(factor, potencia);

        }

        return factoresPrimos;

    }

    /// Interfaz Bubble

    @Override
    public void onPressed() {
        //bubbleToBitmap(getBitmap(String.valueOf(valor)));
    }

    @Override
    public void onPlop() {
    }

    @Override
    public void onDraw(Canvas canvas) {
/*
        if (currentFrame == null)
            return;

        canvas.drawBitmap(
                currentFrame,
                getCenterX() - radiusInterfaz,
                (float) (getCenterY() - A * Math.sin(w * t)) - radiusInterfaz,
                null);*/
    }

    @Override
    public float getRadius() {
        return 0;
    }


    @Override
    public void setRadius(float radius) {

    }

    @Override
    public boolean isTouched(float xCoor, float yCoor) {
        return false;
    }

    /// Interfaz Entero

}
