package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.TermValue;

public class Term extends Operand implements FactorParent {

    public Term() {
        super(new Rational(0));
    }

    public Term(int numerator) {
        super(numerator);
    }

    public Term(int numerator, int denominator) {
        super(numerator, denominator);
    }

    public Term(String name) {
        super(name);
    }

    public Term(TermValue value){
        super(value);
    }

    @Override
    public void invert() {

        if (this.value instanceof Rational)
            ((Rational) this.value).numerator *= -1;

        else if (this.value instanceof GroupFactor)
            ((GroupFactor) this.value).add(0, new Factor(-1));

        else
            return;

        toggleOperation();

    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    protected final boolean group(Factor factor) throws CloneNotSupportedException {

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

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    protected final boolean group(Term term) throws CloneNotSupportedException {

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

    private boolean operateOnFactor(Rational termA, Rational factorB){

        termA.times(factorB);

        factorB.free();

        return true;
    }

    private boolean operateOnFactor(Rational termA, GroupTerm factorB) throws CloneNotSupportedException {
        this.setValue(
                new GroupFactor(
                        new Factor(termA.clone()),
                        new Factor(factorB.clone())
                )
        );
        factorB.getParent().free();
        return true;
    }

    private boolean operateOnFactor(GroupFactor termA, Rational factorB) throws CloneNotSupportedException {

        termA.add(
                new Factor(factorB.clone())
        );
        factorB.free();
        return true;

    }

    private boolean operateOnFactor(GroupFactor termA, GroupTerm factorB){

        termA.add(
                new Factor(factorB.clone())
        );

        factorB.getParent().free();

        return true;
    }

    private boolean operateOnTerm(Rational termA, Rational termB){

        if (termA.isVariable || termB.isVariable)
            return false;

        termA.plus(termB);

        termB.free();

        return true;
    }

    private boolean operateOnTerm(Rational termA, GroupFactor termB){

        ((GroupTerm) getParent()).add(getPositionOnParent()+1, new Term(termB.clone()));

        termB.getParent().free();

        return true;
    }

    private boolean operateOnTerm(GroupFactor termA, Rational termB) throws CloneNotSupportedException{

        if (termA.getVariable() != null ^ termB.isVariable)
            return false;

        ((GroupTerm) getParent()).add(getPositionOnParent()+1, (Term) termB.getParent().clone());
        termB.getParent().free();
        return true;
    }

    private boolean operateOnTerm(GroupFactor termA, GroupFactor termB) {

        if (
                termA.getVariable() != null
                        && termB.getVariable() != null
                        && !termA.getVariable().name.equals(termB.getVariable().name)
                )
            return false;

        if (
                termA.getVariable() != null
                        && termB.getVariable() != null
                        && termA.getVariable().name.equals(termB.getVariable().name)
                )
            termA.getCoeficient().plus(termB.getCoeficient());

        else
            ((GroupTerm) getParent()).add(getPositionOnParent()+1, new Term(termB.clone()));

        termB.getParent().free();

        return true;
    }

    @Override
    public String toString() {
        if (value == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        if (getPositionOnParent() > 0)
            stringBuilder.append((operation == 1) ? "+" : "-");

        if (this.value instanceof GroupTerm && parent != null && !((GroupTerm)parent).isEmpty())
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }

    // Interface Parent

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
            ((GroupTerm) parent).free(this);
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
            setValue(((GroupFactor) this.value).get(0).value);
            return;
        }

        // Si el Term contiene un GroupFactor con solo un GroupTerm,
        // agregamos el GroupTerm al Parent y removemos este Term del mismo
        if (
                this.value instanceof GroupFactor
                        && ((GroupFactor) this.value).size() == 1
                        && ((GroupFactor) this.value).get(0).value instanceof GroupTerm
                ) {
            ((GroupTerm) parent).addAll(
                    getPositionOnParent(),
                    ((GroupTerm) ((GroupFactor) this.value).get(0).value).clone()
            );
            ((GroupTerm) parent).free(this);
        }

    }



}
