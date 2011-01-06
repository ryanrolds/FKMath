package com.mobilebuttes.fortyk;

public class FKUnit {
	protected FKTargetType type = FKTargetType.UNARMORED;
	
	// Target vars
	protected String name = "Target";
	protected int ws = 4;
	protected int bs = 4;
	protected int strength = 4; // Physical strength
	protected int toughness = 4;
	protected int armor = 4;
	protected int inv = -1;
	protected int cover = 0; // TODO - look over this
	protected boolean fnp = false;	
	protected boolean openTopped = false; // TODO - +1 to dmg roll	

	protected boolean onHitFailureRR = false;
	protected boolean onWoundFailureRR = false;
	protected boolean onSaveFailureRR = false;
	protected boolean onInvFailureRR = false;
	protected boolean onHitSuccessRR = false;
	protected boolean onWoundSuccessRR = false;
	protected boolean onSaveSuccessRR = false;
	protected boolean onInvSuccessRR = false;
	
	public FKTargetType getType() {
		return type;
	}
	public void setType(FKTargetType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWS() {
		return ws;
	}
	public void setWS(int ws) throws OutOfAcceptableRange {
		if(ws <= 10 && ws >= 1)
			this.ws = ws;
		else
			throw new OutOfAcceptableRange("Attacker WS out of range");
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
	public int getStrength(FKTargetType target,FKCombatType type) {		
		if(strength < 0) return 0;
		return strength;
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
	public int getArmor(FKTargetType target,boolean lance) {
		if(lance && target == FKTargetType.ARMORED && armor > 12) // BRB p32.2.3 - LANCE
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
	public boolean isOnHitFailureRR() {
		return onHitFailureRR;
	}
	public void setOnHitFailureRR(boolean onHitFailureRR) {
		this.onHitFailureRR = onHitFailureRR;
	}
	public boolean isOnWoundFailureRR(int toughness) {	
		return onWoundFailureRR;
	}
	public void setOnWoundFailureRR(boolean onWoundFailureRR) {
		this.onWoundFailureRR = onWoundFailureRR;
	}
	public boolean isOnSaveFailureRR() {
		return onSaveFailureRR;
	}
	public void setOnSaveFailureRR(boolean onSaveFailureRR) {
		this.onSaveFailureRR = onSaveFailureRR;
	}
	public boolean isOnInvFailureRR() {
		return onInvFailureRR;
	}
	public void setOnInvFailureRR(boolean onInvFailureRR) {
		this.onInvFailureRR = onInvFailureRR;
	}
	public boolean isOnHitSuccessRR() {
		return onHitSuccessRR;
	}
	public void setOnHitSuccessRR(boolean onHitSuccessRR) {
		this.onHitSuccessRR = onHitSuccessRR;
	}	
	public boolean isOnWoundSuccessRR() {
		return onWoundSuccessRR;
	}
	public void setOnWoundSuccessRR(boolean onWoundSuccessRR) {
		this.onWoundSuccessRR = onWoundSuccessRR;
	}	
	public boolean isOnSaveSuccessRR() {
		return onSaveSuccessRR;
	}
	public void setOnSaveSuccessRR(boolean onSaveSuccessRR) {
		this.onSaveSuccessRR = onSaveSuccessRR;
	}	
	public boolean isOnInvSuccessRR() {
		return onInvSuccessRR;
	}
	public void setOnInvSuccessRR(boolean onInvSuccessRR) {
		this.onInvSuccessRR = onInvSuccessRR;
	}
}