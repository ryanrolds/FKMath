package com.mobilebuttes.fortyk;

public class FKMath {
	final private double ANYONESIDE = (1.0 / 6);
	final private int TOHITCONVERSION = 7;
	final private int DICEINVERSION = 7;
	final private double ONE = 1.0;
	final private double FACTORTWO = 2.0;
	final private double ZERO = 0.0;
	final private int[][] woundTable = {
			{ 4, 5, 6, 6,-1,-1,-1,-1,-1,-1},
			{ 3, 4, 5, 6, 6,-1,-1,-1,-1,-1},
			{ 2, 3, 4, 5, 6, 6,-1,-1,-1,-1},
			{ 2, 2, 3, 4, 5, 6, 6,-1,-1,-1},
			{ 2, 2, 2, 3, 4, 5, 6, 6,-1,-1},
			{ 2, 2, 2, 2, 3, 4, 5, 6, 6,-1},
			{ 2, 2, 2, 2, 2, 3, 4, 5, 6, 6},
			{ 2, 2, 2, 2, 2, 2, 3, 4, 5, 6},
			{ 2, 2, 2, 2, 2, 2, 2, 3, 4, 5},
			{ 2, 2, 2, 2, 2, 2, 2, 2, 3, 4},
		};
	
	public double getProbability(FKScenario s) {
		double prob = 1.0;
		double toHit = calcToHit(s.getBS(),s.isTWL(),s.isOnHitSuccessRR(),s.isOnHitFailureRR());
		double toWound = calcToWound(s.getStrength(),s.getToughness(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR(),s.isRending(),s.isSniper());
		double saveChance = calcArmorSave(s.getArmor(),s.getAP(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
		double invChance = calcInvSave(s.getInv(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
		double fnpChance = calcFNP(s.isFNP(),s.getAP());
		
		prob = toHit;
		prob = prob * toWound;
		prob = prob * ((saveChance <= invChance) ? saveChance : invChance);
		prob = prob * fnpChance;
		
		if(s.isRending() || s.isSniper()) prob = prob + (toHit * ANYONESIDE * invChance);
		
		
		System.out.println("To Hit("+s.getBS()+"): "+toHit);
		System.out.println("To Wound("+s.getStrength()+","+s.getToughness()+"): "+toWound);
		System.out.println("Rending: "+s.isRending());
		System.out.println("Save("+s.getArmor()+","+s.getAP()+"): "+saveChance);
		System.out.println("Inv("+s.getInv()+"): "+invChance);
		System.out.println("FNP("+s.isFNP()+"): "+fnpChance);
		System.out.println("-----------------");	
		
		return prob;
	}
	
//	private double getNumberOfWounds(int attacks,double prob) {
//		double wounds = attacks * prob;	
//		return wounds;
//	}

	private double calcToHit(int bs,boolean twinLinked,boolean onFailureRR,boolean onSuccessRR) {
		double prob = (DICEINVERSION - (TOHITCONVERSION - bs)) * ANYONESIDE;	
    	if(twinLinked) prob = (ONE - Math.pow((ONE - prob),FACTORTWO));
    	
    	return factorRerolls(prob,onFailureRR,onSuccessRR);
	}

	private double calcToWound(int strength,int toughness,boolean onFailureRR,boolean onSuccessRR,boolean rending,boolean sniper) {
		int diff = woundTable[strength-1][toughness-1];
		if(rending) diff++;
		if(sniper) diff = 5;		
		
		double prob;
		if(diff == -1) 
			prob = ZERO;
		else 
			prob = (DICEINVERSION - diff) * ANYONESIDE;			
		
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}

	private double calcArmorSave(int armor,int weaponAP,boolean onFailureRR,boolean onSuccessRR) {
		if(weaponAP <= armor && weaponAP != -1) return ONE;

		double prob = (DICEINVERSION - armor) * ANYONESIDE;			
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	private double calcInvSave(int inv,boolean onFailureRR,boolean onSuccessRR) {
		if(inv == -1) return ONE;
		
		double prob = (DICEINVERSION - inv) * ANYONESIDE;	
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	private double calcFNP(boolean fnp,int weaponAP) {
		if(fnp == false || (weaponAP < 3 && weaponAP != -1)) return ONE;		
		return 0.5;		
	}
	
	private double factorRerolls(double prob,boolean onFailureRR,boolean onSuccessRR) {
		if(prob == ZERO) return ZERO;
		
    	if(onFailureRR) prob = Math.pow(prob,FACTORTWO); 	
    	if(onSuccessRR) prob = (ONE - Math.pow((ONE - prob),FACTORTWO));
		
		return prob;
	}
}
