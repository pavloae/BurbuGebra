package ar.com.andino.pablo.burbugebra.elements.groupables;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Value;

public abstract class Operand implements Groupable, Cloneable {

    public int operation = 1;

    public Value value;

    public Operand(){

    }

    public Operand(int numerator) {
        this.value = new Rational(numerator);
        this.value.setParent(this);
    }

}
