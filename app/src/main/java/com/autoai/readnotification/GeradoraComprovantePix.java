package com.autoai.readnotification;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeradoraComprovantePix extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geradora_comprovante_pix);

        Button button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(this);
        EditText tv = (EditText) findViewById(R.id.editTextTextMultiLine9);
        tv.setOnClickListener(corkyListener);

        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    TextView tv = (TextView) findViewById(R.id.editTextTextMultiLine9);
                    TextView tvplaca = (TextView) findViewById(R.id.editTextTextPersonName9);
                    TextView tvdata = (TextView) findViewById(R.id.editTextTextPostalAddress);
                    TextView tval = (TextView) findViewById(R.id.editTextNumberDecimal);

                    String str = tv.getText().toString();
                    String saida = pedaco(str, "Saída: ", 10);
                    String[] sd = saida.split("/");
                    if(saida.indexOf("/")==-1){
                        System.out.println(sd[0]);
                        tvdata.setText(sd[0]);
                    }else{
                        System.out.println(sd[2] + "-" + sd[1] + "-" + sd[0]);
                        tvdata.setText(sd[2] + "-" + sd[1] + "-" + sd[0]);
                    }

                    System.out.println(pedaco(str, "Valor: R$ ", 10));
                    System.out.println(pedaco(str, "Placa: ", 8));


                    tvplaca.setText(pedaco(str, "Placa: ", 8));
                    tval.setText(pedaco(str, "Valor: R$ ", 10));
                }catch(Exception e) {
                    //  Block of code to handle errors
                    System.out.println("Não reconhecemos variáveis");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private View.OnClickListener corkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String pasteData = "";
            EditText pasteItem = v.findViewById(R.id.editTextTextMultiLine9);

// If the clipboard doesn't contain data, disable the paste menu item.
// If it does contain data, decide if you can handle the data.
            if (!(clipboard.hasPrimaryClip())) {

                pasteItem.setEnabled(false);

            } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

                // This disables the paste menu item, since the clipboard has data but it is not plain text
                pasteItem.setEnabled(false);
            } else {

                // This enables the paste menu item, since the clipboard contains plain text.
                pasteItem.setEnabled(true);
            }

            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

// Gets the clipboard as text.
            pasteData = (String) item.getText();

// If the string contains data, then the paste operation is done
            if (pasteData != null) {
                //return true;
                pasteItem.setText(pasteData);

// The clipboard does not contain text. If it contains a URI, attempts to get data from it
            } else {
                Uri pasteUri = item.getUri();

                // If the URI contains something, try to get text from it
                if (pasteUri != null) {

                    // calls a routine to resolve the URI and get data from it. This routine is not
                    // presented here.
                    //pasteData = resolveUri(Uri);
                    System.out.println("Clipboard contains an invalid data type");
                    //return true;
                } else {

                    // Something is wrong. The MIME type was plain text, but the clipboard does not contain either
                    // text or a Uri. Report an error.
                    System.out.println("Clipboard contains an invalid data type");
                    //return false;
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        EditText tvplaca = (EditText) findViewById(R.id.editTextTextPersonName9);
        EditText tvdata = (EditText) findViewById(R.id.editTextTextPostalAddress);
        EditText tval = (EditText) findViewById(R.id.editTextNumberDecimal);

        String str = "pl=" + tvplaca.getText().toString() + "&s=" + tvdata.getText().toString() + "&v=" + tval.getText().toString();
        str = "https://estacionamentopatioconfins.com.br/vagas/gravapix.php?" + str.trim().replace("\n","");
        str = str.replaceAll("[^a-zA-Z0-9\\.\\-\\/?&=:]", "");

        openWebPage(str);
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String pedaco(String str, String oque, int en){
        int nr = str.indexOf(oque);
        if(oque.equals("Valor: R$ ")){
            return str.substring(nr + oque.length(), str.length());
        }else {
            return str.substring(nr + oque.length(), nr + oque.length() + en);
        }
    }
}