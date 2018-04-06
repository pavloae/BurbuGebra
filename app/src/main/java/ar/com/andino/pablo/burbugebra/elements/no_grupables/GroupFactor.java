package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupFactorParent;
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
        if (this.parent != null && super.size() < 2)
            this.parent.onUpdate();
    }

    public GroupFactorParent getParent() {
        return parent;
    }

    @Override
    public boolean add(Factor factor) {
        if (super.add(factor))
            factor.setParent(this);
        else
            return false;
        return true;
    }

    @Override
    public void add(int index, Factor factor) {
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
    public void setParent(Term parent) {
        this.parent = parent;
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
        groupFactor.setParent(null);
        return groupFactor;
    }

}
