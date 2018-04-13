package ar.com.andino.pablo.burbugebra.elements.groupables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.os.OperationCanceledException;

import java.util.ArrayList;

import ar.com.andino.pablo.burbugebra.bubbles.IBubble;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class Equation implements TermParent, FactorParent {

    private Typeface typeface;
    private int textSize;
    private Paint paint;

    private int xGlobalCenter;
    private int yGlobalCenter;
    private Bitmap bitmap;

    private ArrayList<? extends Operand> leftMember, rightMember;

    public Equation(){
        leftMember = new GroupTerm();
        ((GroupTerm) leftMember).setParent(this);
        rightMember = new GroupTerm();
        ((GroupTerm) rightMember).setParent(this);
    }

    public Typeface getTypeface() {
        if (typeface == null)
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
        return typeface;
    }

    public int getTextSize() {
        if (textSize == 0)
            textSize = 64;
        return textSize;
    }

    public Paint getPaint() {
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

    public Equation setXGlobalCenter(int xGlobalCenter) {
        this.xGlobalCenter = xGlobalCenter;
        return this;
    }

    public Equation setYGlobalCenter(int yGlobalCenter) {
        this.yGlobalCenter = yGlobalCenter;
        return this;
    }

    public ArrayList<? extends Operand> getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(@NonNull GroupTerm leftMember) {
        this.leftMember = leftMember;
    }

    public ArrayList<? extends Operand> getRightMember() {
        return rightMember;
    }

    public void setRightMember(@NonNull GroupTerm rightMember) {
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

    private Bitmap getBitmap() {

        if (bitmap == null) {

            String text = "=";
            Rect bound = new Rect();

            Paint paint = getPaint();

            paint.getTextBounds(text, 0, text.length(), bound);

            Paint.FontMetrics fontMetrics = paint.getFontMetrics();

            float ascent = paint.ascent();
            float descent = paint.descent();

            int widht = (int) paint.measureText(text);
            int height = (int) -fontMetrics.ascent;

            bitmap = Bitmap.createBitmap(widht, height, Bitmap.Config.ARGB_8888);

            int xPos = bound.width() / 2;
            int yPos = height;

            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawText(
                    text,
                    xPos,
                    yPos,
                    getPaint()
            );
        }
        return bitmap;
    }

    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(
                getBitmap(),
                xGlobalCenter - getBitmap().getWidth() / 2,
                yGlobalCenter - getBitmap().getHeight() / 2,
                null
        );

        for (Operand operand : leftMember)
            operand.onDraw(canvas);

        for (Operand operand : rightMember)
            operand.onDraw(canvas);

    }

    public void updateBubble() {

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
