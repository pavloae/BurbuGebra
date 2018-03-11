package ar.com.andino.pablo.burbugebra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        //equipoMusica.cargarMusica(this, R.raw.opening);
        //equipoMusica.cargarEfecto(this, R.raw.pop1);
        //equipoMusica.reproducirMusica();
        super.onResume();
    }

    @Override
    protected void onPause() {
        introView.onPause();
        //equipoMusica.pausarMusica();
        super.onPause();
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        Log.v("TAG", "BubblesTime: " + System.currentTimeMillis());
        Intent intent = null;
        if (introView.burbujasOpciones[0].onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityFracciones.class);
        if (introView.burbujasOpciones[1].onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityOperaciones.class);
        if (introView.burbujasOpciones[2].onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivityEcuaciones.class);
        if (introView.burbujasOpciones[3].onTouchScreen(motionEvent.getX(), motionEvent.getY()))
            intent = new Intent(this, ActivitySistemas.class);
        if (intent == null)
            return false;
        startActivity(intent);
        return false;
    }
}
