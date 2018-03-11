package ar.com.andino.pablo.burbugebra.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpritesBubble implements SpritesInterface {

    private Bitmap sprites;
    private int numFrames;
    private boolean loop;
    public boolean dispose;
    private float relativeX, relativeY, relativeR;
    private Rect sourceR;
    private int spriteW, spriteH;
    private int xPos;
    private int yPos;
    private int fps;
    private long frameTimer;
    private int currentFrame;


    public SpritesBubble(
            Bitmap sprites, int numFrames, boolean loop,
            float relativeX, float relativeY, float relativeR
    ) {

        this.sprites = sprites;
        this.numFrames = numFrames;
        this.loop = loop;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.relativeR = relativeR;

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

    public boolean isLoop() {
        return loop;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int value) {
        xPos = value - (spriteW/2);
    }

    public void setYPos(int value) {
        yPos = value - (spriteH/2);
    }

    @Override
    public int getSpritesNumber() {
        return numFrames;
    }

    public void update(long gameTime) {
        if( gameTime > frameTimer + fps) {
            frameTimer = gameTime;
            currentFrame += 1;

            if( currentFrame >= numFrames ) {
                currentFrame = 0;

                if(!loop) dispose = true;
            }

            sourceR.left = currentFrame * spriteW;
            sourceR.right = sourceR.left + spriteW;
        }
    }

    public void draw(Canvas canvas) {
        Rect destR = new Rect(getXPos(), getYPos(), getXPos() + spriteW,
                getYPos() + spriteH);
        canvas.drawBitmap(sprites, sourceR, destR, null);
    }

}
