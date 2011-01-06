package com.mobilebuttes.fortyk;

public class FKScenario {	
	private FKCombatType combatType = FKCombatType.SHOOTING;
	private FKAttacker attacker = new FKAttacker();
	private FKUnit target = new FKUnit();
	
	// Result vars
	private double probability = 0.0;
	// Vehicle vars
	private double noEffectChance = 0.0;
	private double shakenChance = 0.0;
	private double stunnedChance = 0.0;
	private double weaponDestroyedChance = 0.0;
	private double immobileChance = 0.0;
	private double wreakedChance = 0.0;
	private double explodChance = 0.0;	
	
	public FKCombatType getCombatType() {
		return combatType;
	}
	public void setCombatType(FKCombatType combatType) {
		this.combatType = combatType;
	}
	public FKUnit getTarget() {
		return target;
	}	
	public FKAttacker getAttacker() {
		return attacker;
	}	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public double getNoEffectChance() {
		return noEffectChance;
	}
	public void setNoEffectChance(double noEffectChance) {
		this.noEffectChance = noEffectChance;
	}
	public double getShakenChance() {
		return shakenChance;
	}
	public void setShakenChance(double shakenChance) {
		this.shakenChance = shakenChance;
	}
	public double getStunnedChance() {
		return stunnedChance;
	}
	public void setStunnedChance(double stunnedChance) {
		this.stunnedChance = stunnedChance;
	}
	public double getWeaponDestroyedChance() {
		return weaponDestroyedChance;
	}
	public void setWeaponDestroyedChance(double weaponDestroyedChance) {
		this.weaponDestroyedChance = weaponDestroyedChance;
	}
	public double getImmobileChance() {
		return immobileChance;
	}
	public void setImmobileChance(double immobileChance) {
		this.immobileChance = immobileChance;
	}
	public double getWreakedChance() {
		return wreakedChance;
	}
	public void setWreakedChance(double wreakedChance) {
		this.wreakedChance = wreakedChance;
	}
	public double getExplodeChance() {
		return explodChance;
	}
	public void setExplodeChance(double explodChance) {
		this.explodChance = explodChance;
	}
	public void resetVehicleDamage() {
		noEffectChance = 0.0;
		shakenChance = 0.0;
		stunnedChance = 0.0;
		weaponDestroyedChance = 0.0;
		immobileChance = 0.0;
		wreakedChance = 0.0;
		explodChance = 0.0;		
	}
}

