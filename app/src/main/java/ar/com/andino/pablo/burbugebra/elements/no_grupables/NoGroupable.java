package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import ar.com.andino.pablo.burbugebra.elements.groupables.Groupable;

public interface NoGroupable {

    Groupable getParent();
    void removeValue(Groupable value);

    Groupable group(Groupable groupable);

}
