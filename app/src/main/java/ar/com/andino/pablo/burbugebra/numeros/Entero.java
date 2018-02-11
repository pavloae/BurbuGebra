package ar.com.andino.pablo.burbugebra.numeros;

import java.util.HashMap;

public class Entero {

    int value;

    public Entero(int value) {
        this.value = value;
    }

    public HashMap<Integer, Integer> obtenerFactoresPrimos(){

        HashMap<Integer, Integer> factores = new HashMap<>();

        int partial = value;

        int limit;
        if (value % 2 == 0){
            limit = value / 2;
        } else {
            limit = (value - 1) / 2;
        }


        for (int factor = 2 ; factor <= limit ; factor++) {

            if (partial % factor != 0)
                continue;

            int potencia = 1;

            while (partial % factor == 0){
                partial = partial / factor;
                potencia++;
            }

            factores.put(factor, potencia);

        }

        return factores;
    }

}
