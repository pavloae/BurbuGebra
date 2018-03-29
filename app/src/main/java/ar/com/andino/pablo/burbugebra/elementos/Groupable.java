package ar.com.andino.pablo.burbugebra.elementos;

interface Groupable<P extends NoGroupable, V extends NoGroupable> extends AlgebraElement {

    P getParent();
    void setParent(P parent);
    int getPositionOnParent();

    V getValue();
    void setValue(V value);

}
