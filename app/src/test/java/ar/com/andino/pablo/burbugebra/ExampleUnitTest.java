package ar.com.andino.pablo.burbugebra;

import android.os.CountDownTimer;
import android.util.Log;

import org.junit.Test;

import java.util.HashMap;

import ar.com.andino.pablo.burbugebra.numeros.Entero;
import ar.com.andino.pablo.burbugebra.numeros.Fraccion;
import ar.com.andino.pablo.burbugebra.utils.Utils;

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
    public void createRandomNumbers() throws Exception {

        int cant = 10;
        int[] randomNumbers = new int[cant];
        int limitSup = 5;
        int limitInf = -5;

        assert cant < limitSup - limitInf;

        int newRandom;
        boolean original;
        for (int totalPosition = 0; totalPosition < cant; totalPosition++) {
            newRandom = limitInf + (int) (Math.random() * (limitSup -limitInf));

            do {
                original = true;
                //section:
                for (int partialPosition = 0; partialPosition < totalPosition; partialPosition++) {
                    System.out.print(newRandom + ", ");
                    if (newRandom == randomNumbers[partialPosition]){
                        newRandom = (newRandom == limitSup) ? limitInf : newRandom + 1;
                        original = false;
                        //break section;
                    }
                }
            } while (!original);

            System.out.println();
            System.out.println(newRandom);
            randomNumbers[totalPosition] = newRandom;
        }

        for (int random : randomNumbers)
            System.out.print(random + ", ");
        System.out.println();

    }

    @Test
    public void dividirEntero() throws Exception {

        Entero entero = new Entero(2) {
            @Override
            public int getValor() {
                return super.getValor();
            }
        };

        assertTrue(entero.dividirPor(9) == 0);
        assertTrue(entero.getValor() == 40);

        assertTrue(entero.dividirPor(9) == 4);
        assertTrue(entero.getValor() == 4);

        assertTrue(entero.dividirPor(10) == 4);
        assertTrue(entero.getValor() == 0);

    }

    @Test
    public void whileTest() throws Exception {

        boolean condicion = false;
        int count = 0;
        do {

            System.out.println("Primer entrada:  " + count + " - condition: " + condicion);

            condicion = count < 5;

            System.out.println("Segunda entrada: " + count + " - condition: " + condicion);

            count++;

        } while (condicion);
    }

    @Test
    public void randomTest() throws Exception {

        int[] randomNumbers;

        randomNumbers = Utils.createRandomNumbers(10, -5, 5, false);
        for (int position = 0; position < randomNumbers.length; position++)
            System.out.print(randomNumbers[position] + ", ");
        System.out.println();
        randomNumbers = Utils.createRandomNumbers(10, -5, 5, true);
        for (int position = 0; position < randomNumbers.length; position++)
            System.out.print(randomNumbers[position] + ", ");
        System.out.println();

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

        Entero entero = new Entero(360){
        };
        HashMap<Integer, Integer> factores = Entero.factoresPrimos(360);

        for (int factor : factores.keySet()){
            System.out.println(factor +"^"+ factores.get(factor));
        }

        entero = new Entero(8448){

        };
        factores = Entero.factoresPrimos(8448);

        for (int factor : factores.keySet()){
            System.out.println(factor +"^"+ factores.get(factor));
        }

        System.out.println("MCD = " + Entero.obtenerMCD(360, 8448));

    }


    @Test
    public void countDown() throws Exception {
        Contador counter = new Contador(10000,1000);
        counter.start();
    }


    public void fin(){
        System.out.println("FIN");
    }


    public void hola(){
        System.out.println("Hola");
    }

    public class Contador extends CountDownTimer {

        public Contador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            fin();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            hola();
        }

    }

}