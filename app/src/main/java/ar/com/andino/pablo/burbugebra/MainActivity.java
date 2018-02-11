package ar.com.andino.pablo.burbugebra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText numerador, denominador;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        numerador = findViewById(R.id.editText1);
        denominador = findViewById(R.id.editText2);

        resultado = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = Integer.valueOf(numerador.getText().toString());
                int den = Integer.valueOf(denominador.getText().toString());

                resultado.setText(num + " / " + den);

                numerador.setText(factToString(getFactors(num)));
                denominador.setText(factToString(getFactors(den)));

            }
        });

    }

    public HashMap<Integer, Integer> getFactors(int value) {
        HashMap<Integer, Integer> factors = new HashMap<>();

        int partial = value;
        for (int i = 2 ; i < value ; i++) {
            factors.put(i, 0);
            while (partial % i == 0){
                partial = partial / i;
                factors.put(i, factors.get(i)+1);
            }

        }

        return factors;
    }

    public String factToString(HashMap<Integer, Integer> factors) {

        String values = "1";

        for (Integer primo : factors.keySet()){
            if (factors.get(primo) > 0)
                values = values + " x " + primo + "^" + factors.get(primo);
        }

        return values;
    }

}