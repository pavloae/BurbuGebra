package ar.com.andino.pablo.burbugebra.Elementos;

import java.util.ArrayList;
import java.util.HashMap;

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

}
