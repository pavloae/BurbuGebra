package ar.com.andino.pablo.burbugebra.elementos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import java.util.Random;

import ar.com.andino.pablo.burbugebra.R;

public class Bubble {

    private static Bitmap bitmapActive, bitmapPasive;

    public static MediaPlayer plumEffect;

    private float centerX, centerY;
    private float radius;
    private boolean selected;
    private Bitmap bitmap;

    public Bubble(Context context, float x, float y) {
        radius = 60 + new Random().nextFloat() * 300;
        centerX = (int) x;
        centerY = (int) y;
        if (bitmapActive == null)
            bitmapActive = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_black);
        if (bitmapPasive == null)
            bitmapPasive = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_green);
        if (plumEffect == null)
            plumEffect = MediaPlayer.create(context, R.raw.pop1);
        bitmap = Bitmap.createScaledBitmap(bitmapPasive, 2 * (int) radius, 2 * (int) radius, true);

        plumEffect.start();

    }

    public void onScreenPressed(float xCoor, float yCoor) {

        double distancia = Math.sqrt(Math.pow(Math.abs(xCoor-centerX), 2)+Math.pow(Math.abs(yCoor-centerY), 2));

        if (distancia <= radius && selected || distancia > radius && !selected)
            return;

        if (distancia <= radius && !selected){
            selected = true;
            bitmap = Bitmap.createScaledBitmap(bitmapActive, (int) radius, (int) radius, true);
            return;
        }

        if (distancia > radius && selected){
            selected = false;
            bitmap = Bitmap.createScaledBitmap(bitmapPasive, (int) radius, (int) radius, true);
        }

    }

    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, centerX - radius, centerY - radius, null);

    }

}
