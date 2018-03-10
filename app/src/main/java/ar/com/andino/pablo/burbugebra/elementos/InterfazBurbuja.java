package ar.com.andino.pablo.burbugebra.elementos;

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

    float getBubbleRadius();

    SpritesBubble getSpritesBubbles();

    void setBubbleRadius(float radius);

    void setSpritesBubble(@NonNull SpritesBubble bitmap);

    void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble);

    boolean onTouchScreen(float xCoor, float yCoor);

    void onDraw(Canvas canvas);

    void onPressed();

    void onPlop();

}
