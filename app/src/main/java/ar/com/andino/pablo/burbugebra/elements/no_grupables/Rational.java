package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import java.security.acl.Group;
import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public final class Rational implements FactorValue, TermValue, Cloneable {

    public int numerator;
    public int denominator;
    private String name;
    private Groupable parent;

    public Rational(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational(int numerator, int denominator) throws ArithmeticException {
        if (denominator == 0)
            throw new ArithmeticException("División por cero");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational(String name) {
        this.name = name;
    }

    public void setValue(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public void setValue(int numerator, int denominator) throws ArithmeticException{
        if (denominator == 0)
            throw new ArithmeticException("División por cero");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public void free(){
        if (parent != null)
            parent.free();
        parent = null;
        name = null;
    }

    @Override
    public String toString() {

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

    @Override
    public Groupable getParent() {
        return parent;
    }

    @Override
    public void removeValue(Groupable value) {

    }

    @Override
    public void setParent(Factor parent) {
        this.parent = parent;
    }

    @Override
    public void setParent(Term parent) {
        this.parent = parent;
    }

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
