package com.example.chdm.ogo_test.elems;

/**
 * Created by Dima on 14.11.2017.
 */

//Класс, где числу соответствует цвет
public class TableElem
{
	//Номер
	private Integer number;

	//Цвет: False == Black, True == Red
	private Boolean color;

	public TableElem(Integer number, Boolean color)
	{
		this.number = number;
		this.color = color;
	}

	public Integer getNumber(){
		return number;
	}

	public void setNumber(Integer number){
		this.number = number;
	}

	public Boolean getColor(){
		return color;
	}

	public void setColor(Boolean color){
		this.color = color;
	}
}
