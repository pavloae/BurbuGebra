package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Canvas;

import java.lang.annotation.ElementType;
import java.util.ArrayList;

public class MemberBubble<T extends Bubble> extends Bubble implements Elements {

    private Class<T> elementType;
    private ArrayList<T> termBubbles = new ArrayList<>();
    T[] elements;

    MemberBubble() {
    }

    public void addTerm(int position, T termBubble) {
        termBubbles.add(position, termBubble);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int position = 0; position < termBubbles.size(); position++)
            termBubbles.get(position).onDraw(canvas);
    }

    @Override
    public int getElementType() {

        if (elements == null || elements.length == 0)
            return Elements.INTEGER;
        if (termBubbles.get(0) instanceof  OperatorBubble)
            return 1;
        return 0;

    }
}
