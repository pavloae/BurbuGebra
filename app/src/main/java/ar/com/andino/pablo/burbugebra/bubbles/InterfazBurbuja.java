package ar.com.andino.pablo.burbugebra.bubbles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public interface InterfazBurbuja {

    float getCenterX();

    float getCenterY();

    float getRadius();

    Bitmap getBitmap();

    void setRadius(float radius);

    boolean isTouched(float xCoor, float yCoor);

    void onDraw(Canvas canvas);

    void onPressed();

    void onPlop();

}
