package com.example.sayacprogrami;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private Button btn_sayac;
    int sayac = 0;
    private SharedPreferences sharedPreferences,settings;
    private RelativeLayout relativeLayout;
    private Boolean ses,titresim;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sayac = findViewById(R.id.button);
        relativeLayout=findViewById(R.id.relativeLayout);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settings=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.click);
        vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        sayac = sharedPreferences.getInt("sayac_", 0);
        loadSettings();
        btn_sayac.setText(Integer.toString(sayac));


        btn_sayac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayac += 1;
                btn_sayac.setText(Integer.toString(sayac));
                if(ses){
                    mediaPlayer.start();
                }
                if(titresim){
                    vibrator.vibrate(250);
                }
            }
        });
    }

    private void loadSettings() {
        String color=settings.getString("renk","3");
        ses=settings.getBoolean("ses",false);
        titresim=settings.getBoolean("titresim",false);

        switch (Integer.parseInt(color)){
            case 1:
                relativeLayout.setBackgroundColor(Color.RED);
                break;
            case 2:
                relativeLayout.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                relativeLayout.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                relativeLayout.setBackgroundColor(Color.BLUE);
                break;
            case 5:
                relativeLayout.setBackgroundColor(Color.BLACK);
                break;
        }
        settings.registerOnSharedPreferenceChangeListener(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_restart:
                btn_sayac.setText("0");
                sayac=0;
                break;
            case R.id.action_settings:
                Intent intent=new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("sayac_", sayac);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadSettings();
    }
}
