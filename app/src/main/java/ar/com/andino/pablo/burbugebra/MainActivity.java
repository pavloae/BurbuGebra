package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ar.com.andino.pablo.burbugebra.elementos.Bubble;

public class MainActivity extends AppCompatActivity {

    Lienzo lienzo;

    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lienzo = new Lienzo(this);
        lienzo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(lienzo);

        playMusic();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        if (Bubble.plumEffect != null)
            Bubble.plumEffect.stop();
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.on_my_way);

        countDownTimer = new CountDownTimer(70000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.acid_bubble);
                mediaPlayer.start();
            }
        };

        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();

        countDownTimer.start();

    }

    public class Lienzo extends View {

        Context context;

        private Bitmap backGround;
        private Bitmap logo;

        private Canvas mCanvas;
        List<Bubble> bubbles;
        Bubble bubbleOptions;

        public Lienzo(Context context) {
            super(context);
            this.context = context;
            backGround = BitmapFactory.decodeResource(getResources(), R.drawable.oceano_fondo_animado);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.nombre_logo_1);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            backGround = Bitmap.createScaledBitmap(backGround, w, h, true);

            int logoW = w * 75 / 100;
            int logoH = logo.getHeight() * logoW / logo.getWidth();

            logo = Bitmap.createScaledBitmap(logo, logoW, logoH, true);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(backGround, 0, 0, null);

            canvas.drawBitmap(
                    logo,
                    canvas.getWidth() / 2 - logo.getWidth() / 2,
                    canvas.getHeight() * 85 / 100 - logo.getHeight() / 2,
                    null
            );

            float bubbleX = canvas.getWidth() / 2;
            float bubbleY = canvas.getHeight() * 85 / 100;

            float radioBubble = logo.getHeight() / 2;

            if (bubbleOptions == null)
                bubbleOptions = new Bubble(context, bubbleX, bubbleY, radioBubble);

            if (bubbles == null) {

                //radioBubble = 200;

                bubbles = new ArrayList<>();
                float bubblesY = canvas.getHeight() * 85 / 100 - logo.getHeight();
                bubbles.add(new Bubble(context, canvas.getWidth() * 0.2f , bubblesY, radioBubble));
                bubbles.add(new Bubble(context, canvas.getWidth() * 0.4f , bubblesY, radioBubble));
                bubbles.add(new Bubble(context, canvas.getWidth() * 0.6f , bubblesY, radioBubble));
                bubbles.add(new Bubble(context, canvas.getWidth() * 0.8f , bubblesY, radioBubble));
            }

            if (bubbleOptions.selected)
                drawBubbles(canvas);

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

        private void drawBubbles(Canvas canvas) {

            for (Bubble bubble: bubbles)
                bubble.onDraw(canvas);

        }

        private void touch_start(float x, float y) {

            bubbleOptions.onScreenPressed(x, y);
            bubbleOptions.selected = true;

        }

        private void touch_move(float x, float y) {

        }

        private void touch_up() {

        }

    }

}
