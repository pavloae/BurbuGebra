package ar.com.andino.pablo.burbugebra.elementos;

import android.graphics.Bitmap;

import java.util.Locale;

public final class Rational implements FactorValue, TermValue {

    private int numerator;
    private int denominator;
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

    boolean operate(Rational rational){

        if (rational.parent instanceof Factor){
            this.numerator = this.numerator * rational.numerator;
            this.denominator = this.denominator * rational.denominator;
            return true;
        }

        if (this.parent instanceof Term) {
            this.numerator = numerator * rational.denominator + denominator * rational.numerator;
            this.denominator = denominator * rational.denominator;
            return true;
        }

        return false;

    }

    @Override
    public Groupable getParent() {
        return parent;
    }

    @Override
    public void setParent(Groupable parent) {
        this.parent = parent;
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

}
