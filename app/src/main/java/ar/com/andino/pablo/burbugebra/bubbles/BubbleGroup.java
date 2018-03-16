package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class BubbleGroup<T extends Bubble> {

    private List<T> burbujas = new ArrayList<>();
    private List<T> explotadas = new ArrayList<>();
    private int position;

    public BubbleGroup() {
    }

    synchronized public void addBubble(T burbuja) {
        burbujas.add(burbuja);
    }

    synchronized public T getBubble(int position) {
        if (position >= 0 && position < burbujas.size())
            return burbujas.get(position);
        return null;
    }

    synchronized public void clear() {
        burbujas.clear();
    }

    synchronized public void removeBursted() {
        explotadas.clear();
        for (position = 0; position < burbujas.size(); position++)
            if (burbujas.get(position).isBursted)
                explotadas.add(burbujas.get(position));
        burbujas.removeAll(explotadas);
    }

    synchronized public void draw(Canvas canvas) {
        if (burbujas != null && burbujas.size() > 0)
            for (position = 0; position < burbujas.size(); position++)
                burbujas.get(position).onDraw(canvas);
    }

    synchronized public void update() {
        if (burbujas != null && burbujas.size() > 0)
            for (position = 0; position < burbujas.size(); position++)
                burbujas.get(position).update();
    }

    synchronized public void onTouchScreen(float x, float y) {
        if (burbujas != null && burbujas.size() > 0)
            for (position = 0; position < burbujas.size(); position++)
                burbujas.get(position).isTouched(x, y);
    }

    synchronized public boolean isEmpty() {
        return (burbujas.size() == 0);
    }

    synchronized public int size(){
        return burbujas.size();
    }

}
