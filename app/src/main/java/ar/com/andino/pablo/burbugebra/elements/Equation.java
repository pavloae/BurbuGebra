package ar.com.andino.pablo.burbugebra.elements;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.NoGroupable;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class Equation {

    private NoGroupable leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        rightMember = new GroupTerm();
    }

    public NoGroupable getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(NoGroupable leftMember) {
        this.leftMember = leftMember;
    }

    public NoGroupable getRightMember() {
        return rightMember;
    }

    public void setRightMember(NoGroupable rightMember) {
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

}
