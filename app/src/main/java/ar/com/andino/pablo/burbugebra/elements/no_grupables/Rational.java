package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.groupables.Parent;
import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;

public final class Rational implements Value, FactorValue, TermValue {

    private Operand parent;

    public int numerator;
    public int denominator;
    public String name;
    public boolean isVariable;

    public Rational(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational(int numerator, int denominator) throws ArithmeticException {
        setValue(numerator, denominator);
    }

    public Rational(String name) {
        this.name = name;
        this.isVariable = true;
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

    public Operand getParent() {
        return parent;
    }

    public void free(){
        if (parent != null)
            parent.free();
        parent = null;
        name = null;
    }

    public void simplify() {

        HashMap<Integer, Integer> factorNumerator = getPrimeFactors(Math.abs(numerator));
        HashMap<Integer, Integer> factorDenominator = getPrimeFactors(Math.abs(denominator));

        int mcd = 1;

        for (int fn : factorNumerator.keySet()){
            for (int fd : factorDenominator.keySet())
                if (fn == fd)
                    mcd *= Math.pow(fd, Math.min(factorNumerator.get(fn), factorDenominator.get(fd)));
        }

        numerator /= mcd;
        denominator /= mcd;

    }

    public boolean isZero(){
        return !isVariable && numerator == 0;
    }

    public void setZero() {
        this.numerator = 0;
        this.denominator = 1;
        this.name = null;
        this.isVariable = false;
    }

    public void times(Rational rational){
        this.numerator *= rational.numerator;
        this.denominator *= rational.denominator;
    }

    public void plus(Rational rational){
        this.numerator = this.numerator * rational.denominator + this.denominator * rational.numerator;
        this.denominator = this.denominator * rational.denominator;
    }

    @Override
    public String toString() {

        if (isVariable)
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

    // Interface Value

    @Override
    public Rational cloneAsValue() throws CloneNotSupportedException {
        Rational rational = (Rational) super.clone();
        rational.setParent(null);
        return rational;
    }

    @Override
    public void setParent(Parent parent) {
        if (parent == null || parent instanceof Operand)
            this.parent = (Operand) parent;
    }


    private static HashMap<Integer, Integer> getPrimeFactors(int number) {

        @SuppressLint("UseSparseArrays")
        HashMap<Integer, Integer> primeFactors = new HashMap<>();

        int dividend = number;
        int divisor = 2;
        while (dividend > 1){
            if (dividend % divisor == 0 && !primeFactors.containsKey(divisor)){
                primeFactors.put(divisor, 1);
                dividend = dividend / divisor;
            } else if (dividend % divisor == 0 && primeFactors.get(divisor) > 0) {
                primeFactors.put(divisor, primeFactors.get(divisor) + 1);
                dividend = dividend / divisor;
            }
            else
                divisor = getNextPrime(divisor);
        }
        return primeFactors;
    }

    private static int getNextPrime(int downLimit){
        int nextPrime = downLimit + 1;
        while (!isPrime(nextPrime))
            nextPrime++;
        return nextPrime;
    }

    private static boolean isPrime(int number) {
        for (int divisor = 2 ; divisor <= (number / 2) ; divisor++)
            if (number % divisor == 0)
                return false;
        return true;
    }

}
