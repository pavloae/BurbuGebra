package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.TermValue;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Value;

public final class Term implements GroupFactorParent, Groupable, Cloneable {

    private GroupTerm parent;

    public TermValue value;

    private int operation = 1;

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

    public Term(String name) {
        this.value = new Rational(name);
        this.value.setParent(this);
    }

    public Term(TermValue value){
        this.value = value;
        this.value.setParent(this);
    }

    public Term switchOperation() {
        operation *= -1;
        return this;
    }

    public void setParent(GroupTerm parent) {
        this.parent = parent;
    }

    public GroupTerm getParent() {
        return parent;
    }

    public int getPositionOnParent() {
        if (parent == null)
            return -1;
        return parent.indexOf(this);
    }

    public void setValue(TermValue value) {
        this.value = value;
        this.value.setParent(this);
    }

    public TermValue getValue() {
        return value;
    }

    public final boolean group(Factor factor) {

        if (this.value instanceof Rational && factor.value instanceof Rational)
            return operateOnFactor(
                    (Rational) this.value,
                    (Rational) factor.value
            );

        if (this.value instanceof Rational && factor.value instanceof GroupTerm)
            return operateOnFactor(
                    (Rational) this.value,
                    (GroupTerm) factor.value
            );

        if (this.value instanceof GroupFactor && factor.value instanceof Rational)
            return operateOnFactor(
                    (GroupFactor) this.value,
                    (Rational) factor.value
            );

        if (this.value instanceof GroupFactor && factor.value instanceof GroupTerm)
            return operateOnFactor(
                    (GroupFactor) this.value,
                    (GroupTerm) factor.value
            );

        return false;
    }

    public final boolean group(Term term) {

        if (this.value instanceof Rational && term.value instanceof Rational)
            return operateOnTerm(
                    (Rational) this.value,
                    (Rational) term.value
            );

        if (this.value instanceof Rational && term.value instanceof GroupFactor)
            return operateOnTerm(
                    (Rational) this.value,
                    (GroupFactor) term.value
            );

        if (this.value instanceof GroupFactor && term.value instanceof Rational)
            return operateOnTerm(
                    (GroupFactor) this.value,
                    (Rational) term.value
            );

        if (this.value instanceof GroupFactor && term.value instanceof GroupFactor)
            return operateOnTerm(
                    (GroupFactor) this.value,
                    (GroupFactor) term.value
            );

        return false;

    }

    @Override
    public void onUpdate(){

        if (this.parent == null)
            return;

        // Si el Term solo contiene un Rational nulo para la suma ("0")
        // ó un GroupFactor vacío, lo quitamos del GroupTerm.
        if (
                this.value instanceof Rational
                        && ((Rational) this.value).numerator == 0
                        || this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 0
                ) {
            this.parent.free(this);
            this.parent = null;
            this.value = null;
            return;
        }

        // Si el Term contiene un GroupFactor con solo un Rational,
        // apuntamos directamente al Rational desde este Term
        if (
                this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 1
                        && ((GroupFactor) this.value).get(0).value instanceof Rational
                ) {
            setValue((Rational) ((GroupFactor) this.value).get(0).value);
            return;
        }

        // Si el Term contiene un GroupFactor con solo un GroupTerm,
        // agregamos el GroupTerm al Parent y removemos este Term del mismo
        if (
                this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 1
                        && ((GroupFactor) this.value).get(0).value instanceof GroupTerm
                ) {
            this.parent.addAll(
                    getPositionOnParent(),
                    ((GroupTerm) ((GroupFactor) this.value).get(0).value).clone()
            );
            this.parent.free(this);
        }

    }

    @Override
    public void free() {
        if (parent != null)
            parent.free(this);
        this.parent = null;
        this.value = null;
    }

    @Override
    public String toString() {
        if (value == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        if (getPositionOnParent() > 0)
            stringBuilder.append((operation == 1) ? "+" : "-");

        if (this.value instanceof GroupTerm && parent != null && !parent.isEmpty())
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }

    @Override
    public Term clone() throws CloneNotSupportedException {
        Term term = (Term) super.clone();
        term.setParent(null);
        return term;
    }

    protected boolean operateOnFactor(Rational termA, Rational factorB){

        termA.numerator *= factorB.numerator;
        termA.denominator *= factorB.denominator;

        factorB.free();

        return true;
    }

    protected boolean operateOnFactor(Rational termA, GroupTerm factorB){

        this.setValue(
                new GroupFactor(
                        new Factor(termA.clone()),
                        new Factor(factorB.clone())
                )
        );

        factorB.getParent().free();

        return true;
    }

    protected boolean operateOnFactor(GroupFactor termA, Rational factorB){

        termA.add(
                new Factor(factorB.clone())
        );

        factorB.free();

        return true;
    }

    protected boolean operateOnFactor(GroupFactor termA, GroupTerm factorB){

        termA.add(
                new Factor(factorB.clone())
        );

        factorB.getParent().free();

        return true;
    }

    protected boolean operateOnTerm(Rational termA, Rational termB){

        termA.numerator = termA.numerator * termB.denominator + termA.denominator * termB.numerator;
        termA.denominator = termA.denominator * termB.denominator;

        termB.free();

        return true;
    }

    protected boolean operateOnTerm(Rational termA, GroupFactor termB){

        getParent().add(getPositionOnParent()+1, new Term(termB.clone()));

        termB.getParent().free();

        return true;
    }

    protected boolean operateOnTerm(GroupFactor termA, Rational termB){

        getParent().add(getPositionOnParent()+1, new Term(termB.clone()));

        termB.free();

        return true;
    }

    protected boolean operateOnTerm(GroupFactor termA, GroupFactor termB){

        getParent().add(getPositionOnParent()+1, new Term(termB.clone()));

        termB.getParent().free();

        return true;
    }

}
