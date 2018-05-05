package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.bubbles.BubbleGroup;
import ar.com.andino.pablo.burbugebra.bubbles.MemoryBubble;
import ar.com.andino.pablo.burbugebra.utils.Utils;

public class UnderSeaView extends View {

    public static final float T = 2000f;
    public static float radius;
    public static float A;
    public static float k;
    public static float w;

    private static Bitmap backGround;
    public static Bitmap backGroundPerdiste;
    private static int rows;
    private static int columns;
    private int[] randomNumbers;

    public final BubbleGroup<MemoryBubble> bubbleGrid = new BubbleGroup<>();
    public static Bitmap bitmapBubble;

    public UnderSeaView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public UnderSeaView(Context context, int rows, int columns) {
        super(context);
        backGroundPerdiste = null;
        backGround = BitmapFactory.decodeResource(getResources(), R.drawable.background_undersea);
        bitmapBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
        MemoryBubble.hiddenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
        UnderSeaView.rows = rows;
        UnderSeaView.columns = columns;
        randomNumbers = getRandomNumbers();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        backGround = Bitmap.createScaledBitmap(backGround, w, h, false);
        //Utils.setTextToBitmap("2·x-8:2=4", 100, getWidth()/2, (int) (0.3 * getHeight()), backGround, Typeface.createFromAsset(getContext().getAssets(), "comic_sans_ms_bold.ttf"));
        radius = Math.min(getWidth() / columns, getHeight() / rows) * 0.40f;
        A = radius / 10f;
        k = (float) (2 * Math.PI / getWidth());
        UnderSeaView.w = (float) (2.0 * Math.PI / T);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (backGroundPerdiste != null){
            canvas.drawBitmap(backGroundPerdiste, 0, 0, null);
            return;
        }
        canvas.drawBitmap(backGround, 0, 0, null);
        bubbleGrid.draw(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void initBubbles() {
        MemoryBubble.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "comic_sans_ms_bold.ttf"));
        for (int position = 0; position < (rows * columns); position++){
            bubbleGrid.addBubble(
                    new MemoryBubble(
                            bitmapBubble,
                            position % columns * (float) getWidth() / columns + getWidth() / (2.0f * columns),
                            position / columns * (float) getHeight() / rows + getHeight() / (2.0f * rows),
                            radius
                    ) {

                        long t0, t;
                        float yf;
                        float fase;

                        @Override
                        public float getBubbleCenterY() {

                            if (t0 == 0.0f) {
                                t0 = System.currentTimeMillis();
                                fase = (float) ((w * t) / k + 2 * Math.PI * super.getBubbleCenterX() / getWidth() / k);
                                yf = super.getBubbleCenterY();
                            }

                            t = System.currentTimeMillis() - t0;

                            return (float) (yf - A * Math.sin(w * t - k * fase));

                        }

                        @Override
                        public boolean isTouched(float screenX, float screenY) {
                            if (isPressed)
                                onPlop();
                            return super.isTouched(screenX, screenY);
                        }

                        @Override
                        public void onPressed() {
                            super.onPressed();
                            updateBubbleParams();

                        }


                    }.setNumber(randomNumbers[position])
            );
        }



    }

    private int[] getRandomNumbers() {

        int randomCant = rows * columns / 2;
        int limitInf = -2 * randomCant;
        int limitSup = 2 * randomCant;

        int[] singlesRandom = Utils.createRandomNumbers(randomCant, limitInf, limitSup, false);
        int[] assignArray = new int[randomCant];

        int position;
        boolean asignated;
        int[] totalListRandom = new int[2 * randomCant];
        for (int totalPosition = 0; totalPosition < totalListRandom.length; totalPosition++){
            position = (int) (Math.random() * (randomCant - 1));
            asignated = false;

            do {

                if (assignArray[position] == 2) {
                    position = (position == assignArray.length - 1) ? 0 : position + 1;
                    continue;
                }

                totalListRandom[totalPosition] = singlesRandom[position];
                assignArray[position] = assignArray[position] + 1;
                asignated = true;

            } while (!asignated);

        }
        return totalListRandom;
    }



}
