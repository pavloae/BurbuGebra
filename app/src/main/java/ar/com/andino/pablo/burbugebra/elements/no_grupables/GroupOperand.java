package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import android.graphics.Path;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;
import ar.com.andino.pablo.burbugebra.elements.groupables.Parent;

public abstract class GroupOperand<T extends Operand> extends ArrayList<T> implements Value {

    public Parent parent;

    public void free(Operand operand) {
        super.remove(operand);
        onUpdate();
    }

    public Parent getParent() {
        return parent;
    }

    public void onUpdate() {
    }

    public int indexOf(Operand operand) {
        return super.indexOf(operand);
    }

    public float getCenterX(Operand operand) {

        if (parent == null || !contains(operand))
            return operand.centerX;

        float centerX;

        if (parent instanceof Equation)
            centerX = ((Equation) parent).getCenterX(this);

        else
            centerX = parent.getCenterX();


        centerX -= getWidth() / 2;
        for (int i = 0 ; i < indexOf(operand) ; i++)
            centerX += get(i).getWidth();
        centerX += operand.getWidth() / 2;

        return centerX;
    }

    public float getCenterY(Operand operand) {
        if (parent == null)
            return operand.centerY;
        return parent.getCenterY();
    }

    public float getWidth() {
        float width = 0;
        for (T operand : this)
            width += operand.getWidth();

        return width;
    }

    public boolean isContentOn(GroupOperand parent) {

        if (parent == null || getParent() == null)
            return false;

        if (getParent() instanceof Equation && this == parent)
            return true;

        if (getParent() instanceof Operand)
            return ((Operand) getParent()).isContentOn(parent);

        return false;

    }

    @WorkerThread
    public void updateParams(){

        for (Operand operand : this)
            operand.updateParams();

    }

    @Override
    public boolean add(T operand) {
        if (super.add(operand))
            operand.setParent(this);
        else
            return false;

        return true;
    }

    @Override
    public void add(int index, T operand) {
        super.add(index, operand);
        operand.setParent(this);
    }

    @Override
    public boolean addAll(Collection<? extends T> groupOperand) {
        for (T operand : groupOperand)
            operand.setParent(this);
        return super.addAll(groupOperand);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> groupOperand) {
        for (T operand : groupOperand)
            operand.setParent(this);
        return super.addAll(index, groupOperand);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (T operand : this)
            stringBuilder.append(operand.toString());

        return stringBuilder.toString();
    }

    @Override
    public GroupOperand clone() {

        GroupOperand groupOperand = (GroupOperand) super.clone();

        groupOperand.setParent(null);

        for (Object term : groupOperand)
            ((Operand) term).setParent(groupOperand);

        return groupOperand;
    }

    // Interface Value

    @Override
    public Value cloneAsValue() {
        return (Value) super.clone();
    }

    @Override
    public void setParent(Parent parent) {
        this.parent = parent;
    }

}
