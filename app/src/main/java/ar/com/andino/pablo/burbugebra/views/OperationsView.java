package ar.com.andino.pablo.burbugebra.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.bubbles.EquationBubble;
import ar.com.andino.pablo.burbugebra.bubbles.InterfazBurbuja;
import ar.com.andino.pablo.burbugebra.bubbles.TermBubble;
import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;

public class OperationsView extends View {

    public static final float T = 2000f;
    public static float radius;
    public static float A;
    public static float k;
    public static float w;

    private static Bitmap backGroundGame;

    public InterfazBurbuja equation;
    public static Bitmap bitmapBubble;

    public OperationsView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public OperationsView(Context context) {
        super(context);
        bitmapBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_blue);
        backGroundGame = BitmapFactory.decodeResource(getResources(), R.drawable.under_sea_1);
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
        if (equation != null)
            equation.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void initBubbles() {

        equation = new EquationBubble(bitmapBubble, getWidth() / 2, getHeight() / 2, getWidth() / 10);
        ((GroupTerm)((Equation) equation).getLeftMember()).add(new TermBubble());
        ((GroupTerm)((Equation) equation).getRightMember()).add(new TermBubble());

    }

}
