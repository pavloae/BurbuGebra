package ar.com.andino.pablo.burbugebra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.List;

public class EquipoMusica {

    private MediaPlayer mediaPlayer;
    private float volMusicaI, volMusicaD;
    List<Integer> listaMusicas;

    private SoundPool soundPool;
    private float volEfectI, volEfectD;
    private List<Integer> listaEfectos;

    private static EquipoMusica equipoMusica;

    public static EquipoMusica getInstance() {
        if (equipoMusica == null)
            equipoMusica = new EquipoMusica();
        return equipoMusica;
    }

    private EquipoMusica() {
        mediaPlayer = new MediaPlayer();
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        volMusicaI = 0.5f;
        volMusicaD = 0.5f;
        volEfectI = 0.5f;
        volEfectD = 0.5f;
    }

    public void cargarEfecto(Context context, int resource) {
        if (listaEfectos == null)
            listaEfectos = new ArrayList<>();
        listaEfectos.add(soundPool.load(context, resource, 1));
    }

    public void reproducirEfecto(int pista) {
        soundPool.play(listaEfectos.get(pista), volEfectI, volEfectD, 1, 0, 1);
    }

    public void cargarMusica(Context context, int resource) {
        mediaPlayer = MediaPlayer.create(context, resource);
        mediaPlayer.setVolume(0.2f, 0.2f);
    }

    public void reproducirMusica() {
        if (mediaPlayer == null)
            return;
        mediaPlayer.start();
    }

    public void pausarMusica() {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    private void detenerMusica() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
