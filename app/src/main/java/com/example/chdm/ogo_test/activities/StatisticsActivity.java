package com.example.chdm.ogo_test.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.asynctasks.TakeStatisticsTask;
import com.example.chdm.ogo_test.elems.StatisticsElem;
import com.example.chdm.ogo_test.adapters.StatisticsElemsAdapter;
import com.example.chdm.ogo_test.helpers.NetworkHelper;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity
{
    boolean isGlobal = true;
    TakeStatisticsTask takeStatisticsTask;

    private void startTask()
    {
        takeStatisticsTask = new TakeStatisticsTask()
        {
            /**
             * Вывод данных на экран устройства
             * @param result
             */
            @Override
            protected void onPostExecute(List<StatisticsElem> result)
            {
                super.onPostExecute(result);
                if(result != null)
                {
                    ListView listView = (ListView)findViewById(R.id.listView);
                    listView.setAdapter(new StatisticsElemsAdapter(getApplicationContext(), result));
                }
            }

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
     * создание и отрисовка StatisticsActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        startTask();
        takeStatisticsTask.execute(getString(R.string.query_globalStat_mark));
    }

    /**
     * При нажатии кнопки назад идет переход на MainActivity
     */
    @Override
    public void onBackPressed() // кнопка "назад" на телефоне
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * При нажатии кнопки выводится собственная статистика на экран
     */
    public void buttonGetLocalStat(View v) throws SocketException
    {
        setTitle(R.string.local_stat);
        isGlobal = false;
        startTask();
        takeStatisticsTask.execute(getString(R.string.query_localStat_mark).replaceFirst("@", "\'" + NetworkHelper.getMACAddress() + "\'"));
        //Toast.makeText(getApplicationContext(), "Переключена на локальную статистику по рейтингу.", Toast.LENGTH_SHORT);
    }

    /**
     * При нажатии кнопки выводится общая статистика на экран
     */
    public void buttonGetGlobalStat(View v)
    {
        setTitle(R.string.global_stat);
        isGlobal = true;
        startTask();
        takeStatisticsTask.execute(getString(R.string.query_globalStat_mark));
        //Toast.makeText(getApplicationContext(), "Переключена на глобальную статистику по рейтингу.", Toast.LENGTH_SHORT);
    }

    /**
     * Создается меню по нажатию трёх точек или кнопки меню на телефоне
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) // по нажатию трёх точек или кнопки меню на телефоне
    {
        getMenuInflater().inflate(R.menu.menu_stat, menu);
        return true;
    }

    /**
     * Обработчик нажатия на элемент меню
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) // нажатие пунктов меню
    {
        startTask();
        int id = item.getItemId();
        switch (id)
        {
            case R.id.summa_errors:
                if(isGlobal)
                    takeStatisticsTask.execute(getString(R.string.query_globalStat_errors));
                else
                    try
                    {
                        takeStatisticsTask.execute(getString(R.string.query_localStat_errors).replaceFirst("@", "\'" + NetworkHelper.getMACAddress() + "\'"));
                    }
                    catch (SocketException e)
                    {
                        e.printStackTrace();
                    }
                break;
            case R.id.summa_time:
                if(isGlobal)
                    takeStatisticsTask.execute(getString(R.string.query_globalStat_time));
                else
                    try
                    {
                        takeStatisticsTask.execute(getString(R.string.query_localStat_time).replaceFirst("@","\'" + NetworkHelper.getMACAddress()  + "\'"));
                    }
                    catch (SocketException e)
                    {
                        e.printStackTrace();
                    }
                break;
            case R.id.mark:
                if(isGlobal)
                    takeStatisticsTask.execute(getString(R.string.query_globalStat_mark));
                else
                    try
                    {
                        takeStatisticsTask.execute(getString(R.string.query_localStat_mark).replaceFirst("@", "\'" + NetworkHelper.getMACAddress() + "\'"));
                    }
                    catch (SocketException e)
                    {
                        e.printStackTrace();
                    }
                break;
        }
        return true;
    }
}
