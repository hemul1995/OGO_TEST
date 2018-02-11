package com.example.chdm.ogo_test.asynctasks;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.adapters.StatisticsElemsAdapter;
import com.example.chdm.ogo_test.elems.StatisticsElem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 10.02.2018.
 */

public class TakeStatisticsTask extends AsyncTask<String, String, List<StatisticsElem>>
{
    /**
     *
     * @param params
     * @return
     */
    @Override
    protected List<StatisticsElem> doInBackground(String... params) {
        List<StatisticsElem> elems = new ArrayList<>();
        try
        {
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
            catch (SQLException e)//если соединения нет
            {
                this.publishProgress("Нет соединения с БД. Нет статистики.");// логи
                e.printStackTrace();
                return null;
            }

            // Если с сервером установилось соединение
            if (connection != null)
            {
                int i = 0;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(params[0]);

                //перемещение данных из курсора в список
                while (resultSet.next())
                {
                    String MAC_adress = resultSet.getString("mak");
                    Float rating = resultSet.getFloat("tmp");
                    elems.add(new StatisticsElem(++i, MAC_adress, rating));
                }
            }
            else
            {
                // Если сервер БД есть, но с ним что-то случилось
                this.publishProgress("Ошибка соединения!");
            }
        } catch (Exception er) {
            // Если будет ошибка в запросах к БД
            this.publishProgress(er.getMessage());
            return null;
        }
        return elems;
    }
}
