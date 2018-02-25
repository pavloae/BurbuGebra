package ar.com.andino.pablo.burbugebra.elementos;

import java.util.ArrayList;

public class Factor {

    ArrayList<Termino> terminos;
    Fraccion fraccion;
    Character variable;

    public Factor(int entero){
        fraccion = new Fraccion();
    }

    public Factor(Fraccion fraccion){
        this.fraccion = fraccion;
    }

    public Factor(Character variable){
        this.variable = variable;
    }

    public Factor(ArrayList<Termino> terminos) {
        this.terminos = terminos;
    }

    public void agregarTermino(Termino termino, int posicion){
        terminos.add(posicion, termino);
    }
}
