package com.example.PortoQuant.Assets;

import java.util.ArrayList;

public class MutualFunds extends Asset {

	public MutualFunds(double price) {
		super(Asset.AssetType.MUTUAL_FUND.toString(), Asset.AssetType.MUTUAL_FUND);
		// TODO Auto-generated constructor stub
		this.setTotalPrice(price);
	}

	ArrayList<String> MutualFunds;

	@Override
	void calculateExpectedReturn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void calculateVolatility() {
		// TODO Auto-generated method stub
		
	}

}
