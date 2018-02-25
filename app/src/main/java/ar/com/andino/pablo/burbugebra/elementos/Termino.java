package ar.com.andino.pablo.burbugebra.elementos;

import java.util.ArrayList;

public class Termino {

    ArrayList<Factor> factores = new ArrayList<>();
    int selected;

    public Termino() {
        factores = new ArrayList<>();
        factores.add(0, new Factor(1));
    }

    public void agregarFactor(Factor factor, int posicion){
        factores.add(posicion, factor);
    }

    public static Termino neutro(){
        Termino termino = new Termino();
        termino.factores.add(new Factor(0));
        return termino;
    }

    public void addChar(Character character){

    }


}
