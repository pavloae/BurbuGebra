package ar.com.andino.pablo.burbugebra.bubbles;

import java.util.ArrayList;

public class FactorBubble extends Bubble {

    ArrayList<TermBubble> termBubbles = new ArrayList<>();
    FractionBubble burbujaFraccion;
    Character variable;

    public FactorBubble(int entero){
    }

    public FactorBubble(FractionBubble burbujaFraccion){
        this.burbujaFraccion = burbujaFraccion;
    }

    public FactorBubble(Character variable){
        this.variable = variable;
    }

    public FactorBubble(ArrayList<TermBubble> termBubbles) {
        this.termBubbles = termBubbles;
    }

    public void agregarTermino(TermBubble termBubble, int posicion){
        termBubbles.add(posicion, termBubble);
    }
}
