package ar.com.andino.pablo.burbugebra.elementos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;

public class Ecuacion {

    Miembro miembroIzquierdo, miembroDerecho;
    int selected;

    public Ecuacion() {
        miembroIzquierdo = Miembro.neutro();
        miembroDerecho = Miembro.neutro();
    }

    public void addChar(Character character){
        if (selected == 0){
            miembroIzquierdo.addChar(character);
        } else {
            miembroDerecho.addChar(character);
        }
    }

    public void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        int anchoMiembroI = miembroIzquierdo.obtenerAncho();
        int anchoMiembroD = miembroDerecho.obtenerAncho();

        int centerMiembroI = centerX - anchoMiembroI / 2;
        int centerMiembroD = centerX + anchoMiembroD / 2;

        Path trazoI = new Path();
        trazoI.addCircle(centerMiembroI, centerY, anchoMiembroI / 2, Path.Direction.CW);

        Path trazoD = new Path();
        trazoD.addCircle(centerMiembroD, centerY, anchoMiembroD / 2, Path.Direction.CW);

        Paint pincel = new Paint();
        pincel.setColor(Color.BLUE);
        pincel.setStrokeWidth(8);
        pincel.setStyle(Paint.Style.STROKE);

        canvas.drawPath(trazoI, pincel);
        pincel.setColor(Color.GREEN);
        canvas.drawPath(trazoD, pincel);

        pincel.setStrokeWidth(1);
        pincel.setStyle(Paint.Style.FILL_AND_STROKE);
        pincel.setTextSize(60);
        pincel.setTypeface(Typeface.MONOSPACE);

        //canvas.drawTextOnPath("Bubble de Algebra", trazoI, 10, 40, pincel);


    }


}
