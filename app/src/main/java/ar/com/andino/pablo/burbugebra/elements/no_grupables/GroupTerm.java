package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.Arrays;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupTerm extends GroupOperand<Term> implements FactorValue {

    public GroupTerm() {
        super();
    }

    public GroupTerm(Term... terms) {
        super();
        super.addAll(Arrays.asList(terms));
    }

    @Override
    public void onUpdate() {

        if (this.parent instanceof Equation && super.size() == 0)
            super.add(new Term());

        else if (
                this.parent instanceof Equation
                        && super.size() == 1
                        && super.get(0).value instanceof GroupFactor
                || this.parent instanceof Factor
                        && super.size() < 2
                )
            this.parent.onUpdate();

    }

    @Override
    public GroupTerm clone() {
        return (GroupTerm) super.clone();
    }
}
