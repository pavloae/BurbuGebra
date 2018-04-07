package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public final class Rational implements FactorValue, TermValue, Cloneable {

    private Groupable parent;

    public int numerator;
    public int denominator;
    public String name;

    public Rational(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational(int numerator, int denominator) throws ArithmeticException {
        setValue(numerator, denominator);
    }

    public Rational(String name) {
        this.name = name;
    }

    public void setValue(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    private void setValue(int numerator, int denominator) throws ArithmeticException{
        if (denominator == 0)
            throw new ArithmeticException("División por cero");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public void free(){
        if (parent != null)
            parent.free();
        parent = null;
        name = null;
    }

    public Groupable getParent() {
        return parent;
    }

    public void simplify() {

    }

    @Override
    public String toString() {

        if (name != null)
            return name;

        StringBuilder stringBuilder = new StringBuilder();

        if (numerator * denominator < 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(
                Locale.ENGLISH,
                stringBuilder.toString(),
                (denominator == 1) ?
                        String.format(Locale.ENGLISH,"%d", numerator) :
                        String.format(Locale.ENGLISH,"%d/%d", numerator, denominator)
        );

    }

    // Interface TermValue

    //@Override
    public void setParent(Term parent) {
        this.parent = parent;
    }

    // Interface FactorValue

    //@Override
    public void setParent(Factor parent) {
        this.parent = parent;
    }

    // Interface Cloneable

    @Override
    public Rational clone() {
        try {
            Rational rational = (Rational) super.clone();
            rational.setParent((Term) null);
            return rational;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
