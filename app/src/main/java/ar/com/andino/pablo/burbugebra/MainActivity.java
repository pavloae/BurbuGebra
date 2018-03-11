package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ar.com.andino.pablo.burbugebra.elementos.Burbuja;

public class MainActivity extends AppCompatActivity {

    IntroView introView;
    IntroThread introThread;
    //EquipoMusica equipoMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //equipoMusica = EquipoMusica.getInstance();
        introView = new IntroView(this);
        introThread = new IntroThread();
        setContentView(introView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //introThread.start();
        //equipoMusica.cargarMusica(this, R.raw.opening);
        //equipoMusica.cargarEfecto(this, R.raw.pop1);
        //equipoMusica.reproducirMusica();
    }

    @Override
    protected void onPause() {
        introThread.animationIsActive = false;
        //equipoMusica.pausarMusica();
        super.onPause();
    }

    public class IntroView extends View {

        Bitmap background, logo;
        int logoX, logoY;

        Burbuja[] burbujasOpciones;
        private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);
        private float velocidad;

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
            introThread.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(logo, logoX, logoY, null);
            drawAnimation(canvas);
        }

        public void initAnimation() {

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

                    if (t0 == 0.0f){
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

                    if (t0 == 0.0f){
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

                    if (t0 == 0.0f){
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

                    if (t0 == 0.0f){
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

        public void updateAnimation() {
            if (burbujasOpciones != null)
                for (Burbuja burbuja : burbujasOpciones){
                    if (burbuja == null)
                        continue;
                    burbuja.updateBubblePosition();
                }
        }

        private void drawAnimation(Canvas canvas) {
            if (burbujasOpciones != null)
                for (Burbuja burbuja : burbujasOpciones)
                    burbuja.onDraw(canvas);
        }

    }

    public class IntroThread extends Thread {

        boolean animationIsActive;

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;
            animationIsActive = true;


            try {

                introView.initAnimation();

                while (animationIsActive) {
                    frameStartTime = System.currentTimeMillis();

                    introView.updateAnimation();
                    frameTime = System.currentTimeMillis() - frameStartTime;

                    if (frameTime < IntroView.MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(IntroView.MAX_FRAME_TIME - frameTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    introView.postInvalidate();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
