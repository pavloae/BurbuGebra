package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

public abstract class Bubble implements IBubble {

    private int left, top;

    protected Bitmap bitmap;

    private float centerX;
    private float centerY;
    private float radius;
    public boolean isBursted;
    protected boolean isPressed;

    protected Bubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    @Override
    public IBubble setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
        return this;
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
    public Bitmap getBubbleBitmap(){
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

        if (getBubbleBitmap() == null)
            return;

        bitmap = Bitmap.createScaledBitmap(getBubbleBitmap(), 2 * (int) radius, 2 * (int) radius, true);
    }

    @Override
    public void setBubbleBitmap(Bitmap bubbleBitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bubbleBitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
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
        if (getBubbleBitmap() != null && !isBursted)
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
    public void updateBubble(){
        left = (int) (getCenterX() - getRadius());
        top = (int) (getCenterY() - getRadius());
        bitmap = getBubbleBitmap();
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public void setBursted(boolean isBursted) {
        this.isBursted = isBursted;
    }

    @Override
    public void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble) {

        if (bitmap == null || getRadius() <= 0 || getBubbleBitmap() == null)
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
                getBubbleBitmap().getWidth(), getBubbleBitmap().getHeight(), getBubbleBitmap().getConfig()
        );

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(getBubbleBitmap(), new Matrix(), null);
        canvas.drawBitmap(
                bitmap,
                (getBubbleBitmap().getWidth() - bitmap.getWidth()) / 2,
                (getBubbleBitmap().getHeight() - bitmap.getHeight()) / 2 ,
                null);

        setBubbleBitmap(bmOverlay);

    }


}
