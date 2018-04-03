package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupFactor extends ArrayList<Factor> implements TermValue, Cloneable {

    private Term parent;

    public GroupFactor() {
        super();
    }

    public GroupFactor(Factor... factors) {
        super();
        for (Factor factor : factors){
            factor.setParent(this);
            super.add(factor);
        }
    }

    public void onUpdate() {
        if (super.size() < 2 && this.parent != null)
            parent.onUpdate();
    }

    public void free(Factor value){
        super.remove(value);
        onUpdate();
    }

    @Override
    public boolean add(Factor factor) {
        if (!super.add(factor))
            return false;

        factor.setParent(this);
        return true;
    }

    @Override
    public void add(int index, Factor factor) {
        factor.setParent(this);
        super.add(index, factor);
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
    public Term getParent() {
        return parent;
    }

    @Override
    public void removeValue(Groupable value) {
        super.remove(value);
        onUpdate();
    }

    @Override
    public void setParent(Term parent) {
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
    public GroupFactor clone() {
        return (GroupFactor) super.clone();
    }
}
