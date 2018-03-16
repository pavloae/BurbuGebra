package ar.com.andino.pablo.burbugebra;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ar.com.andino.pablo.burbugebra.activities.ActivityEcuaciones;
import ar.com.andino.pablo.burbugebra.activities.ActivityFracciones;
import ar.com.andino.pablo.burbugebra.activities.ActivityOperaciones;
import ar.com.andino.pablo.burbugebra.activities.ActivitySistemas;
import ar.com.andino.pablo.burbugebra.views.IntroView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    IntroView introView;
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    int wavesID;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        introView = new IntroView(this);
        introView.setOnTouchListener(this);

        setContentView(introView);

    }

    private void prepareMusic() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        wavesID = soundPool.load(introView.getContext(), R.raw.waves, 1);

        mediaPlayer = MediaPlayer.create(introView.getContext(), R.raw.opening);
        mediaPlayer.setVolume(0.2f, 0.2f);
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            mediaPlayer.release();
        }

        if (soundPool != null)
            soundPool.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        prepareMusic();

        if (animation != null) {
            animation.animationIsActive = false;
            try {
                animation.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        introView.post(new Runnable() {
            @Override
            public void run() {
                animation = new Animation(introView);
                animation.start();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        animation.animationIsActive = false;
        try {
            animation.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopMusic();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        Intent intent = null;
        if (IntroView.optionBubbles.getBubble(0).isTouched(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityFracciones.class);
        if (IntroView.optionBubbles.getBubble(1).isTouched(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityOperaciones.class);
        if (IntroView.optionBubbles.getBubble(2).isTouched(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityEcuaciones.class);
        if (IntroView.optionBubbles.getBubble(3).isTouched(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivitySistemas.class);

        if (intent == null){
            return false;
        }

        startActivity(intent);
        return false;
    }


    public class Animation extends Thread {

        private final int FPS = 25;
        private final int MAX_FRAME_TIME = (int) (1000.0 / FPS);

        private IntroView introView;
        boolean animationIsActive;

        Handler handler;
        Runnable runnable1, runnable2, runnableWaves;

        Animation(IntroView introView) {
            this.introView = introView;
            handler = new Handler();
        }

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;

            try {

                initRunnables();

                introView.initOptionsSprites();

                handler.postDelayed(runnable1, 0);
                handler.postDelayed(runnable2, 21000);

                animationIsActive = true;

                mediaPlayer.start();

                while (animationIsActive) {
                    frameStartTime = System.currentTimeMillis();
                    IntroView.optionBubbles.update();
                    IntroView.randomBubbles.update();
                    frameTime = System.currentTimeMillis() - frameStartTime;
                    if (frameTime < MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(MAX_FRAME_TIME - frameTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    introView.postInvalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler.removeCallbacks(runnable1, null);
                handler.removeCallbacks(runnable2, null);
                handler.removeCallbacks(runnableWaves, null);
            }

        }

        private void initRunnables() {

            runnable1 = getRandomRunnable(22);
            runnable2 = getRandomRunnable(14);
            runnableWaves = new Runnable() {
                @Override
                public void run() {
                    soundPool.play(wavesID, 0.5f, 0.5f, 1, 0, 1);
                    handler.postDelayed(this, (long) (10000 + Math.random() * 20000));
                }
            };

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    handler.post(runnableWaves);
                }
            });

        }

        private Runnable getRandomRunnable(final int total) {
            return new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int makes = 0;
                            long initTime = System.currentTimeMillis();
                            IntroView.randomBubbles.clear();
                            while (makes <= total && System.currentTimeMillis() - initTime < 4000) {
                                introView.initRandomSprite();
                                makes++;
                                try {
                                    Thread.sleep(75);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            };
        }

    }


}
