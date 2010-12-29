package com.mobilebuttes.fortyk;

class FKScenario {	
	private FKScenarioType type = FKScenarioType.SHOOTING;
	
	private int bs = 4;
	private int strength = 4;
	private int toughness = 4;
	private int armor = 4;
	private int ap = 5;
	private int inv = -1;	
		
	private boolean fnp = false;	
	
	private boolean rending = false; // Not in vehicle or assault area
	private boolean melta = false; // New
	private boolean twl = false; 
	private boolean sniper = false;
	
	private boolean onHitFailureRR = false;
	private boolean onHitSuccessRR = false;
	private boolean onWoundFailureRR = false;
	private boolean onWoundSuccessRR = false;
	private boolean onSaveFailureRR = false;
	private boolean onSaveSuccessRR = false;
	private boolean onInvFailureRR = false;
	private boolean onInvSuccessRR = false;
	
	public FKScenarioType getType() {
		return type;
	}
	public void setType(FKScenarioType type) {
		this.type = type;
	}
	public int getBS() {
		return bs;
	}
	public void setBS(int bs) throws OutOfAcceptableRange {
		if(bs > 7 && bs > 1)
			this.bs = bs;
		else
			throw new OutOfAcceptableRange("Ballistic skill out of range");
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) throws OutOfAcceptableRange {
		if(strength < 11 && strength > -4)
			this.strength = strength;
		else
			throw new OutOfAcceptableRange("Strength out of range");
	}
	public int getToughness() {
		return toughness;
	}
	public void setToughness(int toughness) throws OutOfAcceptableRange {
		if(toughness > 0 && toughness < 11)
			this.toughness = toughness;
		else
			throw new OutOfAcceptableRange("Toughness out of range");
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) throws OutOfAcceptableRange {
		if(armor > -1 && armor < 15)		
			this.armor = armor;
		else
			throw new OutOfAcceptableRange("Armor out of range");
	}
	public int getAP() {
		return ap;
	}
	public void setAP(int ap) throws OutOfAcceptableRange {
		if(ap > -1 && ap < 7)	
			this.ap = ap;
		else 
			throw new OutOfAcceptableRange("AP out of range");
	}
	public int getInv() {
		return inv;
	}
	public void setInv(int inv) throws OutOfAcceptableRange {
		if(inv > -1 && inv < 7)
			this.inv = inv;
		else 
			throw new OutOfAcceptableRange("AP out of range");
	}
	public boolean isFNP() {
		return fnp;
	}
	public void setFNP(boolean fnp) {
		this.fnp = fnp;
	}
	public boolean isRending() {
		return rending;
	}
	public void setRending(boolean rending) {
		this.rending = rending;
	}
	public boolean isMelta() {
		return melta;
	}
	public void setMelta(boolean melta) {
		this.melta = melta;
	}
	public boolean isTWL() {
		return twl;
	}
	public void setTWL(boolean twl) {
		this.twl = twl;
	}
	public boolean isSniper() {
		return sniper;
	}
	public void setSniper(boolean sniper) {
		this.sniper = sniper;
	}
	public boolean isOnHitFailureRR() {
		return onHitFailureRR;
	}
	public void setOnHitFailureRR(boolean onHitFailureRR) {
		this.onHitFailureRR = onHitFailureRR;
	}
	public boolean isOnHitSuccessRR() {
		return onHitSuccessRR;
	}
	public void setOnHitSuccessRR(boolean onHitSuccessRR) {
		this.onHitSuccessRR = onHitSuccessRR;
	}
	public boolean isOnWoundFailureRR() {
		return onWoundFailureRR;
	}
	public void setOnWoundFailureRR(boolean onWoundFailureRR) {
		this.onWoundFailureRR = onWoundFailureRR;
	}
	public boolean isOnWoundSuccessRR() {
		return onWoundSuccessRR;
	}
	public void setOnWoundSuccessRR(boolean onWoundSuccessRR) {
		this.onWoundSuccessRR = onWoundSuccessRR;
	}
	public boolean isOnSaveFailureRR() {
		return onSaveFailureRR;
	}
	public void setOnSaveFailureRR(boolean onSaveFailureRR) {
		this.onSaveFailureRR = onSaveFailureRR;
	}
	public boolean isOnSaveSuccessRR() {
		return onSaveSuccessRR;
	}
	public void setOnSaveSuccessRR(boolean onSaveSuccessRR) {
		this.onSaveSuccessRR = onSaveSuccessRR;
	}
	public boolean isOnInvFailureRR() {
		return onInvFailureRR;
	}
	public void setOnInvFailureRR(boolean onInvFailureRR) {
		this.onInvFailureRR = onInvFailureRR;
	}
	public boolean isOnInvSuccessRR() {
		return onInvSuccessRR;
	}
	public void setOnInvSuccessRR(boolean onInvSuccessRR) {
		this.onInvSuccessRR = onInvSuccessRR;
	}	
}

