package com.example.chdm.ogo_test.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Dima on 17.12.2017.
 */

/**
 * Класс для доступа к БД на устройстве
 */
public class LocalDatabaseHelper extends SQLiteOpenHelper implements BaseColumns
{
	// имя файла БД
	private static final String DATABASE_NAME = "localDB.db";
	// версия базы данных
	private static final int DATABASE_VERSION = 1;

	public LocalDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public LocalDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	/**
	 * Если нет БД, то создает её и таблицу
	 * @param db
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL
				(
					"create table localRatings\n" +
					"(\n" +
					"errors1 real not null,\n" +
					"errors2 real not null,\n" +
					"time1 real not null,\n" +
					"time2 real not null,\n" +
					"mark real not null,\n" +
					"mak varchar not null\n" +
					")"
				);
	}

	/**
	 * Если новая версия БД, то идет обновление
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//--
	}
}
