package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

public interface InterfazBurbuja {

    InterfazBurbuja setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius);

    float getCenterX();

    float getCenterY();

    float getRadius();

    Bitmap getBitmap();

    void setCenterX(float centerX);

    void setCenterY(float centerY);

    void setRadius(float radius);

    void setBitmap(Bitmap bitmap);

    boolean isTouched(float xCoor, float yCoor);

    boolean isBursted();

    void updateBubble();

    void onDraw(Canvas canvas);

    void onPressed();

    void onPlop();

    void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble);

}
