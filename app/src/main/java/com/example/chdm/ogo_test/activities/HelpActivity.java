package com.example.chdm.ogo_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.chdm.ogo_test.R;

public class HelpActivity extends AppCompatActivity
{
    /**
     * создание и отрисовка HelpActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView textView = (WebView) findViewById(R.id.texthelp);
        textView.loadData(getString(R.string.help_text), "text/html; charset=utf-8", "utf-8");
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
     * Создается меню по нажатию трёх точек или кнопки меню на телефоне
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        int id = item.getItemId();
        WebView textView = (WebView) findViewById(R.id.texthelp);
        switch(id)
        {
            case R.id.help_menu:
                setTitle(R.string.help_menu);
                textView.loadData(getString(R.string.help_text), "text/html; charset=utf-8", "utf-8");
                break;
            case R.id.about_menu:
                setTitle(R.string.about_menu);
                textView.loadData(getString(R.string.about_text), "text/html; charset=utf-8", "utf-8");
                break;
        }
        return true;
    }
}
