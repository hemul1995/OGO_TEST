package com.example.chdm.ogo_test.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.adapters.StatisticsElemsAdapter;
import com.example.chdm.ogo_test.asynctasks.SendStatisticsTask;
import com.example.chdm.ogo_test.asynctasks.TakeStatisticsTask;
import com.example.chdm.ogo_test.elems.StatisticsElem;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ResultActivity extends AppCompatActivity
{
    SendStatisticsTask sendStatisticsTask;

    private void startTask()
    {
        sendStatisticsTask = new SendStatisticsTask()
        {
            /**
             * Вывод информации на экран устройства
             * @param values
             */
            @Override
            protected void onProgressUpdate(String... values)
            {
                Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
            }
        };
    }

    /**
     * создание и отрисовка ResultActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // передача данных из предыдущей активити
        Intent intent = getIntent();
        float[] results = intent.getFloatArrayExtra("result"); // Время, ошибки

        TextView textView = (TextView)findViewById(R.id.first_time);
        textView.setText(Math.round(results[0]) + "");

        textView = (TextView)findViewById(R.id.second_time);
        textView.setText(Math.round(results[1]) + "");

        textView = (TextView)findViewById(R.id.summary_time);
        textView.setText(Math.round(results[0] + results[1]) + "");

        textView = (TextView)findViewById(R.id.first_mistakes);
        textView.setText((int)results[2] + "");

        textView = (TextView)findViewById(R.id.second_mistakes);
        textView.setText((int)results[3] + "");

        textView = (TextView)findViewById(R.id.summary_mistakes);
        textView.setText((int)(results[2] + results[3]) + "");

        // подсчет оценки и коэффициента внимания
        float mark = Math.round((results[0] + results[1])  / 2);
        int mark_5 = 5;
        if (mark >= 61)
            mark_5 = 1;
        else
        if (mark >= 51)
            mark_5 = 2;
        else
        if (mark >= 38)
            mark_5 = 3;
        else
        if(mark >= 30)
            mark_5 = 4;
        if (results[2] + results[3] > 4)
            mark_5 -= 1;

        textView = (TextView)findViewById(R.id.num_coef);
        textView.setText((int)(mark) + "");

        textView = (TextView)findViewById(R.id.num_mark);
        textView.setText(mark_5 + "");

        // новый поток для подключения к интернету
        startTask();
        sendStatisticsTask.execute(results[0], results[1], results[2], results[3], mark);
    }

    /**
     * При нажатии кнопки назад идет переход на MainActivity
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * При нажатии кнопки идет переход на StatisticsActivity
     */
    public void buttonStatClick(View view)
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    /**
     * При нажатии кнопки идет переход на TestActivity
     */
    public void buttonTestClick(View view)
    {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    /**
     * == onBackPressed()
     */
    public void buttonMenuClick(View view)
    {
        onBackPressed();
    }
}