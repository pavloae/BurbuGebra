package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ar.com.andino.pablo.burbugebra.activities.ActivityEcuaciones;
import ar.com.andino.pablo.burbugebra.activities.ActivityFracciones;
import ar.com.andino.pablo.burbugebra.activities.ActivitySistemas;
import ar.com.andino.pablo.burbugebra.elementos.Burbuja;
import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public class MainActivity extends AppCompatActivity {

    static SpritesBubble spritesBubbleUnderWater, spritesBubbleDisappear;

    IntroSurfaceView introSurfaceView;
    EquipoMusica equipoMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initSprites();

        equipoMusica = EquipoMusica.getInstance();
        introSurfaceView = new IntroSurfaceView(this, null);
        setContentView(introSurfaceView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        introSurfaceView.startDrawThread();
        equipoMusica.cargarMusica(this, R.raw.opening);
        equipoMusica.cargarEfecto(this, R.raw.pop1);
        equipoMusica.reproducirMusica();
    }

    @Override
    protected void onPause() {
        introSurfaceView.stopDrawThread();
        equipoMusica.pausarMusica();
        super.onPause();
    }

    private void initSprites(){
/*
        if (spritesBubbleUnderWater == null)
            spritesBubbleUnderWater = new SpritesBubble(
                    BitmapFactory.decodeResource(
                            getResources(),
                            R.drawable.bubble_sprites_underwater
                    ),
                    8,
                    false
            );
*/

        if (spritesBubbleDisappear == null)
            spritesBubbleDisappear = new SpritesBubble(
                    BitmapFactory.decodeResource(
                            getResources(),
                            R.drawable.bubble_sprites_disappear
                    ),
                    7,
                    false,
                    0.5f,
                    0.5f,
                    1.0f
            );

    }

    public class IntroSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {

        private SurfaceHolder surfaceHolder;
        private Thread drawThread;

        private boolean surfaceReady = false;
        private boolean drawingActive = false;

        Bitmap background, logo;
        int logoX, logoY;

        private static final int MAX_FRAME_TIME = (int) (1000.0 / 25.0);
        private float velocidad;

        Burbuja[] burbujasOpciones;

        public IntroSurfaceView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            setOnTouchListener(this);

            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_intro);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);

        }

        public void startDrawThread() {
            if (surfaceReady && drawThread == null) {
                drawThread = new Thread(this, "Draw thread");
                drawingActive = true;
                drawThread.start();
            }
        }

        public void stopDrawThread() {

            if (drawThread == null)
                return;

            drawingActive = false;
            while (true) {
                try {
                    drawThread.join(5000);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            drawThread = null;
        }

        public void doDraw(Canvas canvas) {
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(logo, logoX, logoY,null);
            if (burbujasOpciones != null)
                for (Burbuja burbuja : burbujasOpciones){
                    if (burbuja == null)
                        continue;
                    burbuja.updateBubblePosition();
                    burbuja.onDraw(canvas);
                }
        }

        public void initBurbujasOpciones() {

            final float radioBubble = 0.1f * getWidth();
            final float T = 1000f;
            final float A = radioBubble / 5f;
            final float yf = (float) (0.7 * getHeight());
            final float k = (float) (2 * Math.PI / getWidth());
            final float w = (float) (2 * Math.PI / T);
            velocidad = (float) (2 * Math.PI * A / T);

            burbujasOpciones = new Burbuja[4];

            burbujasOpciones[0] = new Burbuja(spritesBubbleDisappear) {

                boolean cycle = false;
                float fase;
                final float x0 = 0.125f * getWidth();
                final float y0 = getHeight();
                long t0, t, t0Animated, tAnimated;

                @Override
                public float getBubbleRadius() {
                    return radioBubble;
                }

                @Override
                public SpritesBubble getSpritesBubbles() {
                    return spritesBubbleDisappear;
                }

                @Override
                public void updateBubblePosition() {

                    if (t0 <= 0)
                        t0 = System.currentTimeMillis();

                    t = System.currentTimeMillis() - t0;

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterX(x0);
                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }
                }

                @Override
                public void onPressed() {
                    super.onPressed();
                    onPlop();
                    startActivity(
                            new Intent(
                                    MainActivity.this,
                                    ActivityFracciones.class
                            )
                    );
                }

                @Override
                public void onPlop() {
                    super.onPlop();
                    equipoMusica.reproducirEfecto(0);
                    t0Animated = System.currentTimeMillis();
                }
            };

            burbujasOpciones[1] = new Burbuja(spritesBubbleDisappear) {


                boolean cycle = false;
                float fase;
                final float x0 = 0.375f * getWidth();
                final float y0 = (float) (getHeight() + 0.25 * T * velocidad);
                final long tfAnimated = 1200;
                int frameAnimated;
                long t0, t, t0Animated, tAnimated;

                @Override
                public float getBubbleRadius() {
                    return radioBubble;
                }

                @Override
                public SpritesBubble getSpritesBubbles() {
                    return null;
                }

                @Override
                public void updateBubblePosition() {

                    if (t0Animated > 0 && tAnimated <= tfAnimated) {
                        tAnimated = System.currentTimeMillis() - t0Animated;

                        frameAnimated++;


                    }

                    if (t0 <= 0)
                        t0 = System.currentTimeMillis();

                    t = System.currentTimeMillis() - t0;

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterX(x0);
                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }

                }

                @Override
                public void onPressed() {
                    super.onPressed();
                    onPlop();
                    /*
                    startActivity(
                            new Intent(
                                    MainActivity.this,
                                    ActivityOperaciones.class
                            )
                    );
                    */
                }

                @Override
                public void onPlop() {
                    equipoMusica.reproducirEfecto(0);
                    t0Animated = System.currentTimeMillis();
                }

            };

            burbujasOpciones[2] = new Burbuja(spritesBubbleDisappear) {

                boolean cycle = false;
                float fase;
                final float x0 = 0.625f * getWidth();
                final float y0 = (float) (getHeight() + 0.5 * T * velocidad);
                long t0, t, t0Animated, tAnimated;

                @Override
                public float getBubbleRadius() {
                    return radioBubble;
                }

                @Override
                public SpritesBubble getSpritesBubbles() {
                    return null;
                }

                @Override
                public void updateBubblePosition() {

                    if (t0 <= 0)
                        t0 = System.currentTimeMillis();

                    t = System.currentTimeMillis() - t0;

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterX(x0);
                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }
                }

                @Override
                public void onPressed() {
                    super.onPressed();
                    onPlop();
                    startActivity(
                            new Intent(
                                    MainActivity.this,
                                    ActivityEcuaciones.class
                            )
                    );
                }

                @Override
                public void onPlop() {
                    equipoMusica.reproducirEfecto(0);
                    t0Animated = System.currentTimeMillis();
                }

            };

            burbujasOpciones[3] = new Burbuja(spritesBubbleDisappear) {

                boolean cycle = false;
                float fase;
                final float x0 = 0.875f * getWidth();
                final float y0 = (float) (getHeight() + 0.75 * T * velocidad);
                long t0, t, t0Animated, tAnimated;

                @Override
                public float getBubbleRadius() {
                    return radioBubble;
                }

                @Override
                public SpritesBubble getSpritesBubbles() {
                    return null;
                }

                @Override
                public void updateBubblePosition() {

                    if (t0 <= 0)
                        t0 = System.currentTimeMillis();

                    t = System.currentTimeMillis() - t0;

                    if (cycle) {
                        setCenterY((float) (yf - A * Math.sin(w * t - k * fase)));
                        return;
                    }

                    setCenterX(x0);
                    setCenterY(y0 - t * velocidad);

                    if (getCenterY() <= yf) {
                        fase = (w * t) / k;
                        cycle = true;
                    }
                }

                @Override
                public void onPressed() {
                    super.onPressed();
                    onPlop();
                    startActivity(
                            new Intent(
                                    MainActivity.this,
                                    ActivitySistemas.class
                            )
                    );
                }

                @Override
                public void onPlop() {
                    equipoMusica.reproducirEfecto(0);
                    t0Animated = System.currentTimeMillis();
                }
            };

            burbujasOpciones[0].setFillingBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.intro_fracciones), true);
            burbujasOpciones[1].setFillingBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.intro_operaciones), true);
            burbujasOpciones[2].setFillingBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.intro_ecuaciones), true);
            burbujasOpciones[3].setFillingBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.intro_sistemas), true);

        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;

            // Si existe un hilo actualmente activo, lo desactivamos y esperamos hasta que se libere
            if (drawThread != null) {
                drawingActive = false;
                try {
                    drawThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            surfaceReady = true;
            startDrawThread();

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int widht, int height) {

            if (widht == 0 || height == 0)
                return;

            background = Bitmap.createScaledBitmap(background, widht, height,true);

            float scale = widht * 0.7f / logo.getWidth();
            logo = Bitmap.createScaledBitmap(logo, (int) (logo.getWidth() * scale), (int) (logo.getHeight() * scale),true);
            logoX = (widht - logo.getWidth()) / 2;
            logoY = height - logoX - logo.getHeight();

            initBurbujasOpciones();

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            stopDrawThread();
            surfaceHolder.getSurface().release();
            this.surfaceHolder = null;
            surfaceReady = false;
        }

        @Override
        public void run() {

            long frameStartTime;
            long frameTime = 0;

            try {
                Canvas canvas;
                while (drawingActive) {
                    if (surfaceHolder == null)
                        return;

                    frameStartTime = System.currentTimeMillis();
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        try {
                            doDraw(canvas);
                            frameTime = System.currentTimeMillis() - frameStartTime;
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }

                    if (frameTime < MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(MAX_FRAME_TIME - frameTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            for (Burbuja burbuja : burbujasOpciones)
                burbuja.onTouchScreen(motionEvent.getX(), motionEvent.getY());
            return false;
        }

    }

}
