package ar.com.andino.pablo.burbugebra;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ar.com.andino.pablo.burbugebra", appContext.getPackageName());
    }

    @Test
    public void countDown() throws Exception {
        Contador counter = new Contador(10000,1000);
        counter.start();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {



            }


        };



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
