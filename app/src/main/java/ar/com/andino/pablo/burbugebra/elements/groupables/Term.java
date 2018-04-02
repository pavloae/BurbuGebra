package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.TermValue;

public final class Term implements Groupable<GroupTerm, TermValue> {

    private GroupTerm parent;
    public TermValue value;

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

    public void onUpdate(){

        if (this.parent == null)
            return;

        // Si el termino solo contiene el nulo para la suma: "0" lo quitamos del grupo
        if (
                this.value instanceof Rational && ((Rational) this.value).numerator == 0
                        || this.value instanceof GroupFactor && ((GroupFactor) this.value).size() == 0
                ) {
            this.parent.removeValue(this);
            this.parent = null;
            return;
        }

        // Si el término contiene un grupo de factores con solo un Rational, apuntamos directamente
        // al racional desde este término
        if (
                this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 1
                        && ((GroupFactor) this.value).get(0).value instanceof Rational
                ) {
            this.value = (Rational) ((GroupFactor) this.value).get(0).value;
        }

        // Si el término contiene un grupo de factores con solo un GroupTerm, agregamos el GroupTerm
        // al Parent y removemos este Term del mismo
        if (
                this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 1
                        && ((GroupFactor) this.value).get(0).value instanceof GroupTerm
                ) {
            this.parent.addAll(
                    getPositionOnParent(),
                    (GroupTerm) ((GroupFactor) this.value).get(0).value
            );
            this.parent.removeValue(this);
        }

    }

    @Override
    public void free() {
        if (parent != null)
            parent.free(this);
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
    public boolean group(Groupable groupable) {

        if (this.value instanceof Rational) {

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof Rational)
                return operateOnFactor(
                        (Rational) this.value,
                        (Rational) ((Factor) groupable).value
                );

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof GroupTerm)
                return operateOnFactor(
                        (Rational) this.value,
                        (GroupTerm) ((Factor) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof Rational)
                return operateOnTerm(
                        (Rational) this.value,
                        (Rational) ((Term) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof GroupFactor)
                return operateOnTerm(
                        (Rational) this.value,
                        (GroupFactor) ((Term) groupable).value
                );

        }

        if (this.value instanceof GroupFactor) {

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof Rational)
                return operateOnFactor(
                        (GroupFactor) this.value,
                        (Rational) ((Factor) groupable).value
                );

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof GroupTerm)
                return operateOnFactor(
                        (GroupFactor) this.value,
                        (GroupTerm) ((Factor) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof Rational)
                return operateOnTerm(
                        (GroupFactor) this.value,
                        (Rational) ((Term) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof GroupFactor)
                return operateOnTerm(
                        (GroupFactor) this.value,
                        (GroupFactor) ((Term) groupable).value
                );

        }

        return false;
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

        if (this.value instanceof GroupTerm && parent.size() > 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
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
                        new Factor(factorB)
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
                new Factor(factorB)
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

        getParent().add(getPositionOnParent()+1, new Term(termB));

        return true;
    }

    protected boolean operateOnTerm(GroupFactor termA, Rational termB){

        getParent().add(getPositionOnParent()+1, new Term(termB));

        return true;
    }

    protected boolean operateOnTerm(GroupFactor termA, GroupFactor termB){

        getParent().add(getPositionOnParent()+1, new Term(termB));

        return true;
    }

}
