package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PlayView(this));


    }

    public class PlayView extends View {

        public PlayView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            Path trazo = new Path();
            trazo.addCircle(450, 600, 250, Path.Direction.CW);
            //trazo.moveTo(50, 100);
            //trazo.cubicTo(60,70, 150,90, 200,110);
            //trazo.lineTo(300,200);
            canvas.drawColor(Color.WHITE);
            Paint pincel = new Paint();
            pincel.setColor(Color.BLUE);
            pincel.setStrokeWidth(8);
            pincel.setStyle(Paint.Style.STROKE);
            canvas.drawPath(trazo, pincel);
            pincel.setStrokeWidth(1);
            pincel.setStyle(Paint.Style.FILL_AND_STROKE);
            pincel.setTextSize(60);
            pincel.setTypeface(Typeface.MONOSPACE);
            canvas.drawTextOnPath("Burbuja de Algebra", trazo, 10, 40, pincel);
        }
    }
}
