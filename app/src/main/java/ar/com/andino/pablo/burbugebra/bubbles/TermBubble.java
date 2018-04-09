package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class TermBubble extends Term implements InterfazBurbuja {


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
    public void setCenterX(float centerX) {

    }

    @Override
    public void setCenterY(float centerY) {

    }

    @Override
    public void setRadius(float radius) {

    }

    @Override
    public void setBitmap(Bitmap bitmap) {

    }

    @Override
    public boolean isTouched(float xCoor, float yCoor) {
        return false;
    }

    @Override
    public boolean isBursted() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void onDraw(Canvas canvas) {

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
