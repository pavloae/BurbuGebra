package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ar.com.andino.pablo.burbugebra.elementos.Bubble;
import ar.com.andino.pablo.burbugebra.elementos.Fraccion;

public class OperacionesFracciones extends AppCompatActivity {

    Lienzo lienzo;
    private Paint mPaint;
    Fraccion fraccionI, fraccionD;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lienzo = new Lienzo(this);
        lienzo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(lienzo);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        playMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.rebels_be);
        mediaPlayer.setLooping(true);
        //mediaPlayer.start();
    }

    public class Lienzo extends View {

        public int width;
        public  int height;
        private Bitmap backGroundBitMap;
        private Canvas mCanvas;
        Context context;
        private Bitmap logo;

        List<Bubble> bubbles;

        public Lienzo(Context context) {
            super(context);
            this.context = context;
            backGroundBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.oceano_fondo_animado);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);

            bubbles = new ArrayList<>();

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            backGroundBitMap = Bitmap.createScaledBitmap(backGroundBitMap, w, h, true);

            float logoX = logo.getWidth();
            float logoY = logo.getHeight();

            float zoomX = logoX / w;
            float zoomY = logoY / h;

            if (Math.abs(zoomX) < Math.abs(zoomY)){
                logo = Bitmap.createScaledBitmap(logo, (int) Math.abs(logoX * zoomX), (int) Math.abs(logoY * zoomX), true);
            } else {
                logo = Bitmap.createScaledBitmap(logo, (int) Math.abs(logoX * zoomY), (int) Math.abs(logoY * zoomY), true);

            }

            mCanvas = new Canvas(backGroundBitMap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(backGroundBitMap, 0, 0, null);
            canvas.drawBitmap(logo, canvas.getWidth()/2-logo.getWidth()/2, canvas.getHeight()/2-logo.getHeight()/2+100, null);
            drawBubbles(canvas);
        }

        private void drawBubbles(Canvas canvas){

            for (Bubble bubble : bubbles)
                bubble.onDraw(canvas);

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

        private void touch_start(float x, float y) {

            for (Bubble bubble : bubbles)
                bubble.onScreenPressed(x, y);

            //bubblesOptions.add(new Bubble(getContext(), x, y));

        }

        private void touch_move(float x, float y) {

        }

        private void touch_up() {

        }

    }

}
