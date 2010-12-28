package com.mobilebuttes;

class FKScenario {
	int bs = 4;
	int strength = 4;
	int toughness = 4;
	int armor = 4;
	int ap = 5;
	int inv = -1;	
	
	boolean fnp = false;
	boolean vehicle = false;
	
	boolean rending = false; // Not in vehicle or assault area
	boolean melta = false; // New
	boolean twl = false; 
	boolean sniper = false;
	
	boolean onHitFailureRR = false;
	boolean onHitSuccessRR = false;
	boolean onWoundFailureRR = false;
	boolean onWoundSuccessRR = false;
	boolean onSaveFailureRR = false;
	boolean onSaveSuccessRR = false;
	boolean onInvFailureRR = false;
	boolean onInvSuccessRR = false;
}