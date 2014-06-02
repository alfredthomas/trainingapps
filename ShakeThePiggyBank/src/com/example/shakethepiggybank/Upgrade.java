package com.example.shakethepiggybank;

public class Upgrade {
	private int cost;
	private int value;
	private String name;
	public Upgrade(int cost, int value, String name){
		this.cost = cost;
		this.value = value;
		this.name = name;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 @Override
    public String toString() {
        return "Buy "+this.name+" for $"+this.cost;
    }
}
