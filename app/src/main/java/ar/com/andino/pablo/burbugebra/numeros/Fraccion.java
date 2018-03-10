package ar.com.andino.pablo.burbugebra.numeros;

import android.support.annotation.Nullable;

public final class Fraccion {

    private Entero numerador;
    private Entero denominador;


    public Fraccion(int numerador, @Nullable Integer denominador) throws RuntimeException {
        if (denominador != null && denominador == 0)
            throw new RuntimeException("División por cero");
    }

    public int numerador(){
        return numerador.getValor();
    }

    public int denominador(){
        return denominador.getValor();
    }

    public void simplificar(){

        int maximoComunDivisor = Entero.obtenerMCD(numerador(), denominador());

        numerador.dividirPor(maximoComunDivisor);
        denominador.dividirPor(maximoComunDivisor);

    }


}
