package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public abstract class Bubble implements InterfazBurbuja {

    private int left, top;

    protected Bitmap bitmap;

    private float centerX;
    private float centerY;
    private float radius;
    public boolean isBursted;
    protected boolean isPressed;

    protected Bubble(){
    }

    protected Bubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius){
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
    }

    @Override
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    @Override
    public void setCenterY(float centerY) {
        this.centerY = centerY;
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

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    @Override
    public boolean isTouched(float screenX, float screenY) {
        return Math.sqrt(
                Math.pow(Math.abs(screenX-getCenterX()), 2) +
                        Math.pow(Math.abs(screenY-getCenterY()), 2)
        ) <= getRadius();
    }

    @Override
    public boolean isBursted() {
        return isBursted;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getBitmap() != null && !isBursted)
            canvas.drawBitmap(bitmap, left, top, null);
    }

    @Override
    public void onPressed() {
        setPressed(true);
    }

    @Override
    public void onPlop(){
        isBursted = true;
    }

    @Override
    public void update(){
        left = (int) (getCenterX() - getRadius());
        top = (int) (getCenterY() - getRadius());
        bitmap = getBitmap();
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public void setBursted(boolean isBursted) {
        this.isBursted = isBursted;
    }

    @Override
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


}
