package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

public class MemoryBubble extends Bubble {

    private static Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);

    public static Bitmap hiddenBitmap;
    public static MemoryBubble burbuja1, burbuja2;
    private int number;

    protected MemoryBubble(Bitmap bitmap, float centerX, float centerY, float radius) {
        super(bitmap, centerX, centerY, radius);
    }

    public MemoryBubble setNumber(int number) {
        this.number = number;
        return this;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public InterfazBurbuja setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius) {
        return null;
    }

    @Override
    public Bitmap getBitmap() {
        if (this.equals(MemoryBubble.burbuja1) || this.equals(MemoryBubble.burbuja2)) {
            return super.getBitmap();
        }
        return Bitmap.createScaledBitmap(MemoryBubble.hiddenBitmap, (int) (2 * getRadius()), (int) (2 * getRadius()), false);
    }

    public static void setTypeface(Typeface typeface) {
        if (typeface != null)
            MemoryBubble.typeface = typeface;
    }

}
