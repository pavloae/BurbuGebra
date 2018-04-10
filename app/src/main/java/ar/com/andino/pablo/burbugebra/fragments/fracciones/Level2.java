package ar.com.andino.pablo.burbugebra.fragments.fracciones;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.views.OperationsView;

public class Level2 extends Fragment implements View.OnTouchListener {

    MediaPlayer mediaPlayer;
    int resourceMusic = R.raw.rebels_be;

    OperationsView operationsView;
    Animation animation;

    public Level2() {
        super();
    }

    public static Level2 newInstance(){
        return new Level2();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        operationsView = new OperationsView(getContext());
        operationsView.setOnTouchListener(this);
        return operationsView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (animation != null && animation.animationIsActive) {
            animation.animationIsActive = false;
            try {
                animation.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        operationsView.post(new Runnable() {
            @Override
            public void run() {
                animation = new Animation(operationsView);
                animation.start();
            }
        });
        playMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
        animation.animationIsActive = false;
        try {
            animation.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopMusic();
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(getContext(), resourceMusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    public void stopMusic() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            update(motionEvent.getX(), motionEvent.getY());
            view.invalidate();
        }
        return true;
    }

    private void update(final float x, final float y) {

    }

    public class Animation extends Thread{

        private static final int FPS = 25;
        private static final int MAX_FRAME_TIME = (int) (1000.0 / FPS);

        OperationsView operationsView;

        boolean animationIsActive;

        Animation(OperationsView operationsView){
            this.operationsView = operationsView;
        }

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;

            try {

                operationsView.initBubbles();

                animationIsActive = true;

                while (animationIsActive) {
                    frameStartTime = System.currentTimeMillis();

                    operationsView.equation.update();

                    frameTime = System.currentTimeMillis() - frameStartTime;
                    if (frameTime < MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(MAX_FRAME_TIME - frameTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    operationsView.postInvalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
