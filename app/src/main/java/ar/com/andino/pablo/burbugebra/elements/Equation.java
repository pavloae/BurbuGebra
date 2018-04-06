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
        rightMember = new GroupTerm();
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

    public boolean switchGroupable(Groupable groupable) {

        if (groupable == null || leftMember == null || rightMember == null)
            return false;

        try {

            // Movemos un Term del GroupTerm de la izquierda al GroupTerm de la derecha
            if (groupable instanceof Term && leftMember.contains(groupable) && rightMember instanceof GroupTerm)
                    ((GroupTerm) rightMember).add(((Term) groupable).clone().switchOperation());

            // Movemos un Term del GroupTerm de la derecha al GroupTerm de la izquierda
            else if (groupable instanceof Term && rightMember.contains(groupable) && leftMember instanceof GroupTerm)
                ((GroupTerm) leftMember).add(((Term) groupable).clone().switchOperation());

            // Movemos un Term del GroupTerm de la izquierda al GroupFactor de la derecha
            else if (groupable instanceof Term && leftMember.contains(groupable) && rightMember instanceof GroupFactor)
                rightMember = new GroupTerm(
                        new Term((GroupFactor) rightMember),
                        ((Term) groupable).clone().switchOperation()
                );

            // Movemos un Term del GroupTerm de la derecha al GroupFactor de la izquierda
            else if (groupable instanceof Term && rightMember.contains(groupable) && leftMember instanceof GroupFactor)
                leftMember = new GroupTerm(
                        new Term((GroupFactor) leftMember),
                        ((Term) groupable).clone().switchOperation()
                );

            // Movemos un Factor del GroupFactor de la izquierda al GroupFactor de la derecha
            else if (groupable instanceof Factor && leftMember.contains(groupable) && rightMember instanceof GroupFactor)
                ((GroupFactor) rightMember).add(((Factor) groupable).clone().switchOperation());

            // Movemos un Factor del GroupFactor de la derecha al GroupFactor de la izquierda
            else if (groupable instanceof Factor && rightMember.contains(groupable) && leftMember instanceof GroupFactor)
                ((GroupFactor) leftMember).add(((Factor) groupable).clone().switchOperation());

            // Movemos un Factor del GroupFactor de la izquierda al GroupTerm de la derecha
            else if (groupable instanceof Factor && leftMember.contains(groupable) && rightMember instanceof GroupTerm)
                rightMember = new GroupFactor(
                        new Factor((GroupTerm) rightMember),
                        ((Factor) groupable).clone().switchOperation()
                );

            // Movemos un Factor del GroupFactor de la derecha al GroupTerm de la izquierda
            else if (groupable instanceof Factor && rightMember.contains(groupable) && leftMember instanceof GroupTerm)
                leftMember = new GroupFactor(
                        new Factor((GroupTerm) leftMember),
                        ((Factor) groupable).clone().switchOperation()
                );



            groupable.free();

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

    @Override
    public void onUpdate() {

    }

    @Override
    public void free() {

    }
}
