package com.example.PortoQuant.Assets;

import java.util.ArrayList;

public class Cash extends Asset{

	public Cash(double price) {
		super(Asset.AssetType.CASH.toString(), Asset.AssetType.CASH);
		this.setTotalPrice(price);
		// TODO Auto-generated constructor stub
	}

	ArrayList<String> bonds;
	
	

	@Override
	public
	void calculateExpectedReturn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public
	void calculateVolatility() {
		// TODO Auto-generated method stub
		
	}

}
