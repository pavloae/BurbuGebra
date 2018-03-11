package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.elementos.Burbuja;

public class IntroView extends View {

    Bitmap background, logo;
    int logoX, logoY;

    IntroAnimation introAnimation;

    public Burbuja[] burbujasOpciones;
    public List<Burbuja> burbujasRandom;
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);
    private float velocidad;

    public final Object lock = new Object();

    public IntroView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_intro);
        logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        drawOptionSprites(canvas);
        canvas.drawBitmap(logo, logoX, logoY, null);
        drawRandomSprites(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void onResume(){
        if (introAnimation != null && introAnimation.animationIsActive) {
            introAnimation.animationIsActive = false;
            try {
                introAnimation.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        post(new Runnable() {
            @Override
            public void run() {
                introAnimation = new IntroAnimation();
                introAnimation.start();
            }
        });

    }

    public void onPause() {
        introAnimation.animationIsActive = false;
        try {
            introAnimation.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initOptionsSprites() {

        final float radioBubble = 0.1f * getWidth();
        final float T = 1000f;
        final float A = radioBubble / 5f;
        final float yf = (float) (0.7 * getHeight());
        final float k = (float) (2 * Math.PI / getWidth());
        final float w = (float) (2 * Math.PI / T);
        velocidad = (float) (2 * Math.PI * A / T);
        final Bitmap bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue),
                2 * (int) radioBubble,
                2 * (int) radioBubble,
                true
        );

        burbujasOpciones = new Burbuja[4];
        final float[][] initPositions = new float[][]{
                {0.125f * getWidth(), getHeight()},
                {0.375f * getWidth(), (float) (getHeight() + 0.25 * T * velocidad)},
                {0.625f * getWidth(), (float) (getHeight() + 0.50 * T * velocidad)},
                {0.875f * getWidth(), (float) (getHeight() + 0.75 * T * velocidad)}
        };

        burbujasOpciones[0] = new Burbuja() {

            boolean cycle;
            float fase;
            long t0, t;

            @Override
            public float getCenterX() {
                return initPositions[0][0];
            }

            @Override
            public float getCenterY() {

                if (t0 == 0.0f) {
                    t0 = System.currentTimeMillis();
                    return initPositions[0][1];
                }

                t = System.currentTimeMillis() - t0;

                if (cycle)
                    return (float) (yf - A * Math.sin(w * t - k * fase));

                if (super.getCenterY() <= yf) {
                    fase = (w * t) / k;
                    cycle = true;
                }

                return initPositions[0][1] - t * velocidad;

            }

            @Override
            public float getRadius() {
                return radioBubble;
            }

            @NonNull
            @Override
            public Bitmap getBitmap() {
                return bitmap;
            }

        };

        burbujasOpciones[1] = new Burbuja() {

            boolean cycle;
            float fase;
            long t0, t;

            @Override
            public float getCenterX() {
                return initPositions[1][0];
            }

            @Override
            public float getCenterY() {

                if (t0 == 0.0f) {
                    t0 = System.currentTimeMillis();
                    return initPositions[1][1];
                }

                t = System.currentTimeMillis() - t0;

                if (cycle)
                    return (float) (yf - A * Math.sin(w * t - k * fase));

                if (super.getCenterY() <= yf) {
                    fase = (w * t) / k;
                    cycle = true;
                }

                return initPositions[1][1] - t * velocidad;

            }

            @Override
            public float getRadius() {
                return radioBubble;
            }

            @NonNull
            @Override
            public Bitmap getBitmap() {
                return bitmap;
            }

        };

        burbujasOpciones[2] = new Burbuja() {

            boolean cycle;
            float fase;
            long t0, t;

            @Override
            public float getCenterX() {
                return initPositions[2][0];
            }

            @Override
            public float getCenterY() {

                if (t0 == 0.0f) {
                    t0 = System.currentTimeMillis();
                    return initPositions[2][1];
                }

                t = System.currentTimeMillis() - t0;

                if (cycle)
                    return (float) (yf - A * Math.sin(w * t - k * fase));

                if (super.getCenterY() <= yf) {
                    fase = (w * t) / k;
                    cycle = true;
                }

                return initPositions[2][1] - t * velocidad;

            }

            @Override
            public float getRadius() {
                return radioBubble;
            }

            @NonNull
            @Override
            public Bitmap getBitmap() {
                return bitmap;
            }

        };

        burbujasOpciones[3] = new Burbuja() {

            boolean cycle;
            float fase;
            long t0, t;

            @Override
            public float getCenterX() {
                return initPositions[3][0];
            }

            @Override
            public float getCenterY() {

                if (t0 == 0.0f) {
                    t0 = System.currentTimeMillis();
                    return initPositions[3][1];
                }

                t = System.currentTimeMillis() - t0;

                if (cycle)
                    return (float) (yf - A * Math.sin(w * t - k * fase));

                if (super.getCenterY() <= yf) {
                    fase = (w * t) / k;
                    cycle = true;
                }

                return initPositions[3][1] - t * velocidad;

            }

            @Override
            public float getRadius() {
                return radioBubble;
            }

            @NonNull
            @Override
            public Bitmap getBitmap() {
                return bitmap;
            }

        };

        burbujasOpciones[0].setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_fracciones),
                true
        );
        burbujasOpciones[1].setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_operaciones),
                true
        );
        burbujasOpciones[2].setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_ecuaciones),
                true
        );
        burbujasOpciones[3].setFillingBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.intro_sistemas),
                true
        );


    }

    public void initRandomSprites(int count) {

        synchronized (lock) {
            burbujasRandom = new ArrayList<>(count);

            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
            final float yf = 0.20f * getHeight();

            for (int i = 0 ; i < count ; i++){

                burbujasRandom.add(
                        new Burbuja() {

                            float x0, y0;
                            long t, t0;
                            float radius;

                            @Override
                            public float getCenterX() {
                                if (x0 == 0.00f)
                                    x0 = (float) (Math.random() * getWidth());
                                return x0;
                            }

                            @Override
                            public float getCenterY() {

                                if (y0 == 0.00f){
                                    y0 = (float) (0.95f * getHeight() + 0.25 * getHeight() * Math.random());
                                    return y0;
                                }

                                if (t0 == 0.00f)
                                    t0 = System.currentTimeMillis();

                                t = System.currentTimeMillis() - t0;

                                if (super.getCenterY() > yf)
                                    return y0 - 2 * velocidad * t;

                                onPlop();
                                return 0;

                            }

                            @Override
                            public float getRadius() {
                                if (radius == 0.00f)
                                    radius = (float) (30.0f + 60.0f * Math.random());
                                return radius;
                            }

                            @NonNull
                            @Override
                            public Bitmap getBitmap() {
                                return Bitmap.createScaledBitmap(bitmap, 2 * (int) getRadius(), 2 * (int) getRadius(), true);
                            }


                        }
                );
            }
        }


    }

    public void updateSprites() {
        if (burbujasOpciones != null)
            for (Burbuja burbuja : burbujasOpciones) {
                if (burbuja == null)
                    continue;
                burbuja.updateBubblePosition();
            }

        synchronized (lock) {
            if (burbujasRandom != null)
                for (Burbuja burbuja : burbujasRandom)
                    if (burbuja != null)
                        burbuja.updateBubblePosition();
        }

    }

    private void cleanSprites(){

        synchronized (lock) {
            if (burbujasRandom == null)
                return;

            List<Burbuja> burbujas = new ArrayList<>();
            for (Burbuja burbuja : burbujasRandom)
                if (burbuja != null && burbuja.isBursted)
                    burbujas.add(burbuja);

            burbujasRandom.removeAll(burbujas);
        }

    }

    private void drawOptionSprites(Canvas canvas) {
        if (burbujasOpciones != null)
            for (Burbuja burbuja : burbujasOpciones)
                burbuja.onDraw(canvas);
    }

    private void drawRandomSprites(Canvas canvas) {
        if (burbujasRandom != null)
            for (Burbuja burbuja : burbujasRandom)
                burbuja.onDraw(canvas);
    }

    public class IntroAnimation extends Thread {

        boolean animationIsActive;
        MediaPlayer mediaPlayer;

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;

            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.opening);
                mediaPlayer.setVolume(0.2f, 0.2f);
            }

            try {

                if (burbujasOpciones == null)
                    initOptionsSprites();

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initRandomSprites(25);
                    }
                }, 200);

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initRandomSprites(15);
                    }
                }, 21000);


                animationIsActive = true;

                mediaPlayer.start();

                while (animationIsActive) {

                    frameStartTime = System.currentTimeMillis();
                    updateSprites();
                    cleanSprites();

                    frameTime = System.currentTimeMillis() - frameStartTime;
                    if (frameTime < MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(MAX_FRAME_TIME - frameTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    postInvalidate();

                }

                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
