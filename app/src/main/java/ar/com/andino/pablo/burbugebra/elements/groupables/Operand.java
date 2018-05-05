package ar.com.andino.pablo.burbugebra.elements.groupables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import ar.com.andino.pablo.burbugebra.bubbles.IBubble;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupOperand;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Value;

public abstract class Operand implements Parent, IBubble, Cloneable {

    public static Typeface typeface;
    public static int textSize = 64;

    private float left, top;
    private Bitmap bitmap;

    private Bitmap bubbleBitmap;
    public float centerX;
    public float centerY;
    private float radius;
    private boolean isPressed;

    public GroupOperand parent;
    public Value value;

    private Paint paint;
    private Rect bound;
    private String text;
    private Bitmap textBitmap;
    public int operation = 1;

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

    public void setParent(GroupOperand parent){
        this.parent = parent;
    }

    public GroupOperand getParentGroup() {
        return parent;
    }

    int getPositionOnParent() {
        if (parent == null)
            return -1;
        return parent.indexOf(this);
    }

    public void setValue(Value value) {
        this.value = value;
        this.value.setParent(this);
    }

    public Value getValue() {
        return value;
    }

    abstract void invert();

    public abstract String getOperator();

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

    private Paint getPaint(){
        if (paint == null){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(getTypeface());
        }
        return paint;
    }

    @WorkerThread
    public void updateParams() {

        centerX = getCenterX();
        centerY = getCenterY();

        updateBitmap();

        left = (int) (centerX - getWidth() / 2);
        top = (int) (centerY - getWidth() / 2);

        if (value instanceof GroupOperand)
            ((GroupOperand) value).updateParams();

    }

    @Override
    public float getCenterX() {

        if (parent == null || isPressed)
            return this.centerX;

        float centerX = parent.getCenterX(this);
        float delta = this.centerX - centerX;

        if (Math.abs(delta) > 1.0f)
            this.centerX -= 0.1f * delta;
        else if (Math.abs(delta) > 0.0f)
            this.centerX = centerX;

        return this.centerX;
    }

    @Override
    public float getCenterY() {

        if (parent == null || isPressed)
            return this.centerY;

        float centerY = parent.getCenterY(this);
        float delta = this.centerY - centerY;

        if (Math.abs(delta) > 1.0f)
            this.centerY -= 0.1f * delta;
        else if (Math.abs(delta) > 0.0f)
            this.centerY = centerY;

        return this.centerY;

    }

    private void updateBitmap(){

        updateTextBitmap();
        updateBubbleBitmap();

        if (textBitmap == null && bubbleBitmap == null)
            bitmap = null;

        else if (textBitmap != null && bubbleBitmap != null) {
            bitmap = Bitmap.createBitmap(
                    bubbleBitmap.getWidth(),
                    bubbleBitmap.getHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(
                    textBitmap,
                    bitmap.getWidth() / 2 - textBitmap.getWidth() / 2,
                    bitmap.getHeight() / 2 - textBitmap.getHeight() / 2,
                    null
            );
            canvas.drawBitmap(bubbleBitmap, 0, 0, null);
        }

        else if (textBitmap != null)
            bitmap = textBitmap;

        else
            bitmap = bubbleBitmap;

    }

    public void updateTextBitmap() {

        if (!(value instanceof Rational)){
            textBitmap = null;
            return;
        }

        if (toString().equals(text))
            return;

        text = toString();

        if (bound == null)
            bound = new Rect();

        getPaint().getTextBounds(text, 0, text.length(), bound);

        textBitmap = Bitmap.createBitmap((int) getPaint().measureText(text), bound.height(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(textBitmap);
        canvas.drawText(
                text,
                textBitmap.getWidth() / 2,
                bound.height()-bound.bottom,
                getPaint()
        );

    }

    Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    public Operand clone() throws CloneNotSupportedException {

        Operand operand = (Operand) super.clone();

        operand.setParent(null);
        operand.value.setParent(operand);

        return operand;

    }

    public float getWidth(){

        if (this.value instanceof GroupOperand)
            return ((GroupOperand) this.value).getWidth();

        if (bitmap != null)
            return bitmap.getWidth();

        return 0;

    }

    public Operand getBubbleTouched(float xCoor, float yCoor) {

        if (value instanceof GroupFactor)
            for (Factor factor : (GroupFactor) value)
                if (factor.getBubbleTouched(xCoor, yCoor) != null)
                    return factor.getBubbleTouched(xCoor, yCoor);

        if (value instanceof GroupTerm)
            for (Term term : (GroupTerm) value)
                if (term.getBubbleTouched(xCoor, yCoor) != null)
                    return term.getBubbleTouched(xCoor, yCoor);

        if (this.isTouched(xCoor, yCoor))
            return this;

        return null;

    }

    public boolean isContentOn(GroupOperand parent) {

        return parent != null && getParentGroup() != null && (getParentGroup() == parent || getParentGroup().isContentOn(parent));

    }

    public static void setTypeface(Typeface typeface) {
        if (typeface == null)
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
        Operand.typeface = typeface;
    }

    public static Typeface getTypeface() {
        if (Operand.typeface == null)
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
        return typeface;
    }

    // Interface IBubble

    @Override
    public Operand setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bubbleBitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * getBubbleRadius()), (int) (2 * getBubbleRadius()), false);
        return this;
    }

    @Override
    public float getBubbleCenterX() {

        if (parent == null || isPressed)
            return this.centerX;

        float centerX = parent.getCenterX(this);
        float delta = this.centerX - centerX;

        if (Math.abs(delta) > 1)
            this.centerX -= 0.1 * delta;
        else if (Math.abs(delta) > 0)
            this.centerX = centerX;

        return this.centerX;
    }

    @Override
    public float getBubbleCenterY() {

        if (parent == null || isPressed)
            return this.centerY;

        float centerY = parent.getCenterY(this);
        float delta = this.centerY - centerY;

        if (Math.abs(delta) > 1)
            this.centerY -= 0.1 * delta;
        else if (Math.abs(delta) > 0)
            this.centerY = centerY;

        return this.centerY;
    }

    @Override
    public float getBubbleRadius() {
        return radius;
    }

    @Override
    public Bitmap getBubbleBitmap() {
        return bubbleBitmap;
    }

    @Override
    public void updateBubbleBitmap(){

        if (textBitmap == null && (value instanceof GroupOperand)) {
            radius = (int) ((GroupOperand) value).getWidth() / 2;
        } else if (textBitmap != null) {
            radius = (int) (Math.sqrt(Math.pow(textBitmap.getWidth(), 2) + Math.pow(textBitmap.getHeight(), 2)) / 2);
        }

        if (bubbleBitmap != null && radius > 0)
            bubbleBitmap = Bitmap.createScaledBitmap(
                    bubbleBitmap,
                    (int) (2 * radius),
                    (int) (2 * radius),
                    false
            );
    }

    @Override
    public void setBubbleCenterX(float centerX) {
        this.centerX = centerX;
    }

    @Override
    public void setBubbleCenterY(float centerY) {
        this.centerY = centerY;
    }

    @Override
    public void setBubbleRadius(float radius) {
    }

    @Override
    public void setBubbleBitmap(Bitmap bubbleBitmap) {
        this.bubbleBitmap = bubbleBitmap.copy(bubbleBitmap.getConfig(), true);
    }

    @Override
    public boolean isTouched(float xCoor, float yCoor) {
        return Math.sqrt(
                Math.pow(Math.abs(xCoor- getCenterX()), 2) +
                        Math.pow(Math.abs(yCoor- getCenterY()), 2)
        ) <= getBubbleRadius();
    }

    @Override
    public boolean isBursted() {
        return false;
    }

    @Override
    public void updateBubbleParams() {

        if (value instanceof GroupTerm)
            for (Term term : (GroupTerm) value)
                term.updateBubbleParams();

        if (value instanceof GroupFactor)
            for (Factor factor : (GroupFactor) value)
                factor.updateBubbleParams();

        updateBitmap();

    }

    @Override
    public void onDraw(Canvas canvas) {

        if (this.value instanceof GroupFactor)
            for (Factor factor : ((GroupFactor) this.value))
                factor.onDraw(canvas);

        else if (this.value instanceof GroupTerm)
            for (Term term : ((GroupTerm) this.value))
                term.onDraw(canvas);

        else if (bitmap != null)
            canvas.drawBitmap(bitmap, left, top, null);

    }

    @Override
    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
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
