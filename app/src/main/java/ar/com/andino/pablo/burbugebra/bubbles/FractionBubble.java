package ar.com.andino.pablo.burbugebra.bubbles;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ar.com.andino.pablo.burbugebra.bubbles.Bubble;
import ar.com.andino.pablo.burbugebra.bubbles.IntegerBubble;
import ar.com.andino.pablo.burbugebra.numeros.Entero;


public class FractionBubble extends Bubble {

    private IntegerBubble numerador;
    private IntegerBubble denominador;

    public FractionBubble(Context context, int numerador, @Nullable Integer denominador) throws RuntimeException {
        //super(null);
        if (denominador != null && denominador == 0)
            throw new RuntimeException("División por cero");
        this.numerador = IntegerBubble.getInstance(context, numerador);
        this.denominador = IntegerBubble.getInstance(context, (denominador == null) ? 1 : denominador);
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

    @NonNull
    @Override
    public Bitmap getBitmap() {
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
