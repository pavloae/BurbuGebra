package ar.com.andino.pablo.burbugebra.elementos;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class BubbleMotion extends Bubble {

    public BubbleMotion(Context context){
        super(context);
        setCenterX(getOriginXY()[0]);
        setCenterY(getOriginXY()[1]);
        setRadius(getRadius());
        bitmapToBubble(getBitmap());
    }

    @Override
    public void onPressed() {

    }

    @Override
    public void plump() {
        isExploited = true;
        if (popID != null) {
            bubbleSoundPool.play(popID, 0.85f, 0.85f, 1, 0, 1f);
        }
        setRadius(0);
    }

    public float[] getOriginXY(){
        return new float[]{0f, 0f};
    }

    public float getRadius() {
        return RADIUS_DEFAULT;
    }

    public Bitmap getBitmap() {
        return null;
    }

    public abstract void onUpdatePosition();


}
