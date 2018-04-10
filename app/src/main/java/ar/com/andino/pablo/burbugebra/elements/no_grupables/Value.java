package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;

public interface Value extends Cloneable {
    Object clone() throws CloneNotSupportedException;
    void setParent(Operand parent);
}
