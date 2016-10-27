package com.skewedbit.multiplication;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_TIMINGS = "BUNDLE_KEY_TIMINGS";
    public static final String BUNDLE_KEY_RESULT = "BUNDLE_KEY_RESULT";
    public static final String BUNDLE_KEY_RESULT_EDIT_TEXT = "BUNDLE_KEY_RESULT_EDIT_TEXT";
    public static final String BUNDLE_KEY_EQUATION_TEXT = "BUNDLE_KEY_EQUATION_TEXT";
    public static final String BUNDLE_KEY_EQUATION_DISPLAYED_MILLIS = "BUNDLE_KEY_EQUATION_DISPLAYED_MILLIS";
    private Button checkBtn;
    private Button nextBtn;
    private EditText resultEdit;
    private TextView equationText;
    private View rootLayout;
    private Map<String, Long> timings;
    private Long equationDisplayedMillis;

    private int result = 0;
    Random random = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int tmpResult = 0;
        String tmpResultEditText = null;
        String tmpEquationText = null;
        Long tmpEquationDisplayedMillis = null;

        if (savedInstanceState != null) {
            timings = (Map<String, Long>) savedInstanceState.getSerializable(BUNDLE_KEY_TIMINGS);
            if (timings == null) {
                timings = new HashMap<>();
            }

            tmpResult = savedInstanceState.getInt(BUNDLE_KEY_RESULT);
            tmpResultEditText = savedInstanceState.getString(BUNDLE_KEY_RESULT_EDIT_TEXT);
            tmpEquationText = savedInstanceState.getString(BUNDLE_KEY_EQUATION_TEXT);
            tmpEquationDisplayedMillis = savedInstanceState.getLong(BUNDLE_KEY_EQUATION_DISPLAYED_MILLIS);

        } else {
            timings = new HashMap<>();
        }

        setContentView(R.layout.activity_main);

        checkBtn = (Button) findViewById(R.id.check);
        nextBtn = (Button) findViewById(R.id.next);
        resultEdit = (EditText) findViewById(R.id.result);
        equationText = (TextView) findViewById(R.id.equation);

        rootLayout = (View) findViewById(R.id.root_layout);

        random = new Random();

        if (tmpResult != 0 && tmpResultEditText != null && tmpEquationText != null) {
            restoreEquation(tmpResult, tmpResultEditText, tmpEquationText, tmpEquationDisplayedMillis);
        } else {
            updateEquation();
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String resultText = resultEdit.getText().toString().trim();
                if (resultText.isEmpty() || result != Integer.parseInt(resultText)) {
                    Snackbar.make(rootLayout, R.string.try_again, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Long seconds = (System.currentTimeMillis() - equationDisplayedMillis) / 1000;
                    timings.put(equationText.getText().toString(), seconds);
                    updateEquation();
                }
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String resultText = resultEdit.getText().toString().trim();
                if (resultText.isEmpty() == false && result == Integer.parseInt(resultText)) {
                    Snackbar.make(rootLayout, R.string.well_done, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(rootLayout, R.string.try_again, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(BUNDLE_KEY_TIMINGS, (Serializable) timings);
        savedInstanceState.putInt(BUNDLE_KEY_RESULT, result);
        savedInstanceState.putString(BUNDLE_KEY_RESULT_EDIT_TEXT, resultEdit.getText().toString().trim());
        savedInstanceState.putString(BUNDLE_KEY_EQUATION_TEXT, equationText.getText().toString());
        savedInstanceState.putLong(BUNDLE_KEY_EQUATION_DISPLAYED_MILLIS, equationDisplayedMillis);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateEquation() {

        int a = 1;
        int b = 1;

        String equationTextString = "";



        int tries = 10;
        while(tries-- >= 0) {
            a = random.nextInt(9) + 1;
            b = random.nextInt(9) + 1;

            equationTextString = a + " * " + b + " = ";

            if(timings.get(equationTextString) == null || timings.get(equationTextString) < 30) {
                break;
            }
        }

        result = a * b;
        equationText.setText(equationTextString);

        resultEdit = (EditText) findViewById(R.id.result);
        resultEdit.setText("");

        equationDisplayedMillis = System.currentTimeMillis();

        equationText.requestFocusFromTouch();
    }

    private void restoreEquation(int tmpResult, String tmpResultEditText, String tmpEquationText, Long tmpEquationDisplayedMillis) {
        result = tmpResult;
        resultEdit.setText(tmpResultEditText);
        equationText.setText(tmpEquationText);
        equationDisplayedMillis = tmpEquationDisplayedMillis;
    }
}
