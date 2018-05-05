package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.Arrays;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupFactor extends GroupOperand<Factor> implements TermValue {

    public GroupFactor() {
        super();
    }

    public GroupFactor(Factor... factors) {
        super();
        super.addAll(Arrays.asList(factors));
    }

    public Rational getVariable() {
        if (
                size() == 2
                        && get(0).value instanceof Rational
                        && get(1).value instanceof Rational
                        && ((Rational) get(0).value).isVariable ^ ((Rational) get(1).value).isVariable
                )
            return (Rational) ((((Rational) get(0).value).isVariable) ? get(0).value : get(1).value);
        return null;
    }

    public Rational getCoeficient() {
        if (
                size() == 2
                        && get(0).value instanceof Rational
                        && get(1).value instanceof Rational
                        && ((Rational) get(0).value).isVariable ^ ((Rational) get(1).value).isVariable
                )
            return (Rational) ((((Rational) get(0).value).isVariable) ? get(1).value : get(0).value);
        return null;
    }

    @Override
    public void onUpdate() {

        if (this.parent instanceof Equation && super.size() == 0)
            super.add(new Factor());

        else if (
                this.parent instanceof Equation
                        && super.size() == 1
                        && super.get(0).value instanceof GroupTerm
                        || this.parent instanceof Term
                        && super.size() < 2
                )
            this.parent.onUpdate();

    }

    @Override
    public boolean add(Factor factor) {

        if (super.size() == 0 && factor.operation == -1)
            factor.invert();

        return super.add(factor);

    }

    @Override
    public void add(int index, Factor factor) {

        if (index == 0 && factor.operation == -1)
            factor.invert();

        super.add(index, factor);

    }

    @Override
    public GroupFactor clone() {
        return (GroupFactor) super.clone();
    }
}
