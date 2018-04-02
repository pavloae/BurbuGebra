package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

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

    public void free(Term value){
        super.remove(value);
        if (super.size() == 0)
            parent.free();
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
    public Factor getParent() {
        return parent;
    }

    @Override
    public void removeValue(Groupable value) {
        super.remove(value);
    }

    @Override
    public void setParent(Factor parent) {
        if (this.parent != null)
            this.parent.free();
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int position = 0; position < size(); position++)
            stringBuilder.append(get(position).toString());

        return stringBuilder.toString();
    }


}
