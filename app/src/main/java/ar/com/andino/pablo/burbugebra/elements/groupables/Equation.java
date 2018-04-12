package ar.com.andino.pablo.burbugebra.elements.groupables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.os.OperationCanceledException;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.bubbles.IBubble;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class Equation implements TermParent, FactorParent {

    private int xGlobalCenter;
    private int yGlobalCenter;
    private Bitmap bitmap;

    private Paint paint;

    private ArrayList<? extends Operand> leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        ((GroupTerm) leftMember).setParent(this);
        rightMember = new GroupTerm();
        ((GroupTerm) rightMember).setParent(this);
    }

    public ArrayList<? extends Operand> getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(GroupTerm leftMember) {
        this.leftMember = leftMember;
    }

    public ArrayList<? extends Operand> getRightMember() {
        return rightMember;
    }

    public void setRightMember(GroupTerm rightMember) {
        this.rightMember = rightMember;
    }

    public boolean changeMember(Operand operand) {

        if (operand == null || leftMember == null || rightMember == null)
            return false;

        try {

            // Movemos un Term del GroupTerm de la izquierda al GroupTerm de la derecha
            if (operand instanceof Term && leftMember.contains(operand) && rightMember instanceof GroupTerm)
                    ((GroupTerm) rightMember).add((Term) operand.clone().toggleOperation());

            // Movemos un Term del GroupTerm de la derecha al GroupTerm de la izquierda
            else if (operand instanceof Term && rightMember.contains(operand) && leftMember instanceof GroupTerm)
                ((GroupTerm) leftMember).add((Term) operand.clone().toggleOperation());

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

    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(
                bitmap,
                xGlobalCenter - bitmap.getWidth() / 2,
                yGlobalCenter - bitmap.getHeight() / 2,
                null
        );

        for (Operand operand : leftMember)
            operand.onDraw(canvas);

        for (Operand operand : rightMember)
            operand.onDraw(canvas);

    }

    private Paint getPaint(){
        if (paint == null){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(Operand.textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        }

        return paint;
    }

    public void setYGlobalCenter(int yGlobalCenter) {

        this.yGlobalCenter = yGlobalCenter;

        if (leftMember instanceof GroupTerm)
            ((GroupTerm) leftMember).setyGlobalCenter(yGlobalCenter);

        if (leftMember instanceof GroupFactor)
            ((GroupFactor) leftMember).setyGlobalCenter(yGlobalCenter);

        if (rightMember instanceof GroupTerm)
            ((GroupTerm) rightMember).setyGlobalCenter(yGlobalCenter);

        if (rightMember instanceof GroupFactor)
            ((GroupFactor) rightMember).setyGlobalCenter(yGlobalCenter);

    }

    public void setXGlobalCenter(int xGlobalCenter) {

        this.xGlobalCenter = xGlobalCenter;

    }

    public void updateBubble() {

        if (bitmap == null) {
            Rect bound = new Rect();
            getPaint().getTextBounds("=", 0, 1, bound);
            bitmap = Bitmap.createBitmap(bound.width(), bound.height(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.drawText("=", 0, 0, getPaint());

        for (Operand operand : leftMember){
            operand.updateBubble();
        }

        for (Operand operand : rightMember)
            operand.updateBubble();

    }

    public void actionUp(float xCoor, float yCoor){

        for (Operand operand : leftMember)
            if (operand.isTouched(xCoor, yCoor))
                operand.updateTextBitmap();

        for (Operand operand : rightMember)
            if (operand.isTouched(xCoor, yCoor))
                operand.updateTextBitmap();

    }

    public IBubble getPressedBubble(float xCoor, float yCoor) {
        for (Operand operand : leftMember)
            if (operand.isTouched(xCoor, yCoor))
                return operand;

        for (Operand operand : rightMember)
            if (operand.isTouched(xCoor, yCoor))
                return operand;

        return null;
    }

    @Override
    public String toString() {
        return leftMember.toString() + " = " + rightMember.toString();
    }

    // Interface Parent

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
