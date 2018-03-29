package ar.com.andino.pablo.burbugebra.elementos;

import android.graphics.Bitmap;

public class Operator implements ElementGraphic {

    public static final int TIMES = 0;
    public static final int OBELIS = 1;
    public static final int PLUS = 2;
    public static final int MINUS = 3;

    int id;

    public Operator(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElementType() {
        return AlgebraElement.OPERATOR;
    }

    @Override
    public String toString() {
        switch (id){
            case TIMES:
                return "·";
            case OBELIS:
                return ":";
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            default:
                return "";
        }
    }
}
