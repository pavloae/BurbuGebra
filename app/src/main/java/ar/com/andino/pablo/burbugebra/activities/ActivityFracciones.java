package ar.com.andino.pablo.burbugebra.activities;

import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_operaciones_fracciones);

        fragmentManager = getSupportFragmentManager();

        levels = Levels.newInstance();
        fragmentManager.beginTransaction().add(R.id.frame_fracciones, levels).commit();

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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_button:
                super.onBackPressed();
                break;
            case R.id.level1_button:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fracciones, Level1.newInstance());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.level2_button:
            case R.id.level3_button:
            case R.id.level4_button:
            case R.id.level5_button:
            case R.id.level6_button:
            case R.id.level7_button:
                break;
            default:


        }


    }

}
