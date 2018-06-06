package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO rdao;
	private List <River> rivers;
	
	
	public Model () {
		
		this.rdao = new RiversDAO();
		
		this.rivers = rdao.getAllRivers();
	}

	public List <River> getAllRivers() {
		if (this.rivers == null)
			return new ArrayList<>();
		
		return rivers;
	}
	
	public void setAllFlowsFromRiver (River river){ 
		river.setFlows(rdao.getFlowFromRiver(river));
	}
	

}
