package com.mobilebuttes.fortyk;

public class FKAttacker extends FKUnit {
	private int attacks = 1;		
	private int ap = 5;
	private int strength = 4;
	
	// Weapon attributes
	private int poisoned = 0; // wound on 4+, str >= t then reroll failed wounds
	private boolean rending = false; 
	private boolean sniper = false;
	private boolean twl = false; 	
	private boolean ordnance = false;
	private boolean melta = false;
	private boolean lance = false;
	private boolean lightningClaws = false; // power weapon, reroll failed wounds
	private boolean powerWeapon = false; // no armor save
	private boolean powerFist = false; // power weapon, doubles str up to 10	
	private boolean witchBlade = false; // to wound is +2, against vehicles they are S9
	private boolean thunderHammer = false; // powerfist  - against vehicles w/o initiative it causes crew shaken


	public int getStrength(FKTargetType target,FKCombatType type) {
		if(sniper && target == FKTargetType.ARMORED && type == FKCombatType.SHOOTING) return 3; // BRB p31.2.9 - Sniper 
		if(witchBlade && target == FKTargetType.ARMORED && type == FKCombatType.CLOSE_COMBAT) return 9; // BRB p42.2.3 - Witchblades
		if((powerFist || thunderHammer) && type == FKCombatType.CLOSE_COMBAT) {
			return (strength * 2 < 10) ? strength * 2 : 10;
		}
		
		if(strength < 0) return 0;
		return strength;
	}
	public void setStrength(int strength) throws OutOfAcceptableRange {
		if(strength <= 10 && strength >= -1)
			this.strength = strength;
		else
			throw new OutOfAcceptableRange("Strength out of range");
	}
	public int getAP() {
		if(ap < 0) return 0;		
		return ap;
	}
	public void setAP(int ap) throws OutOfAcceptableRange {
		if(ap >= -1 && ap <= 6)	
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
	public boolean isRending() {
		if(sniper) return true;
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
	public boolean isPowerFist() {
		return powerFist;
	}
	public void setPowerFist(boolean powerFist) {
		this.powerFist = powerFist;
	}
	
	public boolean isLightningClaws() {
		return lightningClaws;
	}
	public void setLightningClaws(boolean lightningClaws) {
		this.lightningClaws = lightningClaws;
	}
	public boolean isPowerWeapon() {
		if(lightningClaws || powerFist || thunderHammer) return true;
		
		return powerWeapon;
	}
	public void setPowerWeapon(boolean powerWeapon) {
		this.powerWeapon = powerWeapon;
	}
	public int getPoisoned() {
		return poisoned;
	}
	public void setPoisoned(int poisoned) throws OutOfAcceptableRange {
		if(poisoned >= -1 && poisoned <= 6)	
			this.poisoned = poisoned;
		else 
			throw new OutOfAcceptableRange("Poison out of range");
	}
	public boolean isWitchBlade() {
		return witchBlade;
	}
	public void setWitchBlade(boolean witchBlade) {
		this.witchBlade = witchBlade;
	}
	public boolean isThunderHammer() {
		return thunderHammer;
	}
	public void setThunderHammer(boolean thunderHammer) {
		this.thunderHammer = thunderHammer;
	}
	public boolean isArmorIgnored(FKTargetType target,FKCombatType type) {
		if(isPowerWeapon() && target == FKTargetType.UNARMORED && type == FKCombatType.CLOSE_COMBAT) return true;
		
		return false;
	}	
	public boolean isOnWoundFailureRR(int toughness) {
		if(lightningClaws || (poisoned > 0 && strength >= toughness)) return true;		
		return this.onWoundFailureRR;
	}
}
