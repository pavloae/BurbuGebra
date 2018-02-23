package ar.com.andino.pablo.burbugebra;

import org.junit.Test;

import java.util.HashMap;

import ar.com.andino.pablo.burbugebra.numeros.Entero;
import ar.com.andino.pablo.burbugebra.numeros.Fraccion;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dividirEntero() throws Exception {

        Entero entero = new Entero(360);

        assertTrue(entero.dividirPor(9) == 0);
        assertTrue(entero.obtenerValor() == 40);

        assertTrue(entero.dividirPor(9) == 4);
        assertTrue(entero.obtenerValor() == 4);

        assertTrue(entero.dividirPor(10) == 4);
        assertTrue(entero.obtenerValor() == 0);

    }

    @Test
    public void fraccion() throws Exception {

        Fraccion fraccion = new Fraccion(360, 8448);

        assertTrue(fraccion.numerador() == 360);
        assertTrue(fraccion.denominador() == 8448);

        assertTrue(Entero.obtenerMCD(360, 8448) == 24);

    }

    @Test
    public void obtenerMCM() throws Exception {
        assertTrue(Entero.obtenerMCM(6, 4) == 12);
        assertTrue(Entero.obtenerMCM(8, 24) == 24);
    }

    @Test
    public void obtenerMCD() throws Exception {
        assertTrue(Entero.obtenerMCD(6, 4 ) == 2);
    }

    @Test
    public void enteroFactores() throws Exception {

        Entero entero = new Entero(360);
        HashMap<Integer, Integer> factores = Entero.factoresPrimos(360);

        for (int factor : factores.keySet()){
            System.out.println(factor +"^"+ factores.get(factor));
        }

        entero = new Entero(8448);
        factores = Entero.factoresPrimos(8448);

        for (int factor : factores.keySet()){
            System.out.println(factor +"^"+ factores.get(factor));
        }

        System.out.println("MCD = " + Entero.obtenerMCD(360, 8448));

    }


}