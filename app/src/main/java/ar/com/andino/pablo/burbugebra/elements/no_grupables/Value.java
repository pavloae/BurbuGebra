package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import ar.com.andino.pablo.burbugebra.elements.groupables.Parent;

public interface Value extends Cloneable {
    Value cloneAsValue() throws CloneNotSupportedException;
    void setParent(Parent parent);
}
