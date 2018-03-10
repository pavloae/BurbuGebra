package ar.com.andino.pablo.burbugebra.numeros;

import android.annotation.SuppressLint;

import java.util.HashMap;


public abstract class Entero implements InterfazEntero {

    private int valor = 1;

    public Entero(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int dividirPor(int divisor) {
        int resto = valor % divisor;
        valor = valor / divisor;
        return resto;
    }

    public static int obtenerMCD(int valorA, int valorB) {

        valorA = Math.abs(valorA);
        valorB = Math.abs(valorB);

        int mcd = 1;

        HashMap<Integer, Integer> factoresNumerador, factoresDenominador;
        factoresNumerador = factoresPrimos(valorA);
        factoresDenominador = factoresPrimos(valorB);

        for (int factorNumerador : factoresNumerador.keySet()){

            if (factoresDenominador.containsKey(factorNumerador))
                mcd = mcd * (int) Math.pow(
                        factorNumerador,
                        Math.min(
                                factoresNumerador.get(factorNumerador),
                                factoresDenominador.get(factorNumerador)
                        )
                );
        }
        return mcd;
    }

    public static int obtenerMCM(int valorA, int valorB){
        valorA = Math.abs(valorA);
        valorB = Math.abs(valorB);

        int mcd = obtenerMCD(valorA, valorB);

        return (valorA * valorB) / mcd;
    }

    public static HashMap<Integer, Integer> factoresPrimos(int valor) {

        @SuppressLint("UseSparseArrays")
        HashMap<Integer, Integer> factoresPrimos = new HashMap<>();

        valor = Math.abs(valor);

        int dividendo = Math.abs(valor);

        int limit;
        if (dividendo % 2 == 0){
            limit = dividendo / 2;
        } else {
            limit = (dividendo - 1) / 2;
        }

        for (int factor = 2 ; factor <= limit ; factor++) {

            if (dividendo % factor != 0)
                continue;

            int potencia = 0;

            while (dividendo % factor == 0){
                dividendo = dividendo / factor;
                potencia++;
            }

            factoresPrimos.put(factor, potencia);

        }

        return factoresPrimos;

    }

}
