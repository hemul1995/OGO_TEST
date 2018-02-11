package com.example.chdm.ogo_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.helpers.LocalDatabaseHelper;

public class MainActivity extends AppCompatActivity
{
	// класс для доступа к БД на устройстве
	public static LocalDatabaseHelper localDatabaseHelper;

	/**
	 * точка входа в приложение
	 * создание и отрисовка MainActivity
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		localDatabaseHelper = new LocalDatabaseHelper(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	 * При нажатии кнопки идет переход на StatisticsActivity
	 */
	public void buttonStatClick(View view)
	{
		Intent intent = new Intent(this, StatisticsActivity.class);
		startActivity(intent);
	}

	/**
	 * При нажатии кнопки идет переход на HelpActivity
	 */
	public void buttonHelpClick(View view)
	{
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}

	/**
	 * При нажатии кнопки назад приложение закрывается
	 */
	@Override
	public void onBackPressed()
	{
		//--
	}
}