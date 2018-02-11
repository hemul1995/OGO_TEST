package com.example.chdm.ogo_test.asynctasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.chdm.ogo_test.activities.MainActivity;
import com.example.chdm.ogo_test.helpers.NetworkHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Dima on 10.02.2018.
 */

public class SendStatisticsTask extends AsyncTask<Float, String, Void>
{
    /**
     * Сам рабочий поток, подключение к интернету происходит там
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Float... params)
    {
        try
        {
            // получение MAC-адреса
            String adress = NetworkHelper.getMACAddress();

            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            try
            {
                // соединение с сервером БД
                connection = DriverManager.
                        getConnection
                                (
                                        "jdbc:postgresql://www.students.ami.nstu.ru/students",
                                        "pmi-b3515",
                                        "yoacMal3"
                                );

            }
            catch (SQLException e) //если соединения нет
            {
                this.publishProgress("Нет интернета, результат сохранен локально!");// логи

                // дескриптор работы с БД в режиме записи
                SQLiteDatabase sdb = MainActivity.localDatabaseHelper.getWritableDatabase();

                // запись в локальную БД
                ContentValues values = new ContentValues();
                values.put("errors1", params[2]);
                values.put("errors2", params[3]);
                values.put("time1", params[0]);
                values.put("time2", params[1]);
                values.put("mark", params[4]);
                values.put("mak", adress);
                sdb.insert("localRatings", null, values);

                e.printStackTrace();
                return null;
            }

            // Если с сервером установилось соединение
            if (connection != null)
            {
                // подготовка запроса
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into pmib3515.ratings(errors1, errors2, time1, time2, name, mark, mak )" +
                                "values(?, ?, ?, ?, ?, ?, ?)"
                );
                preparedStatement.setFloat(1, params[2]);
                preparedStatement.setFloat(2, params[3]);
                preparedStatement.setFloat(3, params[0]);
                preparedStatement.setFloat(4, params[1]);
                preparedStatement.setString(5, "qwerty");
                preparedStatement.setFloat(6, params[4]);
                preparedStatement.setString(7, adress);
                preparedStatement.execute();

                // дескриптор работы с БД в режиме записи
                SQLiteDatabase sdb = MainActivity.localDatabaseHelper.getWritableDatabase();

                // курсор (структура данных всей локальной таблицы)
                Cursor cursor = sdb.query("localRatings", null, null, null, null, null, null);

                // перенос всех данных из локальной БД на сервер
                while(cursor.moveToNext())
                {
                    float errors1 = cursor.getFloat(cursor.getColumnIndex("errors1"));
                    float errors2 = cursor.getFloat(cursor.getColumnIndex("errors2"));
                    float time1 = cursor.getFloat(cursor.getColumnIndex("time1"));
                    float time2 = cursor.getFloat(cursor.getColumnIndex("time2"));
                    float mark = cursor.getFloat(cursor.getColumnIndex("mark"));
                    String mak = cursor.getString(cursor.getColumnIndex("mak"));

                    // подготовка запроса
                    preparedStatement.setFloat(1, errors1);
                    preparedStatement.setFloat(2, errors2);
                    preparedStatement.setFloat(3, time1);
                    preparedStatement.setFloat(4, time2);
                    preparedStatement.setString(5, "qwerty");
                    preparedStatement.setFloat(6, mark);
                    preparedStatement.setString(7, mak);
                    preparedStatement.execute();
                }
                cursor.close();
                preparedStatement.close();

                // очистка локальной БД
                sdb.execSQL("delete from localRatings");
                sdb.close();
            }
            else
            {
                // если сервер БД есть, но с ним что-то случилось
                this.publishProgress("Ошибка соединения!");
            }
        }
        catch(Exception er)
        {
            // глобальная ошибка чего-нибудь
            this.publishProgress(er.getMessage());
            return null;
        }
        return null;
    }
}
