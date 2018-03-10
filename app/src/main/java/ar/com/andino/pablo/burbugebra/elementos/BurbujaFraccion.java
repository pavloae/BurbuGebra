package ar.com.andino.pablo.burbugebra.elementos;


import android.content.Context;
import android.support.annotation.Nullable;

import ar.com.andino.pablo.burbugebra.numeros.Entero;
import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;


public class BurbujaFraccion extends Burbuja {

    private BurbujaEntero numerador;
    private BurbujaEntero denominador;

    public BurbujaFraccion(Context context, int numerador, @Nullable Integer denominador) throws RuntimeException {
        super(null);
        if (denominador != null && denominador == 0)
            throw new RuntimeException("División por cero");
        this.numerador = BurbujaEntero.getInstance(context, numerador);
        this.denominador = BurbujaEntero.getInstance(context, (denominador == null) ? 1 : denominador);
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

    @Override
    public SpritesBubble getSpritesBubbles() {
        return null;
    }

    @Override
    public void onPressed() {
        super.onPressed();
    }

    @Override
    public void onPlop() {
        super.onPlop();
    }

}
