package ar.com.andino.pablo.burbugebra.elementos;

public class Equation{

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
}
