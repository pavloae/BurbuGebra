package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Canvas;

public class OperatorBubble extends Bubble {

    public final static String EQUAL = "=";
    public final static String PLUS = "+";
    public final static String MINUS = "-";
    public final static String TIMES = "·";
    public final static String OBELUS = ":";

    private String operator;

    OperatorBubble(String operator) {
        this.operator = operator;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
