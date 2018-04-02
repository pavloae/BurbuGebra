package ar.com.andino.pablo.burbugebra.elements;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.NoGroupable;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class Equation {

    private GroupTerm leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        rightMember = new GroupTerm();
    }

    public GroupTerm getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(GroupTerm leftMember) {
        this.leftMember = leftMember;
    }

    public GroupTerm getRightMember() {
        return rightMember;
    }

    public void setRightMember(GroupTerm rightMember) {
        this.rightMember = rightMember;
    }

    public static NoGroupable group(NoGroupable noGroupable1, NoGroupable noGroupable2){
        if (noGroupable1 instanceof Rational && noGroupable2 instanceof Rational)
            return multiplication((Rational) noGroupable1, (Rational) noGroupable2);
        return null;
    }

    private static NoGroupable multiplication(Rational rational1, Rational rational2) {

        return rational1;
    }

    @Override
    public String toString() {
        return leftMember.toString() + " = " + rightMember.toString();
    }
}
