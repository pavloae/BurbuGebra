package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.Locale;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.FactorValue;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.TermValue;

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
                return rationalOnFactorRational((Rational) ((Factor) groupable).value);

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof GroupTerm)
                return rationalOnFactorGroupTerm((GroupTerm) ((Factor) groupable).value);

            if (groupable instanceof Term && ((Term) groupable).value instanceof Rational)
                return rationalOnTermRational((Rational) ((Term) groupable).value);

            if (groupable instanceof Term && ((Term) groupable).value instanceof GroupFactor)
                return rationalOnTermGroupFactor((GroupFactor) ((Term) groupable).value);

        }

        if (this.value instanceof GroupTerm) {

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof Rational)
                return groupTermOnFactorRational((Rational) ((Factor) groupable).value);

            if (groupable instanceof Factor && ((Factor) groupable).value instanceof GroupTerm)
                return groupTermOnFactorGroupTerm((GroupTerm) ((Factor) groupable).value);

            if (groupable instanceof Term && ((Term) groupable).value instanceof Rational)
                return groupTermOnTermRational((Rational) ((Term) groupable).value);

            if (groupable instanceof Term && ((Term) groupable).value instanceof GroupFactor)
                return groupTermOnTermGroupFactor((GroupFactor) ((Term) groupable).value);

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

        if (value instanceof GroupTerm && parent.size() > 0)
            stringBuilder.append("(%s)");
        else
            stringBuilder.append("%s");

        return String.format(Locale.ENGLISH, stringBuilder.toString(), value.toString());
    }

    protected boolean rationalOnFactorRational(Rational value){

        ((Rational) this.value).numerator *= value.numerator;
        ((Rational) this.value).denominator *= value.denominator;

        value.free();

        return true;
    }

    protected boolean rationalOnFactorGroupTerm(GroupTerm value){

        Rational rational = ((Rational) this.value).clone();
        setValue(value);

        for (Term term : (GroupTerm) this.value){
            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(0, new Factor(rational));
                continue;
            }

            if (term.getValue() instanceof Rational) {
                term.setValue(new GroupFactor(
                        new Factor(rational),
                        new Factor((Rational) term.getValue())
                ));
            }
        }

        return true;
    }

    protected boolean groupTermOnFactorRational(Rational value){

        for (Term term : (GroupTerm) this.value){
            if (term.getValue() instanceof GroupFactor){
                ((GroupFactor) term.getValue()).add(new Factor(value.clone()));
                continue;
            }

            if (term.getValue() instanceof Rational) {
                term.setValue(new GroupFactor(
                        new Factor((Rational) term.getValue()),
                        new Factor(value.clone())
                ));
            }
        }
        value.free();

        return true;
    }

    protected boolean groupTermOnFactorGroupTerm(GroupTerm value){

        GroupTerm groupTerm = new GroupTerm();

        for (Term termA : (GroupTerm) this.value) {

            for (Term termB : value) {

                if (termA.value instanceof Rational && termB.value instanceof Rational)
                    groupTerm.add(
                            new Term(
                                    new GroupFactor(
                                            new Factor((Rational) termA.value),
                                            new Factor((Rational) termB.value)
                                    )
                            )

                    );
                else if (termA.value instanceof Rational && termB.value instanceof GroupFactor)
                    groupTerm.add(
                            new Term(
                                    // TODO: Completar bien este método
                            )
                    );

                groupTerm.add(
                        new Term(
                                new GroupFactor(

                                )
                        )
                );

            }

        }

        return false;
    }

    protected boolean rationalOnTermRational(Rational value){
        return false;
    }

    protected boolean rationalOnTermGroupFactor(GroupFactor value){
        return false;
    }

    protected boolean groupTermOnTermRational(Rational value){
        return false;
    }

    protected boolean groupTermOnTermGroupFactor(GroupFactor value){
        return false;
    }

}
