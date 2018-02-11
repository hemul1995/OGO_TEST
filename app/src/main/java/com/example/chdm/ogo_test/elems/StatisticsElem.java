package com.example.chdm.ogo_test.elems;

/**
 * Created by Dima on 18.12.2017.
 */

public class StatisticsElem
{
	// Порядковый номер
	private Integer number;

	private String MAC_adress;

	private Float rating;

	public StatisticsElem(Integer number, String MAC_adress, Float rating)
	{
		this.number = number;
		this.MAC_adress = MAC_adress;
		this.rating = rating;
	}

	public Integer get_Number(){
		return number;
	}

	public void set_Number(Integer number){
		this.number = number;
	}

	public String get_MAC_adress(){
		return MAC_adress;
	}

	public void set_MAC_adress(String MAC_adress){
		this.MAC_adress = MAC_adress;
	}

	public Float get_rating(){
		return rating;
	}

	public void set_rating(Float rating){
		this.rating = rating;
	}
}
