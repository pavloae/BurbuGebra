package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.bubbles.IBubble;
import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class OperationsView extends View {

    public Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
    public Paint paint;

    public static final float T = 2000f;
    public static float radius;
    public static float A;
    public static float k;
    public static float w;

    private static Bitmap backGroundGame;

    public Equation equation;
    public static Bitmap blueBubble;
    public static Bitmap greenBubble;

    public OperationsView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public OperationsView(Context context) {
        super(context);
        blueBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
        greenBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_green);
        backGroundGame = BitmapFactory.decodeResource(getResources(), R.drawable.under_sea_1);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        backGroundGame = Bitmap.createScaledBitmap(backGroundGame, w, h, false);
        radius = 0.10f * getWidth();
        A = radius / 10f;
        k = (float) (2 * Math.PI / getWidth());
        OperationsView.w = (float) (2.0 * Math.PI / T);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backGroundGame, 0, 0, null);

        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);

        if (equation != null)
            equation.onDraw(canvas);

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void initBubbles() {

        initEquation();

        equation.setXGlobalCenter(getWidth() / 2);
        equation.setYGlobalCenter(getHeight() / 2);

        for (Operand operand : equation.getLeftMember())
            operand.setBubbleBitmap(blueBubble);

        for (Operand operand : equation.getRightMember())
            operand.setBubbleBitmap(blueBubble);

    }

    private void initEquation(){

        // (-2)·(23+8·(-3))+5/2·8/3 = 5/2·X+8/3:3/5+2/3·X

        equation = new Equation();

        equation.setLeftMember(
                new GroupTerm(
                        new Term(
                                new GroupFactor(
                                        new Factor(-2),
                                        new Factor(
                                                new GroupTerm(
                                                        new Term(23),
                                                        new Term(
                                                                new GroupFactor(
                                                                        new Factor(8),
                                                                        new Factor(-3)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ),
                        new Term(
                                new GroupFactor(
                                        new Factor(5,2),
                                        new Factor(8, 3)
                                )
                        )
                )
        );

        equation.setRightMember(
                new GroupTerm(
                        new Term(
                                new GroupFactor(
                                        new Factor(5,2),
                                        new Factor("X")
                                )
                        ),
                        new Term(
                                new GroupFactor(
                                        new Factor(8, 3),
                                        (Factor) new Factor(3, 5).toggleOperation()
                                )
                        ),
                        new Term(
                                new GroupFactor(
                                        new Factor(2, 3),
                                        new Factor("X")
                                )
                        )
                )
        );

        Log.d("initEquation", equation.toString());

    }

}
