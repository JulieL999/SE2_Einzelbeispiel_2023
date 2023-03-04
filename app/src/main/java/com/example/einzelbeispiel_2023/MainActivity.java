package com.example.einzelbeispiel_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.Eingabe);
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

                TextView output = findViewById(R.id.textView2);
                output.setText(clientThread.getAnswerFromServer());
            }
        });
    }

}