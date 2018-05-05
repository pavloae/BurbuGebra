package ar.com.andino.pablo.burbugebra.elements.groupables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.os.OperationCanceledException;
import android.view.MotionEvent;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupOperand;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class Equation implements TermParent, FactorParent, Parent {

    private Operand pressedOperand;
    private boolean isPressed;

    private Typeface typeface;
    private int textSize;
    private Paint paint;

    private int xGlobalCenter;
    private int yGlobalCenter;

    private int left, top;
    private Bitmap bitmap;

    private ArrayList<? extends Operand> leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        ((GroupTerm) leftMember).setParent(this);
        rightMember = new GroupTerm();
        ((GroupTerm) rightMember).setParent(this);
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public Typeface getTypeface() {
        if (typeface == null)
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
        return typeface;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        if (textSize == 0)
            textSize = 64;
        return textSize;
    }

    private Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(getTextSize());
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(getTypeface());
        }
        return paint;
    }

    public void setXGlobalCenter(int xGlobalCenter) {
        this.xGlobalCenter = xGlobalCenter;
    }

    public void setYGlobalCenter(int yGlobalCenter) {
        this.yGlobalCenter = yGlobalCenter;
    }

    public ArrayList<? extends Operand> getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(@NonNull ArrayList<? extends Operand> leftMember) {
        this.leftMember = leftMember;
        ((GroupOperand) this.leftMember).setParent(this);
    }

    public ArrayList<? extends Operand> getRightMember() {
        return rightMember;
    }

    public void setRightMember(@NonNull ArrayList<? extends Operand> rightMember) {
        this.rightMember = rightMember;
        ((GroupOperand) this.rightMember).setParent(this);
    }

    public boolean changeMember(Operand operand) {

        if (operand == null || leftMember == null || rightMember == null)
            return false;

        try {

            // Movemos un Term del GroupTerm de la izquierda al GroupTerm de la derecha
            if (operand instanceof Term && leftMember.contains(operand) && rightMember instanceof GroupTerm){
                Operand operandClone = operand.clone();
                operandClone.toggleOperation();
                ((GroupTerm) rightMember).add((Term) operandClone);
                operandClone.setPressed(false);
            }

            // Movemos un Term del GroupTerm de la derecha al GroupTerm de la izquierda
            else if (operand instanceof Term && rightMember.contains(operand) && leftMember instanceof GroupTerm) {
                Operand operandClone = operand.clone();
                operandClone.toggleOperation();
                ((GroupTerm) leftMember).add((Term) operandClone);
                operandClone.setPressed(false);
            }

            // Movemos un Term del GroupTerm de la izquierda al GroupFactor de la derecha
            else if (operand instanceof Term && leftMember.contains(operand) && rightMember instanceof GroupFactor)
                rightMember = new GroupTerm(
                        new Term((GroupFactor) rightMember),
                        (Term) operand.clone().toggleOperation()
                );

            // Movemos un Term del GroupTerm de la derecha al GroupFactor de la izquierda
            else if (operand instanceof Term && rightMember.contains(operand) && leftMember instanceof GroupFactor)
                leftMember = new GroupTerm(
                        new Term((GroupFactor) leftMember),
                        (Term) operand.clone().toggleOperation()
                );

            // Movemos un Factor del GroupFactor de la izquierda al GroupFactor de la derecha
            else if (operand instanceof Factor && leftMember.contains(operand) && rightMember instanceof GroupFactor)
                ((GroupFactor) rightMember).add((Factor) operand.clone().toggleOperation());

            // Movemos un Factor del GroupFactor de la derecha al GroupFactor de la izquierda
            else if (operand instanceof Factor && rightMember.contains(operand) && leftMember instanceof GroupFactor)
                ((GroupFactor) leftMember).add((Factor) operand.clone().toggleOperation());

            // Movemos un Factor del GroupFactor de la izquierda al GroupTerm de la derecha
            else if (operand instanceof Factor && leftMember.contains(operand) && rightMember instanceof GroupTerm)
                rightMember = new GroupFactor(
                        new Factor((GroupTerm) rightMember),
                        (Factor) operand.clone().toggleOperation()
                );

            // Movemos un Factor del GroupFactor de la derecha al GroupTerm de la izquierda
            else if (operand instanceof Factor && rightMember.contains(operand) && leftMember instanceof GroupTerm)
                leftMember = new GroupFactor(
                        new Factor((GroupTerm) leftMember),
                        (Factor) operand.clone().toggleOperation()
                );

            operand.free();

            onUpdate();

            return true;

        } catch (CloneNotSupportedException | OperationCanceledException e) {
            e.printStackTrace();
        }

        return false;

    }

    public void initBitmap() {

        String text = "=";
        Rect bound = new Rect();

        Paint paint = getPaint();
        paint.getTextBounds(text, 0, text.length(), bound);

        bitmap = Bitmap.createBitmap((int) paint.measureText(text), bound.height(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(
                text,
                bitmap.getWidth() / 2,
                bound.height() - bound.bottom,
                getPaint()
        );

        left = xGlobalCenter - bitmap.getWidth() / 2;
        top = yGlobalCenter - bitmap.getHeight() / 2;

    }

    @WorkerThread
    public void updateParams(){

        if (bitmap == null)
            initBitmap();

        left = xGlobalCenter - bitmap.getWidth() / 2;
        top = yGlobalCenter - bitmap.getHeight() / 2;

        ((GroupOperand) leftMember).updateParams();
        ((GroupOperand) rightMember).updateParams();
    }

    public void onDraw(Canvas canvas) {

        for (Operand operand : leftMember)
            operand.onDraw(canvas);

        for (Operand operand : rightMember)
            operand.onDraw(canvas);

        if (bitmap != null)
            canvas.drawBitmap(bitmap, left, top,null);

    }

    private void group(Operand operand) {

        if (operand == null)
            return;

        if (operand.isContentOn((GroupOperand) leftMember) && operand.getCenterX() > xGlobalCenter)
            changeMember(operand);

        if (operand.isContentOn((GroupOperand) rightMember) && operand.getCenterX() < xGlobalCenter)
            changeMember(operand);


    }

    public void onTouch(MotionEvent motionEvent) {

        motionLabel:
        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (Math.abs(motionEvent.getX() - xGlobalCenter) < getWidth() / 2 && Math.abs(motionEvent.getY() - yGlobalCenter) < getHeight() / 2) {
                    isPressed = true;
                    break;
                }

                for (Operand operand : leftMember){
                    if ((pressedOperand = operand.getBubbleTouched(motionEvent.getX(), motionEvent.getY())) != null){
                        pressedOperand.setPressed(true);
                        break motionLabel;
                    }
                }

                for (Operand operand : rightMember) {
                    if ((pressedOperand = operand.getBubbleTouched(motionEvent.getX(), motionEvent.getY())) != null){
                        pressedOperand.setPressed(true);
                        break motionLabel;
                    }
                }

                break;

            case MotionEvent.ACTION_UP:

                if (pressedOperand != null){
                    group(pressedOperand);
                    pressedOperand.setPressed(false);
                    pressedOperand.updateTextBitmap();
                }

                pressedOperand = null;
                isPressed = false;

                break;

            case MotionEvent.ACTION_MOVE:

                if (pressedOperand != null){
                    pressedOperand.setBubbleCenterX(
                            motionEvent.getX()
                    );
                    pressedOperand.setBubbleCenterY(
                            motionEvent.getY()
                    );
                } else if (isPressed) {
                    setXGlobalCenter((int) (motionEvent.getX()));
                    setYGlobalCenter((int) (motionEvent.getY()));
                }

                break;

        }

    }

    public float getCenterX(GroupOperand member) {

        return (member == leftMember) ?
                xGlobalCenter - (getWidth() / 2 + member.getWidth() / 2):
                xGlobalCenter + (getWidth() / 2 + member.getWidth() / 2);

    }

    @Override
    public float getCenterX() {
        return xGlobalCenter;
    }

    @Override
    public float getCenterY() {
        return yGlobalCenter;
    }

    @Override
    public float getWidth() {
        if (bitmap == null)
            return 0;
        return bitmap.getWidth();
    }

    public float getHeight() {
        if (bitmap == null)
            return 0;
        return bitmap.getHeight();
    }

    @Override
    public String toString() {
        return leftMember.toString() + "=" + rightMember.toString();
    }

    // Interface Parent

    @Override
    public void onUpdate() {
        if (
                leftMember instanceof GroupTerm
                        && leftMember.size() == 1
                        && ((GroupTerm) leftMember).get(0).getValue() instanceof GroupFactor
                )
            leftMember = (GroupFactor) ((GroupTerm) leftMember).get(0).getValue();

        if (
                rightMember instanceof GroupTerm
                        && rightMember.size() == 1
                        && ((GroupTerm) rightMember).get(0).getValue() instanceof GroupFactor
                )
            rightMember = (GroupFactor) ((GroupTerm) rightMember).get(0).getValue();

        if (
                leftMember instanceof GroupFactor
                        && leftMember.size() == 1
                        && ((GroupFactor) leftMember).get(0).getValue() instanceof GroupTerm
                )
            leftMember = (GroupTerm) ((GroupFactor) leftMember).get(0).getValue();

        if (
                rightMember instanceof GroupFactor
                        && rightMember.size() == 1
                        && ((GroupFactor) rightMember).get(0).getValue() instanceof GroupTerm
                )
            rightMember = (GroupTerm) ((GroupFactor) rightMember).get(0).getValue();

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
