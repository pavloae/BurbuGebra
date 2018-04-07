package ar.com.andino.pablo.burbugebra.elements;

import android.support.v4.os.OperationCanceledException;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupFactorParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.GroupTermParent;
import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class Equation implements GroupTermParent, GroupFactorParent {

    private ArrayList<? extends Groupable> leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        ((GroupTerm) leftMember).setParent(this);
        rightMember = new GroupTerm();
        ((GroupTerm) rightMember).setParent(this);
    }

    public ArrayList<? extends Groupable> getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(GroupTerm leftMember) {
        this.leftMember = leftMember;
    }

    public ArrayList<? extends Groupable> getRightMember() {
        return rightMember;
    }

    public void setRightMember(GroupTerm rightMember) {
        this.rightMember = rightMember;
    }

    public boolean changeMember(Groupable groupable) {

        if (groupable == null || leftMember == null || rightMember == null)
            return false;

        try {

            // Movemos un Term del GroupTerm de la izquierda al GroupTerm de la derecha
            if (groupable instanceof Term && leftMember.contains(groupable) && rightMember instanceof GroupTerm)
                    ((GroupTerm) rightMember).add(((Term) groupable).clone().toggleOperation());

            // Movemos un Term del GroupTerm de la derecha al GroupTerm de la izquierda
            else if (groupable instanceof Term && rightMember.contains(groupable) && leftMember instanceof GroupTerm)
                ((GroupTerm) leftMember).add(((Term) groupable).clone().toggleOperation());

            // Movemos un Term del GroupTerm de la izquierda al GroupFactor de la derecha
            else if (groupable instanceof Term && leftMember.contains(groupable) && rightMember instanceof GroupFactor)
                rightMember = new GroupTerm(
                        new Term((GroupFactor) rightMember),
                        ((Term) groupable).clone().toggleOperation()
                );

            // Movemos un Term del GroupTerm de la derecha al GroupFactor de la izquierda
            else if (groupable instanceof Term && rightMember.contains(groupable) && leftMember instanceof GroupFactor)
                leftMember = new GroupTerm(
                        new Term((GroupFactor) leftMember),
                        ((Term) groupable).clone().toggleOperation()
                );

            // Movemos un Factor del GroupFactor de la izquierda al GroupFactor de la derecha
            else if (groupable instanceof Factor && leftMember.contains(groupable) && rightMember instanceof GroupFactor)
                ((GroupFactor) rightMember).add(((Factor) groupable).clone().toggleOperation());

            // Movemos un Factor del GroupFactor de la derecha al GroupFactor de la izquierda
            else if (groupable instanceof Factor && rightMember.contains(groupable) && leftMember instanceof GroupFactor)
                ((GroupFactor) leftMember).add(((Factor) groupable).clone().toggleOperation());

            // Movemos un Factor del GroupFactor de la izquierda al GroupTerm de la derecha
            else if (groupable instanceof Factor && leftMember.contains(groupable) && rightMember instanceof GroupTerm)
                rightMember = new GroupFactor(
                        new Factor((GroupTerm) rightMember),
                        ((Factor) groupable).clone().toggleOperation()
                );

            // Movemos un Factor del GroupFactor de la derecha al GroupTerm de la izquierda
            else if (groupable instanceof Factor && rightMember.contains(groupable) && leftMember instanceof GroupTerm)
                leftMember = new GroupFactor(
                        new Factor((GroupTerm) leftMember),
                        ((Factor) groupable).clone().toggleOperation()
                );

            groupable.free();

            onUpdate();

            return true;

        } catch (CloneNotSupportedException | OperationCanceledException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String toString() {
        return leftMember.toString() + " = " + rightMember.toString();
    }

    // Interface GroupParent

    @Override
    public void onUpdate() {

        if (leftMember instanceof GroupTerm && leftMember.size() == 0)
            ((GroupTerm) leftMember).add(new Term());
        if (leftMember instanceof GroupFactor && leftMember.size() == 0)
            ((GroupFactor) leftMember).add(new Factor());
        else if (
                leftMember instanceof GroupTerm
                        && leftMember.size() == 1
                        && ((GroupTerm) leftMember).get(0).getValue() instanceof GroupFactor
                )
            leftMember = (GroupFactor) ((GroupTerm) leftMember).get(0).getValue();
        else if (
                leftMember instanceof GroupFactor
                        && leftMember.size() == 1
                        && ((GroupFactor) leftMember).get(0).getValue() instanceof GroupTerm
                )
            leftMember = (GroupTerm) ((GroupFactor) leftMember).get(0).getValue();

        if (rightMember instanceof GroupTerm && rightMember.size() == 0)
            ((GroupTerm) rightMember).add(new Term());
        if (rightMember instanceof GroupFactor && rightMember.size() == 0)
            ((GroupFactor) rightMember).add(new Factor());
        else if (
                rightMember instanceof GroupTerm
                        && rightMember.size() == 1
                        && ((GroupTerm) rightMember).get(0).getValue() instanceof GroupFactor
                )
            rightMember = (GroupFactor) ((GroupTerm) rightMember).get(0).getValue();
        else if (
                rightMember instanceof GroupFactor
                        && rightMember.size() == 1
                        && ((GroupFactor) rightMember).get(0).getValue() instanceof GroupTerm
                )
            rightMember = (GroupTerm) ((GroupFactor) rightMember).get(0).getValue();

    }

    @Override
    public void free() {
        onUpdate();
    }
}
