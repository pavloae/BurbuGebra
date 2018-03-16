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
import ar.com.andino.pablo.burbugebra.bubbles.MemoryBubble;
import ar.com.andino.pablo.burbugebra.views.UnderSeaView;

public class Level1 extends Fragment implements View.OnTouchListener{

    UnderSeaView underSeaView;
    Animation animation;

    public Level1() {
        super();
    }

    public static Level1 newInstance(){
        return new Level1();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        underSeaView = new UnderSeaView(getContext(), 4, 4);
        underSeaView.setOnTouchListener(this);
        return underSeaView;
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
        underSeaView.post(new Runnable() {
            @Override
            public void run() {
                animation = new Animation(underSeaView);
                animation.start();
            }
        });
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

        synchronized (underSeaView.bubbleGrid) {
            for (int posicion = 0; posicion < underSeaView.bubbleGrid.size(); posicion++){
                if (underSeaView.bubbleGrid.getBubble(posicion).isTouched(x, y) && !underSeaView.bubbleGrid.getBubble(posicion).isBursted){
                    if (MemoryBubble.burbuja1 == null){
                        MemoryBubble.burbuja1 = underSeaView.bubbleGrid.getBubble(posicion);
                        MemoryBubble.burbuja2 = null;
                        break;
                    }
                    if (MemoryBubble.burbuja2 == null){
                        MemoryBubble.burbuja2 = underSeaView.bubbleGrid.getBubble(posicion);
                    } else {
                        MemoryBubble.burbuja1 = underSeaView.bubbleGrid.getBubble(posicion);
                        MemoryBubble.burbuja2 = null;
                    }
                }
            }

            if (MemoryBubble.burbuja1 != null && MemoryBubble.burbuja2 != null){
                if (MemoryBubble.burbuja1.getNumber() == MemoryBubble.burbuja2.getNumber() && !MemoryBubble.burbuja1.equals(MemoryBubble.burbuja2)){
                    MemoryBubble.burbuja1.setBursted(true);
                    MemoryBubble.burbuja2.setBursted(true);
                    MemoryBubble.burbuja1 = null;
                    MemoryBubble.burbuja2 = null;
                }
            }
        }
    }

    public class Animation extends Thread{

        private static final int FPS = 25;
        private static final int MAX_FRAME_TIME = (int) (1000.0 / FPS);

        UnderSeaView underSeaView;

        boolean animationIsActive;
        MediaPlayer mediaPlayer;

        Animation(UnderSeaView underSeaView){
            this.underSeaView = underSeaView;
        }

        @Override
        public void run() {

            long frameStartTime;
            long frameTime;

            try {

                prepareMusic();
                underSeaView.initBubbles();

                animationIsActive = true;

                while (animationIsActive) {
                    frameStartTime = System.currentTimeMillis();

                    underSeaView.bubbleGrid.update();

                    frameTime = System.currentTimeMillis() - frameStartTime;
                    if (frameTime < MAX_FRAME_TIME) {
                        try {
                            Thread.sleep(MAX_FRAME_TIME - frameTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    underSeaView.postInvalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mediaPlayer != null)
                    mediaPlayer.release();
            }

        }

        private void prepareMusic() {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.rebels_be);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0.2f, 0.2f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });

        }

    }

}
