package ar.com.andino.pablo.burbugebra.elements.no_grupables;

import ar.com.andino.pablo.burbugebra.elements.groupables.Operand;

public interface GroupOperand {

    void free(Operand element);
    int indexOf(Operand operand);

}
