package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

public interface IBubble {

    IBubble setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius);

    float getBubbleCenterX();

    float getBubbleCenterY();

    float getBubbleRadius();

    Bitmap getBubbleBitmap();

    void updateBubbleBitmap();

    void setBubbleCenterX(float centerX);

    void setBubbleCenterY(float centerY);

    void setBubbleRadius(float radius);

    void setBubbleBitmap(Bitmap bubbleBitmap);

    boolean isTouched(float xCoor, float yCoor);

    boolean isBursted();

    void updateBubbleParams();

    void onDraw(Canvas canvas);

    void setPressed(boolean isPressed);

    //void setOnPosition(boolean onPosition);

    void onPressed();

    void onPlop();

    void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble);

}
