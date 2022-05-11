package com.autoai.readnotification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class editarRespostas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_respostas);

        String[] filenames = {"resp1", "resp2", "resp3", "resp4", "resp5", "resp6"};
        int[] tvIds = {R.id.editTextTextMultiLine,R.id.editTextTextMultiLine2,R.id.editTextTextMultiLine3,R.id.editTextTextMultiLine4,R.id.editTextTextMultiLine5,R.id.editTextTextMultiLine6};
        String resposta = "oi";

        for(int i=0; i< filenames.length; i++) {
            TextView tv = (TextView) findViewById(tvIds[i]);

            try {
                resposta = capturarDados(tv, filenames[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            tv.setText(resposta);
        }
    }


    public void salvarDadods(View view){
        String[] filenames = {"resp1", "resp2", "resp3", "resp4", "resp5", "resp6"};
        int[] tvIds = {R.id.editTextTextMultiLine,R.id.editTextTextMultiLine2,R.id.editTextTextMultiLine3,R.id.editTextTextMultiLine4,R.id.editTextTextMultiLine5,R.id.editTextTextMultiLine6};

        for(int i=0; i< filenames.length; i++) {

            String filename = filenames[i];
            TextView tv = (TextView) findViewById(tvIds[i]);
            String fileContents = tv.getText().toString();
            try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(fileContents.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String capturarDados(View view, String filename) throws FileNotFoundException {

        FileInputStream fis = this.openFileInput(filename);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
            return contents;
        }
    }
}