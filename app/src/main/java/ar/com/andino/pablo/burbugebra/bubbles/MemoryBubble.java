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
    public IBubble setBubble(@NonNull Bitmap bitmap, float centerX, float centerY, float radius) {
        return null;
    }

    @Override
    public Bitmap getBubbleBitmap() {
        if (this.equals(MemoryBubble.burbuja1) || this.equals(MemoryBubble.burbuja2)) {
            return super.getBubbleBitmap();
        }
        return Bitmap.createScaledBitmap(MemoryBubble.hiddenBitmap, (int) (2 * getBubbleRadius()), (int) (2 * getBubbleRadius()), false);
    }

    @Override
    public void updateBubbleBitmap() {

    }

    public static void setTypeface(Typeface typeface) {
        if (typeface != null)
            MemoryBubble.typeface = typeface;
    }

}
