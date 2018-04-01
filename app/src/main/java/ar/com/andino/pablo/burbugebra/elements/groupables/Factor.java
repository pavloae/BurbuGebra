package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.Equation;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.FactorValue;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class Factor implements Groupable<GroupFactor, FactorValue> {

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

    @Override
    public FactorValue getValue() {
        return value;
    }

    @Override
    public void setValue(FactorValue value) {
        this.value = value;
    }

    @Override
    public boolean group(Groupable groupable) {

        if (groupable instanceof Term)
            return false;

        if (getValue() instanceof Rational && groupable.getValue() instanceof Rational)
            return group(((Rational) this.value), ((Rational) groupable.getValue()));

        if (getValue() instanceof Rational && groupable.getValue() instanceof GroupTerm)
            return group(((Rational) this.value), ((GroupTerm) groupable.getValue()));

        if (getValue() instanceof GroupTerm && groupable.getValue() instanceof Rational)
            return group(((GroupTerm) this.value), ((Rational) groupable.getValue()));

        if (getValue() instanceof GroupTerm && groupable.getValue() instanceof GroupTerm)
            return group(((GroupTerm) this.value), ((GroupTerm) groupable.getValue()));

        return false;
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

    protected boolean group(Rational value1, Rational value2) {
        value1.setNumerator(value1.getNumerator() * value2.getNumerator());
        value1.setDenominator(value1.getDenominator() * value2.getDenominator());
        value2.getParent().getParent().removeValue(value2.getParent());
        return true;
    }

    protected boolean group(Rational value1, GroupTerm value2) {

        return false;
    }

    protected boolean group(GroupTerm value1, Rational value2) {
        Groupable oldParent = value2.getParent();
        for (Term term : value1){
            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(new Factor(value2));
            } else {
                term.setValue(new GroupFactor(
                        new Factor((Rational) term.getValue()),
                        new Factor(value2)
                ));
            }
        }
        oldParent.getParent().removeValue(value2.getParent());
        return true;
    }

    protected boolean group(GroupTerm value1, GroupTerm value2) {

        return false;
    }

}
