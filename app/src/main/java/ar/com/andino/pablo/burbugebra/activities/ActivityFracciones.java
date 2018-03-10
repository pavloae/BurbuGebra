package ar.com.andino.pablo.burbugebra.activities;

import android.app.FragmentManager;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

import ar.com.andino.pablo.burbugebra.EquipoMusica;
import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.fragments.fracciones.Nivel0;

public class ActivityFracciones extends FragmentActivity {

    private boolean active;

    FragmentManager fragmentManager;

    EquipoMusica equipoMusica;
    float scaleGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_operaciones_fracciones);

        fragmentManager = getFragmentManager();

        final Nivel0 nivel0 = Nivel0.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_layout, nivel0)
                .commit();


    }

}
