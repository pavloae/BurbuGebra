package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.FactorValue;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class Factor extends Operand implements TermParent {

    private boolean invert;

    public Factor() {
        super(new Rational(1));
    }

    public Factor(int numerator) {
        super(numerator);
    }

    public Factor(int numerator, int denominator) {
        super(numerator, denominator);
    }

    public Factor(String name) {
        super(name);
    }

    public Factor(FactorValue value){
        super(value);
    }

    @Override
    public void invert() {

        if (this.value instanceof Rational && ((Rational) this.value).numerator != 0) {
            int numerator = ((Rational) this.value).numerator;
            ((Rational) this.value).numerator = ((Rational) this.value).denominator;
            ((Rational) this.value).denominator = numerator;
        }

        else if (this.value instanceof GroupTerm)
            invert = (!invert);

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

        if (this.value instanceof GroupTerm && factor.value instanceof Rational)
            return operateOnFactor(
                    (GroupTerm) this.value,
                    (Rational) factor.value
            );


        if (this.value instanceof GroupTerm && factor.value instanceof GroupTerm)
            return operateOnFactor(
                    (GroupTerm) this.value,
                    (GroupTerm) factor.value
            );

        return false;
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    protected final boolean group(Term term) {

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

        if (this.value instanceof GroupTerm && term.value instanceof Rational)
            return operateOnTerm(
                    (GroupTerm) this.value,
                    (Rational) term.value
            );

        if (this.value instanceof GroupTerm && term.value instanceof GroupFactor)
            return operateOnTerm(
                    (GroupTerm) this.value,
                    (GroupFactor) term.value
            );

        return false;
    }

    private boolean operateOnFactor(Rational factorA, Rational factorB){

        if (((Factor) factorA.getParent()).operation == -1)
            factorA.getParent().invert();

        if (((Factor) factorB.getParent()).operation == -1)
            factorB.getParent().invert();

        if (factorA.isZero() || factorB.isZero())
            factorA.setZero();

        else if (factorA.isVariable || factorB.isVariable)
            return false;

        factorA.numerator *= factorB.numerator;
        factorA.denominator *= factorB.denominator;

        factorB.free();

        return true;
    }

    private boolean operateOnFactor(Rational factorA, GroupTerm factorB) throws CloneNotSupportedException {

        this.value = new GroupTerm();

        for (Term term : factorB){
            if (term.getValue() instanceof Rational) {
                ((GroupTerm) this.value).add(
                        new Term(
                                new GroupFactor(
                                        new Factor(factorA.clone()),
                                        new Factor(((Rational) term.getValue()).clone())
                                )
                        )
                );
            }

            if (term.getValue() instanceof GroupFactor){
                GroupFactor groupFactor = new GroupFactor();
                groupFactor.add(new Factor(factorA.clone()));
                groupFactor.addAll(((GroupFactor) term.getValue()).clone());

                ((GroupTerm) this.value).add(new Term(groupFactor));
            }

        }
        if (factorB.getParent() != null)
            factorB.getParent().free();
        return true;
    }

    private boolean operateOnFactor(GroupTerm factorA, Rational factorB) throws CloneNotSupportedException {

        for (Term term : factorA){

            if (term.getValue() instanceof Rational) {
                term.setValue(
                        new GroupFactor(
                                new Factor((Rational) term.getValue()),
                                new Factor(factorB.clone())
                        )
                );
            }

            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(new Factor(factorB.clone()));
            }

        }

        factorB.free();

        return true;
    }

    private boolean operateOnFactor(GroupTerm factorA, GroupTerm factorB) throws CloneNotSupportedException {

        GroupTerm groupTerm = new GroupTerm();

        for (Term termA : factorA) {

            for (Term termB : factorB) {

                GroupFactor groupFactor = new GroupFactor();

                if (termA.value instanceof Rational)
                    groupFactor.add(new Factor(((Rational) termA.value).clone()));
                else
                    groupFactor.addAll(((GroupFactor) termA.value).clone());

                if (termB.value instanceof Rational)
                    groupFactor.add(new Factor(((Rational) termB.value).clone()));
                else
                    groupFactor.addAll(((GroupFactor) termB.value).clone());

                groupTerm.add(
                        new Term(
                                groupFactor
                        )
                );

            }

        }

        this.setValue(groupTerm);

        if (factorB.getParent() != null)
            factorB.getParent().free();

        return true;

    }

    private boolean operateOnTerm(Rational factorA, Rational termB){
        return false;
    }

    private boolean operateOnTerm(Rational factorA, GroupFactor termB){
        return false;
    }

    private boolean operateOnTerm(GroupTerm factorA, Rational termB){
        return false;
    }

    private boolean operateOnTerm(GroupTerm factorA, GroupFactor termB){
        return false;
    }

    @Override
    public String toString() {
        if (value == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        if (getPositionOnParent() > 0)
            stringBuilder.append((operation == 1) ? "·" : ":");

        if (value instanceof GroupTerm && invert)
            stringBuilder.append("(1/(%s))");
        else if (value instanceof GroupTerm && !invert)
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

        // Si el Factor solo contiene un Rational nulo para la multiplicación ("1")
        // ó un GroupTerm vacío, lo quitamos del GroupFactor.
        if (
                this.value instanceof Rational
                        && ((Rational) this.value).numerator == 1
                        && ((Rational) this.value).denominator == 1
                        || this.value instanceof GroupTerm
                        && ((GroupTerm) this.value).size() == 0
                ) {
            ((GroupFactor) parent).free(this);
            this.parent = null;
            this.value = null;
            return;
        }

        // Si el Factor contiene un GroupTerm con solo un Rational,
        // apuntamos directamente al Rational desde este Factor
        if (
                this.value instanceof GroupTerm
                        && ((GroupTerm) this.value).size() == 1
                        && ((GroupTerm) this.value).get(0).value instanceof Rational
                ) {
            setValue(((GroupTerm) this.value).get(0).value);
        }

        // Si el Factor contiene un GroupTerm con solo un GroupFactor,
        // agregamos el GroupFactor al Parent y removemos este Term del mismo
        if (
                this.value instanceof GroupTerm
                        && ((GroupTerm) this.value).size() == 1
                        && ((GroupTerm) this.value).get(0).value instanceof GroupFactor
                ) {
            ((GroupFactor) parent).addAll(
                    getPositionOnParent(),
                    ((GroupFactor) ((GroupTerm) this.value).get(0).value).clone()
            );
            ((GroupFactor) parent).free(this);
        }

    }

}
