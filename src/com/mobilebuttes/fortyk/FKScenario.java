package com.mobilebuttes.fortyk;

public class FKScenario {	
	private FKScenarioType type = FKScenarioType.SHOOTING;
	private FKScenarioTarget target = FKScenarioTarget.UNARMORED;
	
	private int bs = 4;
	private int strength = 4;
	private int ap = 5;
	private int attacks = 1;
	
	private int toughness = 4;
	private int armor = 4;
	private int inv = -1;
	
	private boolean rending = false;
	private boolean sniperSetRending = false;
	private boolean sniper = false;
	private boolean twl = false; 	
	private boolean ordnance = false;
	private boolean melta = false;
	private boolean lance = false;
	private boolean powerFist = false;
	
	private boolean fnp = false;	
	private int cover = 0;
	private boolean openTopped = false; // TODO - +1 to dmg roll
	
	private boolean onHitFailureRR = false;
	private boolean onHitSuccessRR = false;
	private boolean onWoundFailureRR = false;
	private boolean onWoundSuccessRR = false;
	private boolean onSaveFailureRR = false;
	private boolean onSaveSuccessRR = false;
	private boolean onInvFailureRR = false;
	private boolean onInvSuccessRR = false;
	
	//Results
	private double probability = 1.0;
	
	public FKScenarioType getType() {
		return type;
	}
	public void setType(FKScenarioType type) {
		this.type = type;
	}
	public FKScenarioTarget getTarget() {
		return target;
	}
	public void setTarget(FKScenarioTarget target) {
		this.target = target;
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
		if(sniper && target == FKScenarioTarget.ARMORED) return 3; // BRB p31.2.9 - Sniper 
		
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
		if(lance && target == FKScenarioTarget.ARMORED && armor > 12) // BRB p32.2.3 - LANCE
			return 12;
			
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
	public int getAttacks() {
		return attacks;
	}
	public void setAttacks(int attacks) {
		this.attacks = attacks;
	}
	public int getInv() {
		return inv;
	}
	public void setInv(int inv) throws OutOfAcceptableRange {
		if(inv > -1 && inv < 7)
			this.inv = inv;
		else 
			throw new OutOfAcceptableRange("Inv out of range");
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
		if(rending && sniperSetRending)
			sniperSetRending = false;
		
		if(!rending && sniper) 
			sniperSetRending = true;
			rending = true;
		
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
		if(sniper && !rending)
			rending = true;
			sniperSetRending = true;
	
		if(!sniper && sniperSetRending) {
			rending = false;
			sniperSetRending = false;
		}
		
		this.sniper = sniper;
	}
	public int getCover() {
		return cover;
	}
	public void setCover(int cover) throws OutOfAcceptableRange {
		if(cover > -1 && cover < 7)	
			this.cover = cover;
		else 
			throw new OutOfAcceptableRange("Cover out of range");
	}
	public boolean isOpenTopped() {
		return openTopped;
	}
	public void setOpenTopped(boolean openTopped) {
		this.openTopped = openTopped;
	}
	public boolean isOrdnance() {
		return ordnance;
	}
	public void setOrdnance(boolean ordnance) {
		this.ordnance = ordnance;
	}
	public boolean isLance() {
		return lance;
	}
	public void setLance(boolean lance) {
		this.lance = lance;
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
	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
}

