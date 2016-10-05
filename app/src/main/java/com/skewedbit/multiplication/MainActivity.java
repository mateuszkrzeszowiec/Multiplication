package com.skewedbit.multiplication;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button checkBtn;
    private Button nextBtn;
    private EditText resultEdit;
    private TextView equationText;
    private View rootLayout;

    private int result = 0;
    Random random = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBtn = (Button) findViewById(R.id.check);
        nextBtn = (Button) findViewById(R.id.next);
        resultEdit = (EditText) findViewById(R.id.result);
        equationText = (TextView) findViewById(R.id.equation);

        rootLayout = (View) findViewById(R.id.root_layout);

        random = new Random();

        updateEquation();

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String resultText = resultEdit.getText().toString().trim();
                if (resultText.isEmpty() || result != Integer.parseInt(resultText)) {
                    Snackbar.make(rootLayout, "Policz jeszcze raz smyku!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                updateEquation();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String resultText = resultEdit.getText().toString().trim();
                if (resultText.isEmpty() == false && result == Integer.parseInt(resultText)) {
                    Snackbar.make(rootLayout, "Brawo Jacek!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(rootLayout, "Policz jeszcze raz smyku!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });

    }

    private void updateEquation() {

        int a = random.nextInt(9) + 1;
        int b = random.nextInt(9) + 1;
        result = a * b;
        equationText.setText(a + " * " + b + " = ");

        final EditText resultEdit = (EditText) findViewById(R.id.result);
        resultEdit.setText("");

        equationText.requestFocusFromTouch();
    }
}
