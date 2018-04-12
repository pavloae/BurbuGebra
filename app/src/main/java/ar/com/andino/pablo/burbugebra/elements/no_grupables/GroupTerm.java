package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Parent;
import ar.com.andino.pablo.burbugebra.elements.groupables.TermParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupTerm extends ArrayList<Term> implements FactorValue, GroupOperand {

    private Parent parent;
    public int yGlobalCenter;
    public int widhtTotal;

    public GroupTerm() {
        super();
    }

    public GroupTerm(Term... terms) {
        super();
        addAll(Arrays.asList(terms));
    }

    public void setyGlobalCenter(int yGlobalCenter) {
        this.yGlobalCenter = yGlobalCenter;

        for (Term term : this)
            term.setCenterY(yGlobalCenter);

    }

    public void free(Term value){
        super.remove(value);
        this.widhtTotal -= 2 * value.getRadius();
        this.onUpdate();
    }

    private void onUpdate() {

        if (this.parent instanceof Equation && super.size() == 0){
            super.add(new Term());
            this.widhtTotal = (int) get(0).getRadius();
        } else if (
                this.parent instanceof Equation
                        && super.size() == 1
                        && super.get(0).value instanceof GroupFactor
                || this.parent instanceof Factor
                        && super.size() < 2
                )
            this.parent.onUpdate();

    }

    public Parent getParent() {
        return parent;
    }

    @Override
    public boolean add(Term term) {
        if (super.add(term))
            term.setParent(this);
        else
            return false;

        widhtTotal += 2 * term.getRadius();
        return true;
    }

    @Override
    public void add(int index, Term term) {
        super.add(index, term);
        term.setParent(this);

        widhtTotal += 2 * term.getRadius();

    }

    @Override
    public boolean addAll(Collection<? extends Term> groupTerm) {
        for (Term term : groupTerm)
            term.setParent(this);

        widhtTotal += ((GroupTerm) groupTerm).widhtTotal;

        return super.addAll(groupTerm);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Term> groupTerm) {
        for (Term term : groupTerm)
            term.setParent(this);

        widhtTotal += ((GroupTerm) groupTerm).widhtTotal;

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

        groupTerm.setParent((TermParent) null);

        for (Term term : groupTerm)
            term.setParent(groupTerm);

        return groupTerm;
    }

    @Override
    public void setParent(Operand parent) {
        this.parent = (Parent) parent;
    }


    // Interface FactorValue

    @Override
    public void setParent(TermParent parent) {
        this.parent = parent;
    }

    @Override
    public void free(Operand element) {
        super.remove(element);

        widhtTotal -= 2 * element.getRadius();

        onUpdate();
    }

    @Override
    public int indexOf(Operand operand) {
        return super.indexOf(operand);
    }

}
