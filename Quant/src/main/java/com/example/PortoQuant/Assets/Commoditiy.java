package com.example.PortoQuant.Assets;

import java.util.ArrayList;

public class Commoditiy extends Asset{

	public Commoditiy(double price) {
		super(Asset.AssetType.COMMODITY.toString(), Asset.AssetType.COMMODITY);
		// TODO Auto-generated constructor stub
		this.setTotalPrice(price);
	}

	ArrayList<String> comodities;
	
	

	@Override
	void calculateExpectedReturn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void calculateVolatility() {
		// TODO Auto-generated method stub
		
	}
}
