package com.awesome.wow;


import java.io.Serializable;


public class Scores implements Serializable {
	
	public int HP;
	public int strength;
	public int speed;
	public int stamina;
	public int fatigue;

	public Scores(){
		HP = 0;
		strength = 0;
		speed = 0;
		stamina = 0;
		fatigue = 0;
	}
	
}