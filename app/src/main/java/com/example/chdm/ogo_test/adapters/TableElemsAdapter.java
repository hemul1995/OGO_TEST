package com.example.chdm.ogo_test.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.elems.TableElem;
import com.example.chdm.ogo_test.activities.TestActivity;
import com.example.chdm.ogo_test.activities.ResultActivity;

import java.util.List;

/**
 * Created by Dima on 14.11.2017.
 */

public class TableElemsAdapter extends BaseAdapter
{
	//объект, который предоставляет доступ к базовым функциям активити
	private Context context;
	//массив данных
	private List<TableElem> tableElems;

	public TableElemsAdapter(Context context, List<TableElem> tableElems)
	{
		this.context = context;
		this.tableElems = tableElems;
	}

	@Override
	public int getCount()
	{
		return tableElems.size();
	}

	@Override
	public Object getItem(int position)
	{
		return tableElems.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		//связывание xml элемента списка с самим контейнером
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.table_elem, null);

		//нумерация элементов GridView в соответствии с данными
		Button button = (Button) view.findViewById(R.id.button_table_elem);
		button.setText(tableElems.get(position).getNumber().toString());

		//раскраска элементов GridView в соответствии с данными
		if(tableElems.get(position).getColor() == Boolean.TRUE)
		{
			button.setBackgroundColor(Color.RED);
		}
		if(tableElems.get(position).getColor() == Boolean.FALSE)
		{
			button.setBackgroundColor(Color.BLACK);
		}

		//обработчик нажатия на элемент контейнера (подсвечивание)
		button.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				TableElem currElem = TestActivity.currentElem;
				TableElem selectElem = tableElems.get(position);

				//Если человек нажал на верную ячейку, то она окрашивается в зеленый цвет
				if(selectElem.getNumber()==currElem.getNumber() && selectElem.getColor() == currElem.getColor())
				{
					//режим нажатия
					switch (event.getAction())
					{
						case MotionEvent.ACTION_DOWN:
							v.setBackgroundColor(Color.GREEN);
							break;
						case MotionEvent.ACTION_UP:
							v.setBackgroundColor(selectElem.getColor() ? Color.RED : Color.BLACK);
							break;
					}
				}
				return false;
			}
		});


		//обработчик нажатия на элемент контейнера и отрабатывание логики теста Горбова-Шульте
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableElem currElem = TestActivity.currentElem;
				TableElem selectElem = tableElems.get(position);

				if(selectElem.getNumber() == currElem.getNumber() && selectElem.getColor() == currElem.getColor())
				{
					//1 этап
					if(TestActivity.stage == false)
					{
						//если черный от 1 до 24
						if(currElem.getColor() == Boolean.FALSE && currElem.getNumber() < 24)
						{
							currElem.setNumber(currElem.getNumber()+1);
						}
						else
							//смена цвета на красный
							if(currElem.getColor() == Boolean.FALSE && currElem.getNumber() == 24)
							{
								currElem.setNumber(25);
								currElem.setColor(Boolean.TRUE);
							}
							else
								//красный по убыванию
								if(currElem.getColor() == Boolean.TRUE && currElem.getNumber() <= 25 && currElem.getNumber() > 1)
								{
									currElem.setNumber(currElem.getNumber()-1);
								}
								else
									//последний элемент
									if(currElem.getColor() == Boolean.TRUE && currElem.getNumber() == 1)
									{
										//замеряем время
										TestActivity.time1 = System.nanoTime() - TestActivity.time1;

										//делаем кнопку следующего квадрата невидимой
										TestActivity.buttonCurrElem.setVisibility(View.INVISIBLE);

										TestActivity.buttonNextRound.setVisibility(View.VISIBLE);
										TestActivity.tv.setVisibility(View.INVISIBLE);
										return;
									}
					}
					else // начало 2 стадии
					{
						//последний элемент
						if(currElem.getColor() == Boolean.TRUE && currElem.getNumber() == 1)
						{
							//замеряем время
							TestActivity.time2 = System.nanoTime() - TestActivity.time2;

							//переход на ResultActivity
							Intent intent = new Intent(context, ResultActivity.class);
							intent.putExtra("result", new float[]{(float)TestActivity.time1/1000000000, (float)TestActivity.time2/1000000000, (float)TestActivity.errors1, (float)TestActivity.errors2});
							context.startActivity(intent);
							return;
						}
						else
						{

							//если черный от 1 до 24
							if (currElem.getColor() == Boolean.TRUE)
							{
								currElem.setNumber(25 - currElem.getNumber() + 1);
								currElem.setColor(Boolean.FALSE);
							} else	//если красный от 25 до 1
							{
								currElem.setNumber(25 - currElem.getNumber());
								currElem.setColor(Boolean.TRUE);
							}
						}
					}

					//подает данные следующего квадрата кнопке
					TestActivity.buttonCurrElem.setText(currElem.getNumber() + "");
					TestActivity.buttonCurrElem.setBackgroundColor(currElem.getColor() ? Color.RED : Color.BLACK);
				}
				else //счетчик ошибок для 1-й и 2-й стадии
				{
					if(TestActivity.stage == false)
					{
						TestActivity.errors1++;
					}
					else
					{
						TestActivity.errors2++;
					}
				}
			}
		});
		return view;
	}
}
