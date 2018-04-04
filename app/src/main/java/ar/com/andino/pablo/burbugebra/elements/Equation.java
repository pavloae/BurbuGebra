package ar.com.andino.pablo.burbugebra.elements;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

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

    @Override
    public String toString() {
        return leftMember.toString() + " = " + rightMember.toString();
    }

}
