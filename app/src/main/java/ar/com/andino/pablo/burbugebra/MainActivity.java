package ar.com.andino.pablo.burbugebra;

import android.app.ActivityManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import ar.com.andino.pablo.burbugebra.activities.ActivityEcuaciones;
import ar.com.andino.pablo.burbugebra.activities.ActivityFracciones;
import ar.com.andino.pablo.burbugebra.activities.ActivityOperaciones;
import ar.com.andino.pablo.burbugebra.activities.ActivitySistemas;
import ar.com.andino.pablo.burbugebra.views.IntroView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    IntroView introView;
    //EquipoMusica equipoMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //equipoMusica = EquipoMusica.getInstance();
        introView = new IntroView(this);
        introView.setOnTouchListener(this);
        setContentView(introView);

    }

    @Override
    protected void onResume() {
        introView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        introView.onPause();
        super.onPause();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        Log.v("TAG", "BubblesTime: " + System.currentTimeMillis());
        Intent intent = null;
        if (introView.optionBubbles.getBubble(0).onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityFracciones.class);
        if (introView.optionBubbles.getBubble(1).onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityOperaciones.class);
        if (introView.optionBubbles.getBubble(2).onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityEcuaciones.class);
        if (introView.optionBubbles.getBubble(3).onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivitySistemas.class);

        if (intent == null){
            return false;
        }

        startActivity(intent);
        return false;
    }
}
