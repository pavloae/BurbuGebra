package ar.com.andino.pablo.burbugebra.elementos;

import android.support.annotation.Nullable;

import java.util.ArrayList;

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
    @Nullable
    public Term getParent() {
        return parent;
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
