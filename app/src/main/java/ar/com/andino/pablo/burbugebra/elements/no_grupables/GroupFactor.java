package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class GroupFactor extends ArrayList<Factor> implements TermValue {

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

    public void free(Factor value){
        super.remove(value);
        if (super.size() == 0)
            parent.free();
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
    public Term getParent() {
        return parent;
    }

    @Override
    public void removeValue(Groupable value) {
        super.remove(value);
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

}
