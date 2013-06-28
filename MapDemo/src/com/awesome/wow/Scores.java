package com.awesome.wow;


import java.io.Serializable;


public class Scores implements Serializable {
	
	private int id;
	private String name;
	
	// Constructor
	public Scores(int id, String name){
	    this.id = id;
	    this.name = name;
	}

	// Getters & Setters
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id = id;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name = name;
	}

}