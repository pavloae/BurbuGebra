package ar.com.andino.pablo.burbugebra.activities;

import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.fragments.fracciones.Level1;
import ar.com.andino.pablo.burbugebra.fragments.fracciones.Levels;

public class ActivityFracciones extends FragmentActivity implements View.OnClickListener {

    FragmentManager fragmentManager;
    Levels levels;
    Level1 level1;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_operaciones_fracciones);

        fragmentManager = getSupportFragmentManager();

        levels = Levels.newInstance();
        level1 = Level1.newInstance();


        /*
        fragmentManager.beginTransaction()
                .add(R.id.frame_fracciones, levels)
                .commit();
             */

        fragmentManager.beginTransaction().replace(R.id.frame_fracciones, levels).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        fragmentManager.beginTransaction().replace(R.id.frame_fracciones, levels).commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.level1_button:
                fragmentManager.beginTransaction().replace(R.id.frame_fracciones, level1).commit();
                break;
            default:
                Log.v("ONCLICK", "NADA" + view.getId());

        }


    }

}
