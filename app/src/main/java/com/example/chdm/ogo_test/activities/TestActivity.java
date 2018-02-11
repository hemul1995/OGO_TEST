package com.example.chdm.ogo_test.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.elems.TableElem;
import com.example.chdm.ogo_test.adapters.TableElemsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity
{
    // массив данных пар (число, цвет)
    public static List<TableElem> tableElems = new ArrayList<>();

    // пара (число, цвет)
    public static TableElem currentElem;

    //ошибки 1-й и 2-й стадии
    public static int errors1 = 0;
    public static int errors2 = 0;

    //время 1-й и 2-й стадии
    public static long time1 = 0;
    public static long time2 = 0;

    //номер стадии
    public static boolean stage = false;

    public static Button buttonCurrElem;
    public static Button buttonNextRound;


    public static TextView tv;

    /**
     * создание и отрисовка TestActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // инициализация данных
        errors1 = 0;
        errors2 = 0;
        time1 = 0;
        time2 = 0;
        stage = false;
        currentElem = new TableElem(1, Boolean.FALSE);

        // инициализация таблицы
        tableElems = new ArrayList<>();
        for(int i = 1; i <= 24; i++)
            tableElems.add(new TableElem(i, Boolean.FALSE));
        for(int i = 1; i <= 25; i++)
            tableElems.add(new TableElem(i, Boolean.TRUE));

        //перемешивание данных таблицы
        //Collections.shuffle(tableElems);

        // инициализация сетки
        GridView gridView = (GridView)findViewById(R.id.gridView1);
        gridView.setAdapter(new TableElemsAdapter(this, tableElems));

        // запоминает глобально кнопку следующего квадрата и назначает цвет и число
        buttonCurrElem = (Button)findViewById(R.id.button_curr_elem);
        buttonCurrElem.setText(currentElem.getNumber() + "");
        buttonCurrElem.setBackgroundColor(currentElem.getColor() ? Color.RED : Color.BLACK);

        // запоминает глобально кнопку следующего этапа и делает её невидимой
        buttonNextRound = (Button)findViewById(R.id.buttonnextround);
        buttonNextRound.setVisibility(View.INVISIBLE);// кнопка снова невидима
        tv = (TextView)findViewById(R.id.textView2); // а это текст

        // начало замера времени 1-го этапа в наносекундах
        time1 = System.nanoTime();
    }

    /**
     * При нажатии кнопки запускается 2 этап
     * @param view
     */
    public void buttonNextStageClick(View view)
    {
        // этап 2
        this.stage = true;
        //перемешивание данных таблицы
        //закоменченно для тестирования
        //Collections.shuffle(tableElems);

        // инициализация сетки
        GridView gridView = (GridView)findViewById(R.id.gridView1);
        gridView.setAdapter(new TableElemsAdapter(this, tableElems));


        buttonNextRound.setVisibility(View.INVISIBLE);	// кнопка снова невидима
        tv.setVisibility(View.VISIBLE);  // нацарапал появление

        // запоминает глобально кнопку следующего квадрата и назначает цвет и число
        currentElem = new TableElem(25, Boolean.TRUE);
        buttonCurrElem = (Button)findViewById(R.id.button_curr_elem);
        buttonCurrElem.setText(currentElem.getNumber() + "");
        buttonCurrElem.setBackgroundColor(currentElem.getColor() ? Color.RED : Color.BLACK);
        buttonCurrElem.setVisibility(View.VISIBLE);

        // начало замера времени 2-го этапа в наносекундах
        time2 = System.nanoTime();
    }
}
