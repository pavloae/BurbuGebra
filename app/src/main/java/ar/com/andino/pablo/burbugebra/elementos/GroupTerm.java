package ar.com.andino.pablo.burbugebra.elementos;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GroupTerm extends ArrayList<Term> implements FactorValue {

    private Factor parent;

    public GroupTerm() {
        super();
    }

    public GroupTerm(Term... terms) {
        super();
        for (Term term : terms){
            term.setParent(this);
            super.add(term);
        }
    }

    public void distributive(FactorValue factorValue){
        for (Term term : this){
            Factor factor = new Factor();
            if (term.getValue() instanceof Rational){
                factor.setValue((Rational) term.getValue());
                term.setValue(new GroupFactor(factor));
            }
            ((GroupFactor) term.getValue()).add(0, factor);
            factor.setParent((GroupFactor) term.getValue());
        }

    }

    @Override
    public boolean add(Term term) {
        if (!super.add(term))
            return false;

        term.setParent(this);
        return true;
    }

    @Override
    public void add(int index, Term term) {
        term.setParent(this);
        super.add(index, term);
    }

    @Override
    @Nullable
    public Factor getParent() {
        return parent;
    }

    @Override
    public void setParent(Factor parent) {
        this.parent = (Factor) parent;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int position = 0; position < size(); position++)
            stringBuilder.append(get(position).toString());

        return stringBuilder.toString();
    }


}
