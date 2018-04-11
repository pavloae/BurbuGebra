package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.bubbles.Bubble;
import ar.com.andino.pablo.burbugebra.bubbles.BubbleGroup;

public class IntroView extends View {

    static Bitmap background, logo;
    static int logoX, logoY;

    public static final float T = 1000f;
    public static float radioBubble;
    public static float A;
    public static float k;
    public static float w;
    public static float velocidad;
    public static float ye; // Profundidad relativa a la que explotan las burbujas
    public static float yf; // Profundidad relativa de flotación de las burbujas

    public static BubbleGroup optionBubbles = new BubbleGroup();
    public static BubbleGroup randomBubbles = new BubbleGroup();
    public static Bitmap bitmapBubble;

    public IntroView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_intro);
        logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);
        bitmapBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        background = Bitmap.createScaledBitmap(background, w, h, true);
        float scale = w * 0.7f / logo.getWidth();
        logo = Bitmap.createScaledBitmap(
                logo,
                (int) (logo.getWidth() * scale),
                (int) (logo.getHeight() * scale),
                true);
        logoX = (w - logo.getWidth()) / 2;
        logoY = h - logoX - logo.getHeight();

        radioBubble = 0.10f * getWidth();
        A = radioBubble / 5f;
        yf = (float) (0.7 * getHeight());
        ye = (float) (0.2 * getHeight());
        k = (float) (2 * Math.PI / getWidth());
        IntroView.w = (float) (2 * Math.PI / T);
        velocidad = (float) (2 * Math.PI * A / T);

        bitmapBubble = Bitmap.createScaledBitmap(
                bitmapBubble,
                2 * (int) radioBubble,
                2 * (int) radioBubble,
                true
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        if (optionBubbles != null)
            optionBubbles.draw(canvas);
        canvas.drawBitmap(logo, logoX, logoY, null);
        if (randomBubbles != null && !randomBubbles.isEmpty())
            randomBubbles.draw(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void initOptionsSprites() {

        optionBubbles.clear();

        optionBubbles.addBubble(
                new Bubble(
                        bitmapBubble,
                        0.125f * getWidth(),
                        getHeight(),
                        radioBubble
                ) {

                    boolean cycle;
                    float fase;
                    long t0, t;

                    @Override
                    public float getCenterY() {

                        if (t0 == 0.0f)
                            t0 = System.currentTimeMillis();

                        t = System.currentTimeMillis() - t0;

                        if (cycle)
                            return (float) (yf - A * Math.sin(w * t - k * fase));

                        if (super.getCenterY() - t * velocidad <= yf) {
                            fase = (w * t) / k;
                            cycle = true;
                        }

                        return super.getCenterY() - t * velocidad;

                    }

                }
        );
        optionBubbles.addBubble(
                new Bubble(
                        bitmapBubble,
                        0.375f * getWidth(),
                        (float) (getHeight() + 0.25 * T * velocidad),
                        radioBubble
                ) {

                    boolean cycle;
                    float fase;
                    long t0, t;

                    @Override
                    public float getCenterY() {

                        if (t0 == 0.0f)
                            t0 = System.currentTimeMillis();

                        t = System.currentTimeMillis() - t0;

                        if (cycle)
                            return (float) (yf - A * Math.sin(w * t - k * fase));

                        if (super.getCenterY() - t * velocidad <= yf) {
                            fase = (w * t) / k;
                            cycle = true;
                        }

                        return super.getCenterY() - t * velocidad;
                    }
                }
        );
        optionBubbles.addBubble(
                new Bubble(
                        bitmapBubble,
                        0.625f * getWidth(),
                        (float) (getHeight() + 0.50 * T * velocidad),
                        radioBubble
                ) {

                    boolean cycle;
                    float fase;
                    long t0, t;

                    @Override
                    public float getCenterY() {

                        if (t0 == 0.0f)
                            t0 = System.currentTimeMillis();

                        t = System.currentTimeMillis() - t0;

                        if (cycle)
                            return (float) (yf - A * Math.sin(w * t - k * fase));

                        if (super.getCenterY() - t * velocidad <= yf) {
                            fase = (w * t) / k;
                            cycle = true;
                        }

                        return super.getCenterY() - t * velocidad;
                    }
                }
        );
        optionBubbles.addBubble(
                new Bubble(
                        bitmapBubble,
                        0.875f * getWidth(),
                        (float) (getHeight() + 0.75 * T * velocidad),
                        radioBubble
                ) {

                    boolean cycle;
                    float fase;
                    long t0, t;

                    @Override
                    public float getCenterY() {

                        if (t0 == 0.0f)
                            t0 = System.currentTimeMillis();

                        t = System.currentTimeMillis() - t0;

                        if (cycle)
                            return (float) (yf - A * Math.sin(w * t - k * fase));

                        if (super.getCenterY() - t * velocidad <= yf) {
                            fase = (w * t) / k;
                            cycle = true;
                        }

                        return super.getCenterY() - t * velocidad;
                    }
                }
        );


        optionBubbles.getBubble(0).setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_fracciones),
                true
        );
        optionBubbles.getBubble(1).setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_operaciones),
                true
        );
        optionBubbles.getBubble(2).setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_ecuaciones),
                true
        );
        optionBubbles.getBubble(3).setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_sistemas),
                true
        );


    }

    public void initRandomSprite() {

        randomBubbles.addBubble(

                new Bubble(
                        bitmapBubble,
                        (float) (Math.random() * getWidth()),
                        (float) ((0.95 + 0.15 * Math.random()) * getHeight()),
                        (float) (0.035f + 0.075 * Math.random()) * getWidth()
                ) {

                    long t, t0;

                    @Override
                    public float getCenterY() {

                        if (t0 == 0.00f)
                            t0 = System.currentTimeMillis();

                        t = System.currentTimeMillis() - t0;

                        if (super.getCenterY() - velocidad * t * t / (20 * getRadius()) > ye)
                            return super.getCenterY() - velocidad * t * t / (20 * getRadius());

                        onPlop();
                        return 0;

                    }

                    @Override
                    public Bitmap getBubbleBitmap() {
                        return (t0 == 0) ? null : super.getBubbleBitmap();
                    }
                }
        );

    }

}
