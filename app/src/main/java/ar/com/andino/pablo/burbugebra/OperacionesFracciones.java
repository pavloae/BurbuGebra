package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ar.com.andino.pablo.burbugebra.elementos.Bubble;
import ar.com.andino.pablo.burbugebra.elementos.Ecuacion;
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
        Bubble.plumEffect.stop();
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.rebels_be);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public class Lienzo extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;
        private Bitmap logo;

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        Ecuacion ecuacion;

        List<Bubble> bubbles;

        public Lienzo(Context context) {
            super(context);
            this.context = context;
            mPath = new Path();
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vintage_paper_by_darkwood67);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

            bubbles = new ArrayList<>();

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, w, h, true);

            float logoX = logo.getWidth();
            float logoY = logo.getHeight();

            float zoomX = logoX / w;
            float zoomY = logoY / h;

            if (Math.abs(zoomX) < Math.abs(zoomY)){
                logo = Bitmap.createScaledBitmap(logo, (int) Math.abs(logoX * zoomX), (int) Math.abs(logoY * zoomX), true);
            } else {
                logo = Bitmap.createScaledBitmap(logo, (int) Math.abs(logoX * zoomY), (int) Math.abs(logoY * zoomY), true);

            }


            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap( mBitmap, 0, 0, null);
            canvas.drawBitmap(logo, canvas.getWidth()/2-logo.getWidth()/2, canvas.getHeight()/2-logo.getHeight()/2, null);
            // canvas.drawPath( mPath,  mPaint);
            // canvas.drawPath( circlePath,  circlePaint);
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

            bubbles.add(new Bubble(getContext(), x, y));

            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.lineTo((x + mX)/2, (y + mY)/2);
                //mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            mPath.reset();
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();

        }

    }

}
