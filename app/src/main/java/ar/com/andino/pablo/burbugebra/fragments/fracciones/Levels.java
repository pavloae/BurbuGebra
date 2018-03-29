package ar.com.andino.pablo.burbugebra.fragments.fracciones;

import android.content.Context;
import android.media.AudioManager;
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
    int resourceMusic = R.raw.levels;

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
        return inflater.inflate(R.layout.levels, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backButton = view.findViewById(R.id.back_button);
        Button level1 = view.findViewById(R.id.level1_button);
        Button level2 = view.findViewById(R.id.level2_button);

        backButton.setOnClickListener((ActivityFracciones) getActivity());
        level1.setOnClickListener((ActivityFracciones) getActivity());
        level2.setOnClickListener((ActivityFracciones) getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        playMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
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

}
