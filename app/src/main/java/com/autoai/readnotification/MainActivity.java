package com.autoai.readnotification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        editText = findViewById(R.id.editText);
        Intent intent = new Intent(MainActivity.this, MyNotifiService.class);//启动服务
        startService(intent);//Start service
        final SharedPreferences sp = getSharedPreferences("msg", MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMsg = sp.getString("getMsg", "");
                if (!TextUtils.isEmpty(getMsg)) {
                    editText.setText(getMsg);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                //Open listener reference message // Notification access
                Intent intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent_s);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_p = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivity(intent_p);
            }
        });
    }

    public void openNotificationSettings(View view) {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    public void openEditorRespostas(View view) {
        Intent intent = new Intent(this, editarRespostas.class);
        startActivity(intent);
    }

    public void ligDesliga(View view) {
        Switch ld = (Switch) findViewById(R.id.switch1);
        //Intent intent = new Intent(MainActivity.this, MyNotifiService.class);
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(ld.isChecked()){
            //desligado

            editor.putString("LigDes", "Liga");
            editor.apply();

            //startService(intent);
        }else {

            editor.putString("LigDes", "Desliga");
            editor.apply();
            //stopService(intent);
        }
    }
}