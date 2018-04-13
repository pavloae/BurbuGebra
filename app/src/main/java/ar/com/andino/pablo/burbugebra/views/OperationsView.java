package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.bubbles.IBubble;
import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class OperationsView extends View {

    private Paint paint;

    public static final float T = 2000f;
    public static float radius;
    public static float A;
    public static float k;
    public static float w;

    private static Bitmap backGroundGame;

    public Equation equation;
    public static Bitmap bitmapBubble;

    public OperationsView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public OperationsView(Context context) {
        super(context);
        bitmapBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
        backGroundGame = BitmapFactory.decodeResource(getResources(), R.drawable.under_sea_1);

        paint = new Paint();
        paint.setColor(Color.RED);

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

        equation = new Equation()
                .setXGlobalCenter(getWidth() / 2)
                .setYGlobalCenter(getHeight() / 2);

        Term t12 = new Term(12);
        t12.setBubbleBitmap(bitmapBubble);
        t12.setRadius(30);
        t12.updateBitmap();
        Term t3_5 = new Term(3, 5);
        t3_5.setBubbleBitmap(bitmapBubble);
        Term t2_8 = new Term(2, 8);
        t2_8.setBubbleBitmap(bitmapBubble);


        ((GroupTerm) equation.getLeftMember()).add(t12);

        ((GroupTerm) equation.getRightMember()).add(t3_5);

        ((GroupTerm) equation.getRightMember()).add(t2_8);

    }

    public IBubble getPressedBubble(float xCoor, float yCoor){
        return equation.getPressedBubble(xCoor, yCoor);
    }



}
