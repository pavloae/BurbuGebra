package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Canvas;

public class VariableBubble extends Bubble {

    public final static String X = "x";
    public final static String Y = "y";

    private String variable;

    VariableBubble(String variable) {
        this.variable = variable;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
