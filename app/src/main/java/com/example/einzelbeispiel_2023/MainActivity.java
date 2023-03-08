package com.example.einzelbeispiel_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input = findViewById(R.id.Eingabe);
        TextView output = findViewById(R.id.Ausgabe);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(view -> {
            String matrNumber = input.getText().toString();

            ClientThread clientThread = new ClientThread(matrNumber);
            Thread thread = new Thread(clientThread);
            thread.start();

            try {
                thread.join();
            }
            catch (InterruptedException ex) {
                Log.e("Error", "Interrupted Exception was caught while waiting for a client thread! " + ex.getMessage());
            }

            output.setText(clientThread.getAnswerFromServer());
        });

        Button btnPrimeNumbers = findViewById(R.id.primeNumbersBtn);
        btnPrimeNumbers.setOnClickListener(view -> {
            String matrNumber = input.getText().toString();
            //matrNumber has always 8 digits!
            if (!checkMatrNumber(matrNumber)) {
                output.setText("Matrikelnummer ist ungueltig!");
            }
            else {
                StringBuilder sb = new StringBuilder();
                //there are just 4 prime digits - 2,3,5,7
                int[] primeDigits = {2,3,5,7};
                int[] matrNumberDigits = new int[8];
                for (int i = 0; i < matrNumber.length(); i++) {
                    matrNumberDigits[i] = Character.getNumericValue(matrNumber.charAt(i));
                }

                for (int i = 0; i < primeDigits.length; i++) {
                    for (int j = 0; j < matrNumberDigits.length; j++) {
                        if (matrNumberDigits[j] == primeDigits[i]) {
                            sb.append(matrNumberDigits[j] + " ");
                            //when the digits should not be printed twice or more,
                            //then just put break here
                        }
                    }
                }
                output.setText(sb.toString());
            }

        });

    }

    public boolean checkMatrNumber(String matrNumber) {
        //matrikel Number contains always 8 digits
        if (matrNumber.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

}