package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.com.andino.pablo.burbugebra.elementos.Bubble;
import ar.com.andino.pablo.burbugebra.elementos.BubbleMotion;

public class MainActivity extends AppCompatActivity {

    Lienzo lienzo;
    boolean run = false;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mediaPlayer = MediaPlayer.create(this, R.raw.opening);

        lienzo = new Lienzo(this);
        setContentView(lienzo);

        lienzo.startLogic();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic();
        run = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        run = false;
        lienzo.setActive(false);
    }

    private void playMusic() {
        mediaPlayer.setVolume(0.35f, 0.35f);
        mediaPlayer.start();
    }

    public class Lienzo extends View {

        Context context;
        private Bitmap backGround, logo;
        private boolean active;
        private float t;  // tiempo de la animación en segundos

        // Parámetros de la pantalla
        private final int HEIGHT;
        private final int WIDHT;

        private float velocidad;        // Velocidad de ascención
        BubbleMotion[] bubblesOptions;
        BubbleMotion[] bubbleMotionList;

        public Lienzo(Context context) {
            super(context);
            this.context = context;
            backGround = BitmapFactory.decodeResource(getResources(), R.drawable.oceano_fondo_animado);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            HEIGHT = displayMetrics.heightPixels;
            WIDHT = displayMetrics.widthPixels;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            backGround = Bitmap.createScaledBitmap(backGround, w, h, true);

            int logoW = w * 70 / 100;
            int logoH = logo.getHeight() * logoW / logo.getWidth();
            logo = Bitmap.createScaledBitmap(logo, logoW, logoH, true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(backGround, 0, 0, null);

            if (bubblesOptions == null)
                initBubblesOptions();

            if (bubbleMotionList == null)
                initBubblesG(23);

            canvas.drawBitmap(logo, backGround.getWidth() / 2 - logo.getWidth() / 2, backGround.getHeight() - backGround.getWidth() * 15 / 100 - logo.getHeight(),null);

            drawBubbles(canvas);

        }

        public void initBubblesOptions() {

            final float radioBubble = 0.1f * WIDHT;

            final float T = 1f;   // Periodo en segundos
            final float A = radioBubble / 4f;
            final float yf = (float) (0.7 * HEIGHT);
            final float k = (float) (2 * Math.PI / WIDHT);
            final float w = (float) (2 * Math.PI / T);
            velocidad = (float) (2 * Math.PI * A / T);

            bubblesOptions = new BubbleMotion[4];

            bubblesOptions[0] = new BubbleMotion(MainActivity.this) {

                boolean cycle = false;
                float fase;
                final float y0 = HEIGHT;

                @Override
                public float[] getOriginXY() {
                    return new float[]{
                            0.125f * WIDHT,
                            HEIGHT
                    };
                }

                @Override
                public float getRadius() {
                    return radioBubble;
                }

                @Override
                public void onUpdatePosition() {

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }

                }

                @Override
                public void bitmapToBubble(@Nullable Bitmap bitmap) {
                    super.bitmapToBubble(BitmapFactory.decodeResource(getResources(), R.drawable.operaciones));
                }

            };

            bubblesOptions[1] = new BubbleMotion(MainActivity.this) {

                boolean cycle = false;
                float fase;
                final float y0 = (float) (HEIGHT + 0.25 * T * velocidad);

                @Override
                public float[] getOriginXY() {
                    return new float[]{
                            0.375f * WIDHT,
                            HEIGHT
                    };
                }

                @Override
                public float getRadius() {
                    return radioBubble;
                }

                @Override
                public void onUpdatePosition() {

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }
                }



            };

            bubblesOptions[2] = new BubbleMotion(MainActivity.this) {

                boolean cycle = false;
                float fase;
                final float y0 = (float) (HEIGHT + 0.5 * T * velocidad);

                @Override
                public float[] getOriginXY() {
                    return new float[]{
                            0.625f * WIDHT,
                            HEIGHT
                    };
                }

                @Override
                public float getRadius() {
                    return radioBubble;
                }

                @Override
                public void onUpdatePosition() {
                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }
                }

            };

            bubblesOptions[3] = new BubbleMotion(MainActivity.this) {

                boolean cycle = false;
                float fase;
                final float y0 = (float) (HEIGHT + 0.75 * T * velocidad);

                @Override
                public float[] getOriginXY() {
                    return new float[]{
                            0.875f * WIDHT,
                            HEIGHT
                    };
                }

                @Override
                public float getRadius() {
                    return radioBubble;
                }

                @Override
                public void onUpdatePosition() {
                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }

                }
            };


        }

        public void initBubblesG(int bubbles) {

            Log.v("INIT", "LANZANDO BURBUJJAS..." + bubbles);

            final float lineaExplosion = 0.2f * HEIGHT;

            bubbleMotionList = new BubbleMotion[bubbles];

            final float t0 = t;

            for (int i = 0; i < bubbles; i++){

                final float y0 = HEIGHT + 1000f * new Random().nextFloat();

                final int finalI = i;
                bubbleMotionList[i] = new BubbleMotion(MainActivity.this) {

                    @Override
                    public float[] getOriginXY() {
                        return new float[]{
                                WIDHT * new Random().nextFloat(),
                                y0
                        };
                    }

                    @Override
                    public float getRadius() {
                        return 30f + 60f * new Random().nextFloat();
                    }

                    @Override
                    public void onUpdatePosition() {
                        setCenterY((float) (y0 - Math.pow((t - t0), 2) * velocidad / 4));
                        if (getCenterY() < lineaExplosion){
                            plump();
                            bubbleMotionList[finalI] = null;
                        }
                    }
                };


            }
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            performClick();

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

        public void update(int delta){

            t += delta / 1000f;

            float timeLaunch = 19f;

            if (bubblesOptions != null)
                for (BubbleMotion bubbleMotion : bubblesOptions)
                    if (bubbleMotion != null)
                        bubbleMotion.onUpdatePosition();

            if (bubbleMotionList != null)
                for (BubbleMotion bubbleMotion : bubbleMotionList)
                    if (bubbleMotion != null)
                        bubbleMotion.onUpdatePosition();

            if (t > timeLaunch && t < timeLaunch + (delta / 1000f))
                initBubblesG(12);

        }

        private void drawBubbles(Canvas canvas) {

            for (BubbleMotion bubbleMotion : bubblesOptions) {
                if (bubbleMotion != null)
                    bubbleMotion.onDraw(canvas);
            }

            for (BubbleMotion bubbleMotion : bubbleMotionList) {
                if (bubbleMotion != null)
                    bubbleMotion.onDraw(canvas);
            }



        }

        public void startLogic() {

            active = true;

            new Thread(new Runnable(){
                @Override
                public void run() {

                    long time1 = System.currentTimeMillis();
                    long time2;

                    while(active){
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        time2 = System.currentTimeMillis(); // Get current time
                        int delta = (int) (time2 - time1);

                        lienzo.update(delta);

                        time1 = time2;

                        lienzo.postInvalidate();
                    }

                }}).start();
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        private void touch_start(float x, float y) {

            for (int i = 0; i < 4; i ++){

                if (bubblesOptions[i] == null)
                    return;

                bubblesOptions[i].onScreenPressed(x, y);

            }

        }

        private void touch_move(float x, float y) {

        }

        private void touch_up() {

        }

    }



}
