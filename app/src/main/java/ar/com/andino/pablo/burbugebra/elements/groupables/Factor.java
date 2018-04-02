package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.FactorValue;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class Factor implements Groupable<GroupFactor, FactorValue> {

    private GroupFactor parent;
    public FactorValue value;

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

    public void onUpdate(){

        if (parent == null)
            return;

        if (
                value instanceof Rational && ((Rational) value).numerator == 1 && ((Rational) value).denominator == 1
                        || value instanceof GroupTerm && ((GroupTerm) value).size() == 0
                ) {
            parent.removeValue(this);
            parent = null;
            return;
        }

        // Si el Factor contiene un GroupTerm con solo un Rational, apuntamos directamente
        // al Rational desde este Factor
        if (
                this.value instanceof GroupTerm
                        && ((GroupTerm) this.value).size() == 1
                        && ((GroupTerm) this.value).get(0).value instanceof Rational
                ) {
            this.value = (Rational) ((GroupTerm) this.value).get(0).value;
        }

        // Si el Factor contiene un GroupTerm con solo un GroupFactor, agregamos el GroupFactor
        // al Parent y removemos este Factor del mismo
        if (
                this.value instanceof GroupTerm
                        && ((GroupTerm) this.value).size() == 1
                        && ((GroupTerm) this.value).get(0).value instanceof GroupFactor
                ) {
            this.parent.addAll(
                    getPositionOnParent(),
                    (GroupFactor) ((GroupTerm) this.value).get(0).value
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
    public FactorValue getValue() {
        return value;
    }

    @Override
    public void setValue(FactorValue value) {
        this.value = value;
        this.value.setParent(this);
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

        if (this.value instanceof GroupTerm) {

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof Rational)
                return operateOnFactor(
                        (GroupTerm) this.value,
                        (Rational) ((Factor) groupable).value
                );

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof GroupTerm)
                return operateOnFactor(
                        (GroupTerm) this.value,
                        (GroupTerm) ((Factor) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof Rational)
                return operateOnTerm(
                        (GroupTerm) this.value,
                        (Rational) ((Term) groupable).value
                );

            if (groupable instanceof Term && ((Term) groupable).value instanceof GroupFactor)
                return operateOnTerm(
                        (GroupTerm) this.value,
                        (GroupFactor) ((Term) groupable).value
                );

        }

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

        if (value instanceof GroupTerm && getPositionOnParent() > 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }

    protected boolean operateOnFactor(Rational factorA, Rational factorB){

        factorA.numerator *= factorB.numerator;
        factorA.denominator *= factorB.denominator;

        factorB.free();

        return true;
    }

    protected boolean operateOnFactor(Rational factorA, GroupTerm factorB){

        for (Term term : factorB){

            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(0, new Factor(factorA.clone()));
                continue;
            }

            if (term.getValue() instanceof Rational) {
                term.setValue(
                        new GroupFactor(
                                new Factor(factorA.clone()),
                                new Factor((Rational) term.getValue())
                        )
                );
            }

        }

        this.setValue(factorB);

        return true;
    }

    protected boolean operateOnFactor(GroupTerm factorA, Rational factorB){

        for (Term term : factorA){

            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(new Factor(factorB.clone()));
                continue;
            }

            if (term.getValue() instanceof Rational) {
                term.setValue(
                        new GroupFactor(
                                new Factor((Rational) term.getValue()),
                                new Factor(factorB.clone())
                        )
                );
            }
        }

        factorB.free();

        return true;
    }

    protected boolean operateOnFactor(GroupTerm factorA, GroupTerm factorB){

        GroupTerm groupTerm = new GroupTerm();

        for (Term termA : factorA) {

            for (Term termB : factorB) {

                GroupFactor groupFactor = new GroupFactor();

                if (termA.value instanceof Rational)
                    groupFactor.add(new Factor((Rational) termA.value));
                else
                    groupFactor.addAll((GroupFactor) termA.value);

                if (termB.value instanceof Rational)
                    groupFactor.add(new Factor((Rational) termB.value));
                else
                    groupFactor.addAll((GroupFactor) termB.value);

                groupTerm.add(
                        new Term(
                                groupFactor
                        )
                );

            }

        }

        this.setValue(groupTerm);
        factorB.getParent().free();

        return true;
    }

    protected boolean operateOnTerm(Rational factorA, Rational termB){
        return false;
    }

    protected boolean operateOnTerm(Rational factorA, GroupFactor termB){
        return false;
    }

    protected boolean operateOnTerm(GroupTerm factorA, Rational termB){
        return false;
    }

    protected boolean operateOnTerm(GroupTerm factorA, GroupFactor termB){
        return false;
    }

}
