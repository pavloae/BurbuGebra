package ar.com.andino.pablo.burbugebra.bubbles;

import java.util.ArrayList;

public class TermBubble extends Bubble {

    private OperatorBubble termOperator;

    ArrayList<FactorBubble> factorBubbles = new ArrayList<>();
    FractionBubble fractionBubble;

    boolean positive;
    int selected;

    public TermBubble() {
        factorBubbles.add(0, new FactorBubble(1));
        termOperator = new OperatorBubble(OperatorBubble.PLUS);
    }

    public void agregarFactor(FactorBubble factorBubble, int posicion){
        factorBubbles.add(posicion, factorBubble);
    }

    public static TermBubble neutro(){
        TermBubble termBubble = new TermBubble();
        termBubble.factorBubbles.add(new FactorBubble(0));
        return termBubble;
    }

    public void addChar(Character character){

    }


}
