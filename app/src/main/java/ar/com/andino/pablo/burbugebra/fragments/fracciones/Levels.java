package ar.com.andino.pablo.burbugebra.fragments.fracciones;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.activities.ActivityFracciones;

public class Levels extends Fragment {

    MediaPlayer mediaPlayer;

    public Levels() {
        super();
    }

    public static Levels newInstance(){
        return new Levels();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.levels, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button backButton = view.findViewById(R.id.back_button);
        Button level0 = view.findViewById(R.id.level1_button);
        backButton.setOnClickListener((ActivityFracciones) getActivity());
        level0.setOnClickListener((ActivityFracciones) getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareMusic(R.raw.levels);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopMusic();
    }

    private void prepareMusic(int resource) {
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getContext(), resource);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0.2f, 0.2f);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });

    }

    public void stopMusic() {
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
    }

}
