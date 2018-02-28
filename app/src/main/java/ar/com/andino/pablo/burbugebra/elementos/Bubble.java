package ar.com.andino.pablo.burbugebra.elementos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ar.com.andino.pablo.burbugebra.R;

public abstract class Bubble {

    public static final float RADIUS_DEFAULT = 20;

    public static SoundPool bubbleSoundPool;
    public static Integer popID;

    protected static Bitmap bitmapDefault;

    private float centerX, centerY;
    private float radius = RADIUS_DEFAULT;
    private Bitmap bitmapBubble;
    boolean isExploited;

    Bubble(Context context){
        context = context.getApplicationContext();
        if (bitmapDefault == null)
            bitmapDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_blue);
        if (bubbleSoundPool == null)
            bubbleSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        if (popID == null)
            popID = bubbleSoundPool.load(context, R.raw.pop1, 1);
        bitmapBubble = bitmapDefault;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getRadius() {
        return radius;
    }

    protected void setRadius(float radius) {
        this.radius = radius;
        if (radius <= 0) {
            bitmapBubble = null;
            return;
        }
        bitmapBubble = Bitmap.createScaledBitmap(bitmapBubble, 2 * (int) radius, 2 * (int) radius, true);
    }

    public boolean isExploited() {
        return isExploited;
    }

    public void onScreenPressed(float xCoor, float yCoor) {
        double distancia = Math.sqrt(Math.pow(Math.abs(xCoor-centerX), 2)+Math.pow(Math.abs(yCoor-centerY), 2));
        if (distancia <= radius)
            onPressed();
    }

    public void newBitmap(Bitmap bitmap) {
        bitmapBubble = Bitmap.createScaledBitmap(bitmap, 2 * (int) radius, 2 * (int) radius, true);
    }

    public void bitmapToBubble(@Nullable Bitmap bitmap){

        if (bitmap == null){
            setBitmapDefault();
            return;
        }

        int bitmapRadius = (int) (0.5 * Math.sqrt(
                Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2))
        );

        float scale = radius / bitmapRadius;

        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                (int) (scale * bitmap.getWidth()),
                (int) (scale * bitmap.getHeight()),
                true
        );

        overlay(bitmap);

    }

    public void bubbleToBitmap(@Nullable Bitmap bitmap) {

        if (bitmap == null){
            setBitmapDefault();
            return;
        }

        setRadius(
                0.5f * (float) Math.sqrt(
                        Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2)
                )
        );

        overlay(bitmap);

    }

    public void onDraw(Canvas canvas) {
        if (bitmapBubble == null)
            return;
        canvas.drawBitmap(bitmapBubble, centerX - radius, centerY - radius, null);
    }

    protected void setBitmapDefault() {
        if (radius <= 0)
            radius = RADIUS_DEFAULT;
        bitmapBubble = Bitmap.createScaledBitmap(bitmapDefault, 2 * (int) radius, 2 * (int) radius, true);
    }

    private void overlay(@NonNull Bitmap bitmap) {
        Bitmap bmOverlay = Bitmap.createBitmap(
                bitmapBubble.getWidth(), bitmapBubble.getHeight(), bitmapBubble.getConfig()
        );

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bitmapBubble, new Matrix(), null);
        canvas.drawBitmap(
                bitmap,
                (bitmapBubble.getWidth() - bitmap.getWidth()) / 2,
                (bitmapBubble.getWidth() - bitmap.getHeight()) / 2,
                null);
        this.bitmapBubble = bmOverlay;
    }

    public abstract void onPressed();

    public abstract void plump();

}
