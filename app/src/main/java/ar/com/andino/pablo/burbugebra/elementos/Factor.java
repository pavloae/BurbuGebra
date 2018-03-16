package ar.com.andino.pablo.burbugebra.elementos;

import java.util.ArrayList;

public class Factor {

    ArrayList<Termino> terminos;
    BubbleFraccion burbujaFraccion;
    Character variable;

    public Factor(int entero){
    }

    public Factor(BubbleFraccion burbujaFraccion){
        this.burbujaFraccion = burbujaFraccion;
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
