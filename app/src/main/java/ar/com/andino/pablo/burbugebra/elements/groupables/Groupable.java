package ar.com.andino.pablo.burbugebra.elements.groupables;

import java.util.ArrayList;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.NoGroupable;

public interface Groupable<P extends ArrayList<? extends Groupable>, V extends NoGroupable> {

    void setValue(V value);
    V getValue();

    void setParent(P parent);
    P getParent();

    int getPositionOnParent();

    boolean group(Term factor);
    boolean group(Factor factor);

    void free();

}
