package ar.com.andino.pablo.burbugebra.bubbles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

public abstract class Bubble implements InterfazBurbuja {

    private static Paint textPaint = new Paint();
    private static Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);

    static {
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(typeface);
    }

    private int left, top;
    private float centerX;
    private float centerY;
    private float radius;
    private Bitmap bitmap;
    public boolean isBursted;
    public boolean isPressed;

    protected Bubble(){
    }

    protected Bubble(Bitmap bitmap, float centerX, float centerY, float radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    @Override
    public float getCenterX() {
        return centerX;
    }

    @Override
    public float getCenterY() {
        return centerY;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public Bitmap getBitmap(){
        return bitmap;
    };

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public void setBursted(boolean isBursted) {
        this.isBursted = isBursted;
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
        if (radius <= 0) {
            this.bitmap = null;
            return;
        }

        if (getBitmap() == null)
            return;

        bitmap = Bitmap.createScaledBitmap(getBitmap(), 2 * (int) radius, 2 * (int) radius, true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    public void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble) {

        if (bitmap == null || getRadius() <= 0 || getBitmap() == null)
            return;

        if (scaleToBubble){

            int bitmapRadius = (int) (0.5 * Math.sqrt(
                    Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2))
            );

            bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    (int) (getRadius() / bitmapRadius * bitmap.getWidth()),
                    (int) (getRadius() / bitmapRadius * bitmap.getHeight()),
                    true
            );

        } else {

            setRadius(
                    0.5f * (float) Math.sqrt(
                            Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2)
                    )
            );

        }

        Bitmap bmOverlay = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), getBitmap().getConfig()
        );

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(getBitmap(), new Matrix(), null);
        canvas.drawBitmap(
                bitmap,
                (getBitmap().getWidth() - bitmap.getWidth()) / 2,
                (getBitmap().getHeight() - bitmap.getHeight()) / 2 ,
                null);

        setBitmap(bmOverlay);

    }

    @Override
    public boolean isTouched(float screenX, float screenY) {
        return Math.sqrt(
                Math.pow(Math.abs(screenX-getCenterX()), 2) +
                        Math.pow(Math.abs(screenY-getCenterY()), 2)
        ) <= getRadius();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getBitmap() != null && !isBursted)
            canvas.drawBitmap(getBitmap(), left, top, null);
    }

    protected void update(){
        left = (int) (getCenterX() - getRadius());
        top = (int) (getCenterY() - getRadius());
        bitmap = getBitmap();
    }

    @Override
    @CallSuper
    public void onPressed() {
        setPressed(true);
    }

    @Override
    public void onPlop(){
        isBursted = true;
    }

    public static void setTextToBitmap(String text, Bitmap bitmap, @Nullable Typeface typeface) {

        if (typeface != null)
            textPaint.setTypeface(typeface);

        Rect bound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bound);
        int width = bound.width();
        int height = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);

        //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(
                text,
                canvas.getWidth()/2,
                canvas.getHeight()/2 - textPaint.ascent() / 2 - textPaint.descent() / 2,
                textPaint
        );

    }

}
