package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView processTextView;
    private TextView resultTextView;
    private StringBuilder process = new StringBuilder();
    private String operator = "";
    private double firstNumber = Double.NaN;
    private double secondNumber = Double.NaN;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processTextView = findViewById(R.id.processTextView);
        resultTextView = findViewById(R.id.resultTextView);

        findViewById(R.id.button0).setOnClickListener(v -> appendNumber("0"));
        findViewById(R.id.button1).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.button2).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.button3).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.button4).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.button5).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.button6).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.button7).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.button8).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.button9).setOnClickListener(v -> appendNumber("9"));

        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonDivide).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.buttonAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.buttonEquals).setOnClickListener(v -> calculate());
        findViewById(R.id.buttonDecimal).setOnClickListener(v -> {
            if (!process.toString().endsWith(".")) {
                appendNumber(".");
            }
        });
    }

    private void appendNumber(String number) {
        process.append(number);
        processTextView.setText(process.toString());
    }

    private void clear() {
        process.setLength(0);
        operator = "";
        processTextView.setText("");
        resultTextView.setText("");
        firstNumber = Double.NaN;
        secondNumber = Double.NaN;
    }

    private void setOperator(String op) {
        if (process.length() > 0) {
            if (!Double.isNaN(firstNumber)) {
                calculate();
            }
            operator = op;
            firstNumber = Double.parseDouble(process.toString());
            process.setLength(0);
            processTextView.setText(decimalFormat.format(firstNumber) + operator);
        }
    }

    private void calculate() {
        if (process.length() > 0 || !Double.isNaN(firstNumber)) {
            if (Double.isNaN(firstNumber)) {
                firstNumber = Double.parseDouble(process.toString());
            } else {
                secondNumber = Double.parseDouble(process.toString());
            }

            if (!Double.isNaN(secondNumber)) {
                switch (operator) {
                    case "+":
                        firstNumber += secondNumber;
                        break;
                    case "-":
                        firstNumber -= secondNumber;
                        break;
                    case "*":
                        firstNumber *= secondNumber;
                        break;
                    case "/":
                        if (secondNumber != 0) {
                            firstNumber /= secondNumber;
                        } else {
                            resultTextView.setText("Error");
                            return;
                        }
                        break;
                }
                process.setLength(0);
                process.append(decimalFormat.format(firstNumber));
                resultTextView.setText(process.toString());
                operator = "";
                secondNumber = Double.NaN;
            }
        }
    }
}

