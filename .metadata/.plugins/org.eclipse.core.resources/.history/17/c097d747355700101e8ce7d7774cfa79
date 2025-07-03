package com.example.PortoQuant.analyticalModels;

import java.util.List;

public class TimeVaryingExpectedReturnModel implements ExpectedReturnModel {

	List<Double> expectedReturn;
	
	
	public TimeVaryingExpectedReturnModel(List<Double> expectedReturn) {
		super();
		this.expectedReturn = expectedReturn;
	}


	@Override
	public double getValue(int timeStep) {
		// TODO Auto-generated method stub
		return timeStep>=expectedReturn.size()?expectedReturn.get(expectedReturn.size()-1):expectedReturn.get(timeStep);
	}

}
