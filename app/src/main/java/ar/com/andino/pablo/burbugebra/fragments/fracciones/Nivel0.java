package ar.com.andino.pablo.burbugebra.fragments.fracciones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.com.andino.pablo.burbugebra.EquipoMusica;
import ar.com.andino.pablo.burbugebra.R;
import ar.com.andino.pablo.burbugebra.elementos.BurbujaEntero;
import ar.com.andino.pablo.burbugebra.sprites.SpritesBubble;

public class Nivel0 extends Fragment {

    EquipoMusica equipoMusica;
    Lienzo lienzo;
    private boolean active;

    public Nivel0() {
        super();
    }

    public static Nivel0 newInstance(){
        return new Nivel0();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        equipoMusica = EquipoMusica.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lienzo = new Lienzo(getContext());
        return lienzo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        equipoMusica.cargarMusica(getContext(), R.raw.rebels_be);
        equipoMusica.reproducirMusica();
    }

    @Override
    public void onPause() {
        super.onPause();
        equipoMusica.pausarMusica();
    }

    public void startLogic() {

        active = true;

        new Thread(new Runnable(){

            @Override
            public void run() {

                long timeI = System.currentTimeMillis();
                long timeF;

                while(active){

                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    timeF = System.currentTimeMillis(); // Get current time
                    int delta = (int) (timeF - timeI);
                    lienzo.update(delta);
                    lienzo.postInvalidate();

                    timeI = timeF;
                }

            }}).start();
    }

    public class Lienzo extends View {

        Context context;
        private Bitmap backGround;
        private float t;  // tiempo de la animación en segundos

        // Parámetros de la pantalla
        private int HEIGHT;
        private int WIDHT;

        private float velocidad;        // Velocidad de ascención
        List<BurbujaEntero> bubbleMotionList;

        public Lienzo(Context context) {
            super(context);
            this.context = context;
            backGround = BitmapFactory.decodeResource(getResources(), R.drawable.background_undersea);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            HEIGHT = displayMetrics.heightPixels;
            WIDHT = displayMetrics.widthPixels;
            bubbleMotionList = new ArrayList<>();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            HEIGHT = h;
            WIDHT = w;
            backGround = Bitmap.createScaledBitmap(
                    backGround,
                    (int) (backGround.getWidth() * h / (float) backGround.getHeight()),
                    h,
                    true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(backGround, HEIGHT / 2 - backGround.getWidth() / 2, 0, null);

            drawBubbles(canvas);

        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            performClick();

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

        public void update(int delta){
            for (BurbujaEntero entero : bubbleMotionList)
                entero.t += delta / 1000f;
        }

        private void drawBubbles(Canvas canvas) {

            if (bubbleMotionList == null)
                return;

            for (BurbujaEntero entero : bubbleMotionList) {
                if (entero != null)
                    entero.onDraw(canvas);
            }



        }

        private void touch_start(final float x, final float y) {

            for (BurbujaEntero burbujaEntero : bubbleMotionList)
                burbujaEntero.isPressed = false;

            for (BurbujaEntero entero : bubbleMotionList)
                if (entero != null && entero.onTouchScreen(x, y)){
                    return;
                }

            int entero = (int) (-20 + new Random().nextFloat() * 40);

            bubbleMotionList.add(new BurbujaEntero(context, entero) {


                @Override
                public void setSpritesBubble(@NonNull SpritesBubble bitmap) {

                }

                @Override
                public void setFillingBitmap(@NonNull Bitmap bitmap, boolean scaleToBubble) {

                }

                @Override
                public boolean onTouchScreen(float xCoor, float yCoor) {
                    boolean pressed = super.onTouchScreen(xCoor, yCoor);
                    //if (!pressed)
                    //setBitmapDefault();
                    return pressed;
                }

                @Override
                public void onPlop() {
                    super.onPlop();
                    Nivel0.this.equipoMusica.reproducirEfecto(0);
                }

            });


        }

        private void touch_move(float x, float y) {

            for (BurbujaEntero entero : bubbleMotionList)
                if (entero != null && entero.isPressed){
                    //entero.setCenterX(x);
                    //entero.setCenterY(y);
                    return;
                }

        }

        private void touch_up() {

            for (BurbujaEntero burbujaEntero : bubbleMotionList)
                burbujaEntero.isPressed = false;

        }

    }

}
