package com.example.PortoQuant.analyticalModels;

import java.util.List;

public class TimeVaryingVolitality implements volitalityModel {

	List<Double> volitality;
	
	
	public TimeVaryingVolitality(List<Double> volitality) {
		super();
		this.volitality = volitality;
	}

	@Override
	public double getValue(int timeStep) {
		// TODO Auto-generated method stub
		return timeStep>=volitality.size()?volitality.get(volitality.size()-1):volitality.get(timeStep);
		
	}

}
