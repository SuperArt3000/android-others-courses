package com.example.cooltimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SeekBar seekBar;
    private TextView textView;
    private Button button;
    private boolean isTimerOn;
    private CountDownTimer countDownTimer;
    private int defaultInterval;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        seekBar.setMax(600);
        setIntervalFromSharedPreferences(sharedPreferences);
        isTimerOn = false;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer((long) progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(v -> {

            if (!isTimerOn) {
                button.setText("Stop");
                seekBar.setEnabled(false);
                isTimerOn = true;

                countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimer(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        if (sharedPreferences.getBoolean("enable_sound", true)) {
                            String melodyName = sharedPreferences.getString("timer_melody", "bell");
                            switch (melodyName) {
                                case "bell": {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                                    mediaPlayer.start();
                                    break;
                                }
                                case "alarm_siren": {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_siren_sound);
                                    mediaPlayer.start();
                                    break;
                                }
                                case "bip": {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bip_sound);
                                    mediaPlayer.start();
                                    break;
                                }
                            }
                        }
                        resetTimer();
                    }
                }.start();
            } else {
                resetTimer();
            }
        });

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void updateTimer(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000 / 60);
        int seconds = (int) (millisUntilFinished / 1000 - (minutes * 60));

        String minutesString;
        String secondsString;

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }

        textView.setText(String.format("%s:%s", minutesString, secondsString));
    }

    private void resetTimer() {
        countDownTimer.cancel();
        button.setText("Start");
        seekBar.setEnabled(true);
        isTimerOn = false;
        setIntervalFromSharedPreferences(sharedPreferences);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        } else if (id == R.id.action_about) {
            Intent openAbout = new Intent(this, AboutActivity.class);
            startActivity(openAbout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setIntervalFromSharedPreferences(SharedPreferences sharedPreferences) {
        defaultInterval = Integer.parseInt(sharedPreferences.getString("default_interval", "30"));
        long defaultIntervalInMillis = defaultInterval * 1000;
        updateTimer(defaultIntervalInMillis);
        seekBar.setProgress(defaultInterval);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("default_interval")) {
            setIntervalFromSharedPreferences(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}