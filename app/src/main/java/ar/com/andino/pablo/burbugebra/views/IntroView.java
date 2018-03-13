package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.elementos.Burbuja;
import ar.com.andino.pablo.burbugebra.utils.Utils;

public class IntroView extends View {

    Bitmap background, logo;
    int logoX, logoY;

    private static final int MAX_FRAME_TIME = (int) (1000.0 / 25.0);
    IntroAnimation introAnimation;

    private float velocidad;
    public BubbleGroup optionBubbles;
    Bitmap bitmapOptionBubble;

    final Lock lockRandomBubbles = new ReentrantLock();
    private BubbleGroup randomBubbles;

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
        if (optionBubbles != null)
            optionBubbles.draw(canvas);
        canvas.drawBitmap(logo, logoX, logoY, null);
        synchronized (lockRandomBubbles) {
            if (randomBubbles!= null && !randomBubbles.isEmpty()) {
                randomBubbles.draw(canvas);
                randomBubbles.removeBursted();
            }
        }
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

        final float radioBubble = 0.10f * getWidth();
        final float T = 1000f;
        final float A = radioBubble / 5f;
        final float yf = (float) (0.7 * getHeight());
        final float k = (float) (2 * Math.PI / getWidth());
        final float w = (float) (2 * Math.PI / T);
        velocidad = (float) (2 * Math.PI * A / T);
        if (bitmapOptionBubble == null)
            bitmapOptionBubble = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue),
                    2 * (int) radioBubble,
                    2 * (int) radioBubble,
                    true
            );

        if (optionBubbles == null)
            optionBubbles = new BubbleGroup();

        optionBubbles.clear();
        optionBubbles.addBubble(
                new Burbuja(bitmapOptionBubble,0.125f * getWidth(), getHeight(), radioBubble) {

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
                new Burbuja(bitmapOptionBubble,0.375f * getWidth(), (float) (getHeight() + 0.25 * T * velocidad), radioBubble) {

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
                new Burbuja(bitmapOptionBubble,0.625f * getWidth(), (float) (getHeight() + 0.50 * T * velocidad), radioBubble) {

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
                new Burbuja(bitmapOptionBubble,0.875f * getWidth(), (float) (getHeight() + 0.75 * T * velocidad), radioBubble) {

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

    public void initRandomSprites(int count) {

        synchronized (lockRandomBubbles) {

            final float yf = 0.20f * getHeight(); // Altura relativa a la que explotan las burbujas
            int incubadoraW = getWidth();
            int incubadoraH = (int) (getHeight() * 0.40f);
            int incubadoraTop = (int) (getHeight() * 0.95f);

            int filas = 8;
            final int columnas = 16;

            count = Math.min(count, filas * columnas);

            if (randomBubbles == null)
                randomBubbles = new BubbleGroup();
            randomBubbles.clear();

            final int[][] coord = new int[filas * columnas][2];
            int randomCelda;
            int fila, columna;
            for (int burbuja = 0; burbuja < count; burbuja++){
                randomCelda = (int) (Math.random() * (filas * columnas - 1));
                while (coord[randomCelda][0] != 0){
                    if (randomCelda == filas * columnas - 1){
                        randomCelda = 0;
                    } else {
                        randomCelda++;
                    }

                }
                fila = randomCelda / columnas;
                columna = randomCelda % columnas;
                int celdaW = incubadoraW / columnas;
                int celdaH = incubadoraH /filas;
                coord[randomCelda][0] = (int) (columna * celdaW + celdaW * 0.50f);
                coord[randomCelda][1] = (int) (incubadoraTop + (fila * celdaH + celdaH * 0.50f));
            }

            for (int position = 0 ; position < (filas * columnas) ; position ++){

                if (coord[position] == null)
                    continue;

                randomBubbles.addBubble(
                        new Burbuja(
                                bitmapOptionBubble,
                                coord[position][0],
                                coord[position][1],
                                (float) (0.03f + 0.05f * Math.random()) * getWidth()
                        ) {

                            long t, t0;

                            @Override
                            public float getCenterY() {

                                if (t0 == 0.00f)
                                    t0 = System.currentTimeMillis();

                                t = System.currentTimeMillis() - t0;

                                if (super.getCenterY() - 2 * velocidad * t > yf)
                                    return super.getCenterY() - 2 * velocidad * t;

                                onPlop();
                                return 0;

                            }

                        }
                );

            }
        }


    }

    public class IntroAnimation extends Thread {

        boolean animationIsActive;
        MediaPlayer mediaPlayer;
        Handler handlerWaves;
        Runnable runnableWaves;

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;

            prepareMusic();

            try {

                initOptionsSprites();

                initRandomSprites(20);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(21000);
                            initRandomSprites(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                animationIsActive = true;

                mediaPlayer.start();

                while (animationIsActive) {

                    frameStartTime = System.currentTimeMillis();

                    optionBubbles.update();
                    randomBubbles.update();

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



            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.release();
                }
                if (handlerWaves != null)
                    handlerWaves.removeCallbacks(runnableWaves);
            }

        }

        private void prepareMusic() {
            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.opening);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    initWaves();
                }
            });
            mediaPlayer.setVolume(0.2f, 0.2f);
        }

        private void initWaves(){
            final SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            final int wavesID = soundPool.load(getContext(), R.raw.waves, 1);
            handlerWaves = new Handler();
            runnableWaves = new Runnable() {
                @Override
                public void run() {
                    soundPool.play(wavesID, 0.5f, 0.5f, 1, 0, 1);
                    handlerWaves.postDelayed(this, (long) (5000 + Math.random() * 15000));
                }
            };
            handlerWaves.post(runnableWaves);
        }

    }

    public class BubbleGroup extends Burbuja {

        List<Burbuja> burbujas;
        List<Burbuja> explotadas = new ArrayList<>();
        int position;

        @NonNull
        @Override
        public Bitmap getBitmap() {
            return null;
        }

        public void addBubble(Burbuja burbuja){
            if (burbujas == null)
                burbujas = new ArrayList<>();
            burbujas.add(burbuja);
        }

        public void addBubble(Burbuja burbuja, int position){
            if (burbujas == null)
                burbujas = new ArrayList<>();
            if (position < 0 || position > burbujas.size())
                position = burbujas.size();
            burbujas.add(position, burbuja);
        }

        public Burbuja getBubble(int position){
            if (position < 0 || position > burbujas.size())
                return null;
            return burbujas.get(position);
        }

        public List<Burbuja> getBubbles(){
            return burbujas;
        }

        public void clear(){
            if (burbujas != null)
                burbujas.clear();
        }

        public void removeBursted() {
            explotadas.clear();
            for (Burbuja burbuja : burbujas)
                if (burbuja.isBursted)
                    explotadas.add(burbuja);
            burbujas.removeAll(explotadas);
        }

        public void draw(Canvas canvas) {
            if (burbujas == null || burbujas.size() == 0)
                return;
            for (position = 0 ; position < burbujas.size() ; position++)
                burbujas.get(position).onDraw(canvas);
        }

        public void update(){
            if (burbujas == null || burbujas.size() == 0)
                return;
            for (position = 0 ; position < burbujas.size() ; position++)
                burbujas.get(position).update();
        }

        public boolean isEmpty(){
            return (burbujas == null || burbujas.size() == 0);
        }

    }

}
