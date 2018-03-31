package ar.com.andino.pablo.burbugebra.elements.groupables;

import ar.com.andino.pablo.burbugebra.elements.no_grupables.NoGroupable;

public interface Groupable<P extends NoGroupable, V extends NoGroupable> {

    P getParent();
    void setParent(P parent);
    int getPositionOnParent();

    V getValue();
    void setValue(V value);

}
