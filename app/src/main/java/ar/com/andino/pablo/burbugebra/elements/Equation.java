package ar.com.andino.pablo.burbugebra.elements;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.NoGroupable;

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
}
