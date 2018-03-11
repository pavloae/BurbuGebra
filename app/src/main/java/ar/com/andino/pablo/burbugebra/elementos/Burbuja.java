package ar.com.andino.pablo.burbugebra.elementos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public abstract class Burbuja implements InterfazBurbuja {

    private float centerX;
    private float centerY;
    private float radius;
    private Bitmap bitmap;

    protected Burbuja(){
        super();
        centerX = getCenterX();
        centerY = getCenterY();
        radius = getRadius();
        bitmap = getBitmap();
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
        return 100;
    }

    @NonNull
    public abstract Bitmap getBitmap();

    @Override
    public void setBubbleRadius(float radius) {
        this.radius = radius;
        if (radius <= 0) {
            this.bitmap = null;
            return;
        }

        if (bitmap == null){
            //bitmap = spritesBubble.getSpriteBubble(0, radius);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, 2 * (int) radius, 2 * (int) radius, true);
    }

    public void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble) {

        if (bitmap == null || radius <= 0)
            return;

        if (scaleToBubble){

            int bitmapRadius = (int) (0.5 * Math.sqrt(
                    Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2))
            );

            bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    (int) (radius / bitmapRadius * bitmap.getWidth()),
                    (int) (radius / bitmapRadius * bitmap.getHeight()),
                    true
            );

        } else {

            setBubbleRadius(
                    0.5f * (float) Math.sqrt(
                            Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2)
                    )
            );

        }

        if (this.bitmap == null)
            return;

        Bitmap bmOverlay = Bitmap.createBitmap(
                this.bitmap.getWidth(), this.bitmap.getHeight(), this.bitmap.getConfig()
        );

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(this.bitmap, new Matrix(), null);
        canvas.drawBitmap(
                bitmap,
                (this.bitmap.getWidth() - bitmap.getWidth()) / 2,
                (this.bitmap.getHeight() - bitmap.getHeight()) / 2 ,
                null);

        this.bitmap = bmOverlay;

    }

    @Override
    public boolean onTouchScreen(float screenX, float screenY) {
        double distancia = Math.sqrt(Math.pow(Math.abs(screenX-centerX), 2)+Math.pow(Math.abs(screenY-centerY), 2));
        if (distancia <= radius) {
            onPressed();
            return true;
        }
        //pressed = false;
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (bitmap == null)
            return;
        canvas.drawBitmap(bitmap, centerX - radius, centerY - radius, null);
    }

    @Override
    @CallSuper
    public void onPressed() {
    }

    @Override
    public void onPlop(){
    }

    public void updateBubblePosition(){
        centerX = getCenterX();
        centerY = getCenterY();
    }

}
