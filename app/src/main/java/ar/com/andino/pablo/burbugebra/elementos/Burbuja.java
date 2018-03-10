package ar.com.andino.pablo.burbugebra.elementos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public abstract class Burbuja implements InterfazBurbuja {

    private float centerX, centerY, radius;
    private float excX, excY;

    private SpritesBubble spritesBubble;
    private Bitmap currentFrame;
    private int left, top;

    private boolean pressed, bursted;

    protected Burbuja(SpritesBubble spritesBubble){
        this.spritesBubble = spritesBubble;
        radius = getBubbleRadius();
        centerX = getCenterX();
        centerY = getCenterY();
    }

    @Override
    public float getBubbleRadius() {
        return 100;
    }

    public void setCurrentFrame() {
        if (spritesBubble == null)
            return;
        currentFrame = spritesBubble.getFrame(0, radius);
    }

    protected float getCenterX() {
        return centerX;
    }

    protected float getCenterY() {
        return centerY;
    }

    protected void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    protected void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    @Override
    public void setBubbleRadius(float radius) {
        this.radius = radius;
        if (radius <= 0) {
            this.currentFrame = null;
            return;
        }

        if (currentFrame == null){
            currentFrame = spritesBubble.getFrame(0, radius);
        }

        currentFrame = Bitmap.createScaledBitmap(currentFrame, 2 * (int) radius, 2 * (int) radius, true);
    }

    @Override
    public void setSpritesBubble(@NonNull SpritesBubble bitmap) {
        if (radius <= 0)
            return;
        currentFrame = spritesBubble.getFrame(0, radius);
    }

    @Override
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

        if (currentFrame == null)
            return;

        Bitmap bmOverlay = Bitmap.createBitmap(
                currentFrame.getWidth(), currentFrame.getHeight(), currentFrame.getConfig()
        );

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(currentFrame, new Matrix(), null);
        canvas.drawBitmap(
                bitmap,
                (currentFrame.getWidth() - bitmap.getWidth()) / 2,
                (currentFrame.getHeight() - bitmap.getHeight()) / 2 ,
                null);

        this.currentFrame = bmOverlay;

    }

    @Override
    public boolean onTouchScreen(float xCoor, float yCoor) {
        double distancia = Math.sqrt(Math.pow(Math.abs(xCoor-centerX), 2)+Math.pow(Math.abs(yCoor-centerY), 2));
        if (distancia <= radius) {
            onPressed();
            return true;
        }
        pressed = false;
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (currentFrame == null)
            return;
        canvas.drawBitmap(currentFrame, centerX - radius, centerY - radius, null);
    }

    @Override
    @CallSuper
    public void onPressed() {
        pressed = true;
    }

    @Override
    public void onPlop(){
        bursted = true;
        setBubbleRadius(0);

    }

    public void updateBubblePosition(){
    }

}
