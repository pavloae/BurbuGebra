package ar.com.andino.pablo.burbugebra.elements.groupables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.bubbles.InterfazBurbuja;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupOperand;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Value;

public abstract class Operand implements InterfazBurbuja, Cloneable {

    private int left, top;
    protected Bitmap bitmap;
    private float centerX;
    private float centerY;
    private float radius;
    public boolean isBursted;
    protected boolean isPressed;

    public GroupOperand parent;
    public Value value;
    public int operation = 1;

    Operand(){
    }

    Operand(Rational rational){
        this.value = rational;
        this.value.setParent(this);
    }

    Operand(int numerator){
        this.value = new Rational(numerator);
        this.value.setParent(this);
    }

    Operand(int numerator, int denominator) {
        this.value = new Rational(numerator, denominator);
        this.value.setParent(this);
    }

    Operand(String name){
        this.value = new Rational(name);
        this.value.setParent(this);
    }

    Operand(Value value){
        this.value = value;
        this.value.setParent(this);
    }

    public Operand setParent(GroupOperand parent){
        this.parent = parent;
        return this;
    }

    public GroupOperand getParent() {
        return parent;
    }

    int getPositionOnParent() {
        if (parent == null)
            return -1;
        return parent.indexOf(this);
    }

    public Operand setValue(Value value) {
        this.value = value;
        this.value.setParent(this);
        return this;
    }

    public Value getValue() {
        return value;
    }

    abstract void invert();

    public boolean group(Operand operand) {

        try {
            if (operand instanceof Factor)
                return group((Factor) operand);

            return operand instanceof Term && group((Term) operand);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return false;

    }

    protected abstract boolean group(Factor factor) throws CloneNotSupportedException;

    protected abstract boolean group(Term term) throws CloneNotSupportedException;

    public Operand toggleOperation() {
        operation *= -1;
        return this;
    }

    public void free(){
        if (parent != null)
            parent.free(this);
        this.parent = null;
        this.value = null;
    }

    @Override
    public Operand clone() throws CloneNotSupportedException {

        Operand operand = (Operand) super.clone();

        operand.setParent(null);
        operand.value.setParent(operand);

        return operand;

    }

    // Interface InterfazBurbuja

    @Override
    public Operand setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
        return this;
    }

    @Override
    public float getCenterX() {
        return centerX;
    }

    @Override
    public float getCenterY() {
        return centerY;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    @Override
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
        if (radius <= 0) {
            this.bitmap = null;
            return;
        }

        if (getBitmap() == null)
            return;

        bitmap = Bitmap.createScaledBitmap(getBitmap(), 2 * (int) radius, 2 * (int) radius, true);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    @Override
    public boolean isTouched(float xCoor, float yCoor) {
        return Math.sqrt(
                Math.pow(Math.abs(xCoor-getCenterX()), 2) +
                        Math.pow(Math.abs(yCoor-getCenterY()), 2)
        ) <= getRadius();
    }

    @Override
    public boolean isBursted() {
        return isBursted;
    }

    @Override
    public void updateBubble() {

        left = (int) (getCenterX() - getRadius());
        top = (int) (getCenterY() - getRadius());
        bitmap = getBitmap();

        if (value instanceof GroupTerm)
            for (Term term : (GroupTerm) value)
                term.updateBubble();

        if (value instanceof GroupFactor)
            for (Factor factor : (GroupFactor) value)
                factor.updateBubble();

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getBitmap() != null && !isBursted)
            canvas.drawBitmap(bitmap, left, top, null);
    }

    @Override
    public void onPressed() {

    }

    @Override
    public void onPlop() {

    }

    @Override
    public void setFillingBitmap(Bitmap bitmap, boolean scaleToBubble) {

    }
}
