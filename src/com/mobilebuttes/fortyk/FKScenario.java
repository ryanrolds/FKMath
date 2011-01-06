package com.mobilebuttes.fortyk;

public class FKScenario {	
	private FKScenarioType type = FKScenarioType.SHOOTING;
	private FKScenarioTarget target = FKScenarioTarget.UNARMORED;
	// Attacker vars
	private int bs = 4;
	private int aWS = 4;
	private int strength = 4;
	private int ap = 5;
	private int attacks = 1;
	// Target vars
	private int tWS = 4;
	private int toughness = 4;
	private int armor = 4;
	private int inv = -1;
	private int cover = 0; // TODO - look over this
	private boolean fnp = false;	
	private boolean openTopped = false; // TODO - +1 to dmg roll
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
	// Reroll bools
	private boolean onHitFailureRR = false;
	private boolean onHitSuccessRR = false;
	private boolean onWoundFailureRR = false;
	private boolean onWoundSuccessRR = false;
	private boolean onSaveFailureRR = false;
	private boolean onSaveSuccessRR = false;
	private boolean onInvFailureRR = false;
	private boolean onInvSuccessRR = false;
	
	//Result vars
	private double probability = 0.0;
	// Vehicle vars
	private double noEffectChance = 0.0;
	private double shakenChance = 0.0;
	private double stunnedChance = 0.0;
	private double weaponDestroyedChance = 0.0;
	private double immobileChance = 0.0;
	private double wreakedChance = 0.0;
	private double explodChance = 0.0;	
	
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
		if(bs <= 6 && bs >= 1)
			this.bs = bs;
		else
			throw new OutOfAcceptableRange("Ballistic skill out of range");
	}
	public int getStrength() {
		if(sniper && target == FKScenarioTarget.ARMORED && type == FKScenarioType.SHOOTING) return 3; // BRB p31.2.9 - Sniper 
		if(witchBlade && target == FKScenarioTarget.ARMORED && type == FKScenarioType.CLOSE_COMBAT) return 9; // BRB p42.2.3 - Witchblades
		if((powerFist || thunderHammer) && type == FKScenarioType.CLOSE_COMBAT) {
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
	public int getToughness() {
		return toughness;
	}
	public void setToughness(int toughness) throws OutOfAcceptableRange {
		if(toughness >= -1 && toughness <= 10)
			this.toughness = toughness;
		else
			throw new OutOfAcceptableRange("Toughness out of range");
	}
	public int getArmor() {
		if(lance && target == FKScenarioTarget.ARMORED && armor > 12) // BRB p32.2.3 - LANCE
			return 12;
		
		if(armor < 0) return 0;		
		return armor;
	}
	public void setArmor(int armor) throws OutOfAcceptableRange {
		if(armor >= -1 && armor <= 14)		
			this.armor = armor;
		else
			throw new OutOfAcceptableRange("Armor out of range");
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
	public int getInv() {
		return inv;
	}
	public void setInv(int inv) throws OutOfAcceptableRange {
		if(inv >= -1 && inv <= 6)
			this.inv = inv;
		else 
			throw new OutOfAcceptableRange("Inv out of range ("+inv+")");
	}
	public boolean isFNP() {
		return fnp;
	}
	public void setFNP(boolean fnp) {
		this.fnp = fnp;
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
	public int getCover() {
		if(cover < 0) return 0;		
		return cover;
	}
	public void setCover(int cover) throws OutOfAcceptableRange {
		if(cover >= -1 && cover <= 6)	
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
		if(lightningClaws || (poisoned > 0 && strength >= toughness)) return true;		
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
	public int getAWS() {
		return aWS;
	}
	public void setAWS(int aWS) throws OutOfAcceptableRange {
		if(aWS <= 10 && aWS >= 1)
			this.aWS = aWS;
		else
			throw new OutOfAcceptableRange("Attacker WS out of range");
	}
	public int getTWS() {
		return tWS;
	}
	public void setTWS(int tWS) throws OutOfAcceptableRange {
		if(tWS <= 10 && tWS >= 1)
			this.tWS = tWS;
		else
			throw new OutOfAcceptableRange("Target WS out of range");
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
	public boolean isArmorIgnored() {
		if(isPowerWeapon() && target == FKScenarioTarget.UNARMORED && type == FKScenarioType.CLOSE_COMBAT) return true;
		
		return false;
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

