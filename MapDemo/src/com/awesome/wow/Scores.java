package com.awesome.wow;


import java.io.Serializable;


public class Scores implements Serializable {
	
	private double id;
	private String name;
	
	// Constructor
	public Scores(double id, String name){
	    this.id = id;
	    this.name = name;
	}

	// Getters & Setters
	public double getId() {
	    return id;
	}
	public void setId(double id) {
	    this.id = id;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name = name;
	}

}