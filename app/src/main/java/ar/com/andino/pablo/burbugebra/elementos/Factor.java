package ar.com.andino.pablo.burbugebra.elementos;

import java.util.Locale;

public final class Factor implements Groupable<GroupFactor, FactorValue> {

    private GroupFactor parent;
    private FactorValue value;

    public Factor() {
        this.value = new Rational(1);
        this.value.setParent(this);
    }

    public Factor(int numerator) {
        this.value = new Rational(numerator);
        this.value.setParent(this);
    }

    public Factor(int numerator, int denominator) {
        this.value = new Rational(numerator, denominator);
        this.value.setParent(this);
    }

    public Factor(FactorValue value){
        this.value = value;
        this.value.setParent(this);
    }

    public void addFactor(Factor factor) {

        if (this.value instanceof Rational && factor.value instanceof Rational){
            ((Rational) this.value).operate((Rational) factor.value);
            factor.getParent().remove(factor);
        }

        if (this.value instanceof GroupTerm && factor.value instanceof Rational) {
            ((GroupTerm) this.value).distributive(factor.value);
        }


    }

    @Override
    public FactorValue getValue() {
        return value;
    }

    @Override
    public void setValue(FactorValue value) {
        this.value = value;
    }

    @Override
    public GroupFactor getParent() {
        return parent;
    }

    @Override
    public void setParent(GroupFactor parent) {
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
            stringBuilder.append("·");

        if (value instanceof GroupTerm && parent.size() > 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }

}
