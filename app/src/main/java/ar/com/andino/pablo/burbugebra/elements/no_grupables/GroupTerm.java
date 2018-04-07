package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupTermParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupTerm extends ArrayList<Term> implements FactorValue, NoGroupable {

    private GroupParent parent;

    public GroupTerm() {
        super();
    }

    public GroupTerm(Term... terms) {
        super();
        addAll(Arrays.asList(terms));
    }

    public void free(Term value){
        super.remove(value);
        this.onUpdate();
    }

    private void onUpdate() {

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

    public GroupParent getParent() {
        return parent;
    }

    @Override
    public boolean add(Term term) {
        if (super.add(term))
            term.setParent(this);
        else
            return false;
        return true;
    }

    @Override
    public void add(int index, Term term) {
        super.add(index, term);
        term.setParent(this);
    }

    @Override
    public boolean addAll(Collection<? extends Term> groupTerm) {
        for (Term term : groupTerm)
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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Term term : this)
            stringBuilder.append(term.toString());

        return stringBuilder.toString();
    }

    @Override
    public GroupTerm clone() {
        GroupTerm groupTerm = (GroupTerm) super.clone();
        groupTerm.setParent(null);
        return groupTerm;
    }

    // Interface NoGroupable

    @Override
    public void setParent(GroupParent parent) {
        this.parent = parent;
    }

}
