package ar.com.andino.pablo.burbugebra.Elementos;

public class Ecuacion {

    Miembro miembroIzquierdo, miembroDerecho;
    int selected;

    public Ecuacion() {
        miembroIzquierdo = Miembro.neutro();
        miembroDerecho = Miembro.neutro();
    }

    public void addChar(Character character){
        if (selected == 0){
            miembroIzquierdo.addChar(character);
        } else {
            miembroDerecho.addChar(character);
        }
    }


}
