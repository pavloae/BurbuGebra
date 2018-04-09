package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface InterfazBurbuja {

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

    void update();

    void onDraw(Canvas canvas);

    void onPressed();

    void onPlop();

    void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble);

}
