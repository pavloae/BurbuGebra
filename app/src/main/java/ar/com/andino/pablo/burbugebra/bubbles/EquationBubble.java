package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;

public class EquationBubble extends Equation implements InterfazBurbuja {

    private int left, top;

    protected Bitmap bitmap;

    private float centerX;
    private float centerY;
    private float radius;
    public boolean isBursted;
    protected boolean isPressed;

    public EquationBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius) {
        super();
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
    public Bitmap getBitmap() {
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
    public boolean isTouched(float xCoor, float yCoor) {
        return Math.sqrt(
                Math.pow(Math.abs(xCoor-getCenterX()), 2) +
                        Math.pow(Math.abs(yCoor-getCenterY()), 2)
        ) <= getRadius();
    }

    @Override
    public boolean isBursted() {
        return isBursted;
    }

    @Override
    public void update() {
        left = (int) (getCenterX() - getRadius());
        top = (int) (getCenterY() - getRadius());
        bitmap = getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getBitmap() != null && !isBursted)
            canvas.drawBitmap(bitmap, left, top, null);
    }

    @Override
    public void onPressed() {

    }

    @Override
    public void onPlop() {

    }

    @Override
    public void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble) {

    }
}
