package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.TermValue;

public final class Term implements Groupable<GroupTerm, TermValue> {

    private GroupTerm parent;
    private TermValue value;

    public Term() {
        this.value = new Rational(0);
        this.value.setParent(this);
    }

    public Term(int numerator) {
        this.value = new Rational(numerator);
        this.value.setParent(this);
    }

    public Term(int numerator, int denominator) {
        this.value = new Rational(numerator, denominator);
        this.value.setParent(this);
    }

    public Term(TermValue value){
        this.value = value;
        this.value.setParent(this);
    }

    @Override
    public TermValue getValue() {
        return value;
    }

    @Override
    public void setValue(TermValue value) {
        this.value = value;
    }

    @Override
    public GroupTerm getParent() {
        return parent;
    }

    @Override
    public void setParent(GroupTerm parent) {
        if (this.parent != null)
            this.parent.remove(this);
        this.parent = parent;
    }

    @Override
    public int getPositionOnParent() {
        return parent.indexOf(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (getPositionOnParent() > 0)
            stringBuilder.append("+");

        if (value instanceof GroupTerm && parent.size() > 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }
}
