package ar.com.andino.pablo.burbugebra.elementos;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Miembro {

    private ArrayList<Termino> terminos = new ArrayList<>();
    int selected;

    public void agregarTermino(Termino termino, int posicion) {
        terminos.add(posicion, termino);
    }

    public static Miembro neutro(){
        Miembro miembro = new Miembro();
        miembro.terminos.add(Termino.neutro());
        return miembro;
    }

    public void addChar(Character character){
        terminos.get(selected).addChar(character);

    }

    public int obtenerAncho() {
        float random = new Random().nextFloat();

        return  20 + (int) (200 * random);

    }

    public void onDraw(Canvas canvas) {

    }

}
