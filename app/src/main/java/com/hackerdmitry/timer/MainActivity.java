package com.hackerdmitry.timer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private CountDownTimer countDownTimer;

    private final String[][] NAMES = {
        {"дня", "день", "дней"},
        {"часа", "час", "часов"},
        {"минуты", "минута", "минут"},
        {"секунды", "секунда", "секунд"}
    };
    private final long MILLISECONS_IN_DAY = 86400000,
                       MILLISECONS_IN_HOUR = 3600000,
                       MILLISECONS_IN_MINUTE = 60000,
                       MILLISECONS_IN_SECOND = 1000;
    private final String EVENT = "10.10.2018 18:20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(GetMillisUntilFinished(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(getPluralizeName(0, millisUntilFinished / MILLISECONS_IN_DAY) + ", " +
                        getPluralizeName(1, millisUntilFinished % MILLISECONS_IN_DAY / MILLISECONS_IN_HOUR) + ", " +
                        getPluralizeName(2, millisUntilFinished % MILLISECONS_IN_HOUR / MILLISECONS_IN_MINUTE) + ", " +
                        getPluralizeName(3, millisUntilFinished % MILLISECONS_IN_MINUTE / MILLISECONS_IN_SECOND));
            }

            @Override
            public void onFinish() {
                timer.setText("Курс начался!");
            }
        }.start();
    }

    private long GetMillisUntilFinished()
    {
        try {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US).parse(EVENT).getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            return 0;
        }
    }

    private String getPluralizeName(int numberOfTime, long countToFinished) {
        return countToFinished + " " + NAMES[numberOfTime][pluralize(countToFinished)];
    }

    private int pluralize(long n) {
        if (n % 10 >= 2 && n % 10 <= 4 && n % 100 / 10 != 1)
            return 0;
        if (n % 10 == 1 && n % 100 / 10 != 1)
            return 1;
        return 2;
    }
}