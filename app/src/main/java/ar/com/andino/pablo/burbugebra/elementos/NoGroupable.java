package ar.com.andino.pablo.burbugebra.elementos;

interface NoGroupable extends AlgebraElement {
    Groupable getParent();
    void setParent(Groupable parent);
}