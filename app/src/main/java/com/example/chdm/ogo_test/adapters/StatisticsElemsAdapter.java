package com.example.chdm.ogo_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chdm.ogo_test.R;
import com.example.chdm.ogo_test.elems.StatisticsElem;

import java.util.List;

/**
 * Created by Dima on 18.12.2017.
 */

public class StatisticsElemsAdapter extends BaseAdapter
{
	//объект, который предоставляет доступ к базовым функциям активити
	private Context context;
	//массив данных
	private List<StatisticsElem> statElems;

	public StatisticsElemsAdapter(Context context, List<StatisticsElem> statElems)
	{
		this.context = context;
		this.statElems = statElems;
	}

	@Override
	public int getCount()
	{
		return statElems.size();
	}

	@Override
	public Object getItem(int position)
	{
		return statElems.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//связывание xml элемента списка с самим контейнером
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.stat_elem, null);

		TextView textView_tmp;

		//нумерация элементов ListView в соответствии с данными
		textView_tmp = (TextView) view.findViewById(R.id.textView_index);
		textView_tmp.setText(statElems.get(position).get_Number().toString());

		//нумерация элементов ListView в соответствии с данными
		textView_tmp = (TextView) view.findViewById(R.id.textView_MAC);
		textView_tmp.setText(statElems.get(position).get_MAC_adress().toString());

		//нумерация элементов ListView в соответствии с данными
		textView_tmp = (TextView) view.findViewById(R.id.textView_rating);
		textView_tmp.setText(statElems.get(position).get_rating().toString());

		return view;
	}
}
