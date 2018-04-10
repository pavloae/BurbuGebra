package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupFactorParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupFactor extends ArrayList<Factor> implements TermValue, NoGroupable {

    private GroupFactorParent parent;

    public GroupFactor() {
        super();
    }

    public GroupFactor(Factor... factors) {
        super();
        addAll(Arrays.asList(factors));
    }

    public void free(Factor value){
        super.remove(value);
        onUpdate();
    }

    private void onUpdate() {

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

    public GroupParent getParent() {
        return parent;
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
    public boolean add(Factor factor) {

        if (super.size() == 0 && factor.operation == -1)
            factor.invert();

        if (super.add(factor))
            factor.setParent(this);
        else
            return false;
        return true;
    }

    @Override
    public void add(int index, Factor factor) {

        if (index == 0 && factor.operation == -1)
            factor.invert();

        super.add(index, factor);
        factor.setParent(this);
    }

    @Override
    public boolean addAll(Collection<? extends Factor> groupFactor) {
        for (Factor factor : groupFactor)
            factor.setParent(this);
        return super.addAll(groupFactor);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Factor> groupFactor) {
        for (Factor factor : groupFactor)
            factor.setParent(this);
        return super.addAll(index, groupFactor);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Factor factor : this)
            stringBuilder.append(factor.toString());

        return stringBuilder.toString();
    }

    @Override
    public GroupFactor clone() {

        GroupFactor groupFactor = (GroupFactor) super.clone();

        groupFactor.setParent((GroupFactorParent) null);

        for (Factor factor : groupFactor)
            factor.setParent(groupFactor);

        return groupFactor;
    }

    @Override
    public void setParent(Operand parent) {

    }

    // Interface TermValue

    @Override
    public void setParent(GroupFactorParent parent) {
        this.parent = parent;
    }

}
