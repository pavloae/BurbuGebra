package ar.com.andino.pablo.burbugebra.Elementos;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.numeros.Fraccion;

public class Factor {

    ArrayList<Termino> terminos;
    Fraccion fraccion;
    Character variable;

    public Factor(int entero){
        fraccion = new Fraccion(entero, null);
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
