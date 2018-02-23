package ar.com.andino.pablo.burbugebra.numeros;

import android.support.annotation.Nullable;

public final class Fraccion {

    private Entero numerador;
    private Entero denominador = new Entero(1);


    public Fraccion(int numerador, @Nullable Integer denominador) throws RuntimeException {
        if (denominador != null && denominador == 0)
            throw new RuntimeException("División por cero");
        if (denominador != null)
            this.denominador = new Entero(denominador);
        this.numerador = new Entero(numerador);
    }

    public int numerador(){
        return numerador.obtenerValor();
    }

    public int denominador(){
        return denominador.obtenerValor();
    }

    public void simplificar(){

        int maximoComunDivisor = Entero.obtenerMCD(numerador(), denominador());

        numerador.dividirPor(maximoComunDivisor);
        denominador.dividirPor(maximoComunDivisor);

    }


}
