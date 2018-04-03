package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupTerm extends ArrayList<Term> implements FactorValue, Cloneable {

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

    public void onUpdate() {
        if (parent != null && super.size() < 2)
            parent.onUpdate();
    }

    public void free(Term value){
        super.remove(value);
        this.onUpdate();
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
    public boolean addAll(Collection<? extends Term> groupTerm) {
        for (Term term : groupTerm)
            if (term.getParent() != null && term.getParent() != this)
                term.setParent(this);
        return super.addAll(groupTerm);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Term> groupTerm) {
        for (Term term : groupTerm)
            term.setParent(this);
        return super.addAll(index, groupTerm);
    }

    @Override
    public Factor getParent() {
        return parent;
    }

    @Override
    public void removeValue(Groupable value) {
        super.remove(value);
        onUpdate();
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

    @Override
    public GroupTerm clone() {
        return (GroupTerm) super.clone();
    }
}
