package ar.com.andino.pablo.burbugebra.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class Utils {

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {

        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;

    }

    public static void setTextToBitmap(String text, int textSize, int positionX, int positionY, Bitmap bitmap, @Nullable Typeface typeface) {

        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        typeface = (typeface == null) ? Typeface.create(Typeface.MONOSPACE, Typeface.BOLD) : typeface;
        textPaint.setTypeface(typeface);

        if (typeface != null)
            textPaint.setTypeface(typeface);

        Rect bound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bound);
        int width = bound.width();
        int height = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);

        //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(
                text,
                positionX,
                positionY,
                textPaint
        );

    }

    public static int[] createRandomNumbers(int cant, int limitInf, int limitSup, boolean canRepeat) {

        if (!canRepeat && cant > limitSup - limitInf)
            throw new IllegalArgumentException("No se pueden generar numeros aleatorios sin repeticiones");

        int[] randomNumbers = new int[cant];
        int newRandom;

        for (int globalPosition = 0; globalPosition < cant; globalPosition++) {
            newRandom = limitInf + (int) (Math.random() * (limitSup - limitInf));
            if (!canRepeat){
                int localPosition = 0;
                do {
                    if (newRandom == randomNumbers[localPosition]){
                        newRandom = (newRandom == limitSup) ? limitInf : newRandom + 1;
                        localPosition = 0;
                        continue;
                    }
                    localPosition++;
                } while (localPosition < globalPosition);
            }
            randomNumbers[globalPosition] = newRandom;
        }

        return randomNumbers;

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);


        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
