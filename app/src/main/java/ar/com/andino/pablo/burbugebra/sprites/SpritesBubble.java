package ar.com.andino.pablo.burbugebra.sprites;

import android.graphics.Bitmap;

public class SpritesBubble implements SpritesInterface {

    private Bitmap[] bitmapArray;
    private boolean cyclic;
    private float relativeX, relativeY, relativeR;

    public SpritesBubble(Bitmap bitmap, int framesNumber, boolean cyclic, float relativeX, float relativeY, float relativeR) {

        this.bitmapArray = new Bitmap[framesNumber];
        this.cyclic = cyclic;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.relativeR = relativeR;
        int frameHeight = bitmap.getHeight();
        int frameWidth = bitmap.getWidth() / framesNumber;

        for (int frame = 0; frame < framesNumber; frame++){
            bitmapArray[frame] = Bitmap.createBitmap(bitmap, frame * frameWidth, 0, frameWidth, frameHeight);
        }

    }

    public float getRelativeX() {
        return relativeX;
    }

    public float getRelativeY() {
        return relativeY;
    }

    public float getRelativeR() {
        return relativeR;
    }

    public boolean isCyclic() {
        return cyclic;
    }

    @Override
    public Bitmap getFrame(int position, float bubbleRadius) {

        if (position < 0 || position >= bitmapArray.length)
            return null;

        Bitmap frame = bitmapArray[position];
        if (frame == null)
            return null;

        int frameR = Math.min(frame.getWidth(), frame.getHeight());
        float scale = bubbleRadius / relativeR / frameR;

        return Bitmap.createScaledBitmap(
                frame,
                (int) (scale * frame.getWidth()),
                (int) (scale * frame.getHeight()),
                false
        );

    }

}
