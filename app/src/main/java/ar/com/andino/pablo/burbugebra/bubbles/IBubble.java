package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

public interface IBubble {

    IBubble setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius);

    float getCenterX();

    float getCenterY();

    float getRadius();

    Bitmap getBubbleBitmap();

    void setCenterX(float centerX);

    void setCenterY(float centerY);

    void setRadius(float radius);

    void setBubbleBitmap(Bitmap bubbleBitmap);

    boolean isTouched(float xCoor, float yCoor);

    boolean isBursted();

    void updateBubble();

    void onDraw(Canvas canvas);

    void onPressed();

    void onPlop();

    void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble);

}
