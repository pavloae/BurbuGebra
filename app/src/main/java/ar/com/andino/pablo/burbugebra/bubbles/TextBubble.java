package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import ar.com.andino.pablo.burbugebra.elementos.AlgebraElement;

public abstract class TextBubble extends Bubble {

    private static Paint textPaint = new Paint();
    private static Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);

    static {
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(typeface);
    }

    AlgebraElement elementText;

    protected TextBubble(Bitmap bitmap, float centerX, float centerY, float radius) {
        super(bitmap, centerX, centerY, radius);
    }

    public TextBubble setElementText(AlgebraElement elementText){
        this.elementText = elementText;
        return this;
    }

    public void plotStringValue(){

        if (typeface != null)
            textPaint.setTypeface(typeface);

        Rect bound = new Rect();
        textPaint.getTextBounds(elementText.toString(), 0, elementText.toString().length(), bound);
        int width = bound.width();
        int height = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);

        //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(
                elementText.toString(),
                canvas.getWidth()/2,
                canvas.getHeight()/2 - textPaint.ascent() / 2 - textPaint.descent() / 2,
                textPaint
        );
    }

}
