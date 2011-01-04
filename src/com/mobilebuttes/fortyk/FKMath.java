package com.mobilebuttes.fortyk;

public class FKMath {
	final private double ANYONESIDE = (1.0 / 6);
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
	
	final private int[][] outcomes = { // possible combinations of >= to a number with 1d6, 2d6
			{ 6,36},
			{ 6,36}, // 1
			{ 5,36}, // 2
			{ 4,35}, // 3
			{ 3,33}, // 4
			{ 2,30}, // 5
			{ 1,26}, // 6
			{ 0,21}, // 7
			{ 0,15}, // 8
			{ 0,10}, // 9
			{ 0, 6}, // 10
			{ 0, 3}, // 11
			{ 0, 1}  // 12		
		};
	
	final private int[][] twoDHighest = { // Odds of that number with 2d6 pick highest - 1st column if for glancing, 2nd is for pen
			{36,36}, 
			{ 1,36}, // 1
			{ 3,35}, // 2
			{ 5,32}, // 3
			{ 7,27}, // 4
			{ 9,20}, // 5
			{11,11}  // 6
		};
	
	final private int[][] assaultToHit = {
			{4,4,5,5,5,5,5,5,5,5},
			{3,4,4,4,5,5,5,5,5,5},
			{3,3,4,4,4,4,5,5,5,5},
			{3,3,3,4,4,4,4,4,5,5},
			{3,3,3,3,4,4,4,4,4,4},
			{3,3,3,3,3,4,4,4,4,4},
			{3,3,3,3,3,3,4,4,4,4},
			{3,3,3,3,3,3,3,4,4,4},
			{3,3,3,3,3,3,3,3,4,4},
			{3,3,3,3,3,3,3,3,3,4}
		};
	
	public void processScenario(FKScenario s) {
		double prob = 1.0;
		
		if(s.getType() == FKScenarioType.SHOOTING) {
			double hitChance = calcToHit(s.getBS(),s.isTWL(),s.isOnHitSuccessRR(),s.isOnHitFailureRR());
			
			if(s.getTarget() == FKScenarioTarget.UNARMORED) {
				double toWound = calcToWound(s.getStrength(),s.getToughness(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR(),s.isRending(),s.isSniper());
				double saveChance = calcArmorSave(s.getArmor(),s.getAP(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR(),s.isArmorIgnored());
				double coverChance = calcCover(s.getCover());
				double invChance = calcInvSave(s.getInv(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
				double fnpChance = calcFNP(s.isFNP(),s.getAP(),s.isArmorIgnored());
				
				double bestSave = saveChance;
				if(bestSave > coverChance) bestSave = coverChance;
				if(bestSave > invChance) bestSave = invChance;
				
				prob = hitChance * toWound * bestSave * fnpChance;
				
				if(s.isRending()) prob = prob + (hitChance * ANYONESIDE * ((coverChance < invChance) ? coverChance : invChance));			
			} else {			
				double glanceChance = calcToGlance(s.getStrength(),s.getArmor(),s.isMelta(),s.isOrdnance(),s.isRending(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR());
				double penChance = calcToPen(s.getStrength(),s.getArmor(),s.isMelta(),s.isOrdnance(),s.isRending(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR());
				double coverChance = calcCover(s.getCover());
				
		    	processVehicleDamage(s,hitChance*glanceChance*coverChance,true);
		    	processVehicleDamage(s,hitChance*penChance*coverChance,false);		
		    	
				prob = hitChance * (glanceChance + penChance) * coverChance;	
				s.setNoEffectChance(ONE - prob);
			}			
		} else  { // TODO Close Combat
			if(s.getTarget() == FKScenarioTarget.UNARMORED) { // TODO bug in rending calculation.
				double hitChance = calcToAssaultHit(s.getAWS(),s.getTWS(),s.isOnHitSuccessRR(),s.isOnHitFailureRR());
				double toWound = calcToAssaultWound(s.getStrength(),s.getToughness(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR(),s.isRending(),s.isWitchBlade(),s.getPoisoned());				
				double saveChance = calcArmorSave(s.getArmor(),s.getAP(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR(),s.isArmorIgnored());
				double coverChance = calcCover(s.getCover());
				double invChance = calcInvSave(s.getInv(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
				double fnpChance = calcFNP(s.isFNP(),s.getAP(),s.isArmorIgnored());
				
				double bestSave = saveChance;
				if(bestSave > coverChance) bestSave = coverChance;
				if(bestSave > invChance) bestSave = invChance;				
				
				prob = hitChance * toWound * bestSave * fnpChance;
				
				if(s.isRending()) prob += hitChance * ANYONESIDE * invChance;
			} else {	
				
			}
		}
		
		s.setProbability(prob);
	}
	
	private void processVehicleDamage(FKScenario s,double chance,boolean glancing) {
    	int shift = 0;  
    	double sixth = chance / 6.0;    	
    	
    	if(glancing) shift -= 2; // Glancing hits are -2 to v.damage
    	if(s.isOpenTopped()) shift += 2; // Open topped is +2 to v. damage     	
    	if(s.getAP() == 1) // AP1 is +1 to v. damage
    		shift++;
    	else if(s.getAP() < 1) // AP- is -1 to v. damage
    		shift--;    	  	
		
    	switch(shift) { // TODO I want to make this cleaner
			case 5:    	
				s.setExplodeChance(s.getExplodeChance() + (6.0 * sixth));
				break;
			case 4:    	
				s.setWreakedChance(s.getWreakedChance() + sixth);
				s.setExplodeChance(s.getExplodeChance() + (5.0 * sixth));
				break;
			case 3:    	
				s.setImmobileChance(s.getImmobileChance() + sixth);
				s.setWreakedChance(s.getWreakedChance() + sixth);
				s.setExplodeChance(s.getExplodeChance() + (4.0 * sixth));
				break;
			case 2:			
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				s.setImmobileChance(s.getImmobileChance() + sixth);
				s.setWreakedChance(s.getWreakedChance() + sixth);
				s.setExplodeChance(s.getExplodeChance() + (3.0 * sixth));
				break;
			case 1:    	
				s.setStunnedChance(s.getStunnedChance() + sixth);
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				s.setImmobileChance(s.getImmobileChance() + sixth);
				s.setWreakedChance(s.getWreakedChance() + sixth);
				s.setExplodeChance(s.getExplodeChance() + (2.0 * sixth));
				break;
			case -1:
				s.setShakenChance(s.getShakenChance() + (2.0 * sixth));
				s.setStunnedChance(s.getStunnedChance() + sixth);
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				s.setImmobileChance(s.getImmobileChance() + sixth);
				s.setWreakedChance(s.getWreakedChance() + sixth);
				break;
			case -2:
				s.setShakenChance(s.getShakenChance() + (3.0 * sixth));
				s.setStunnedChance(s.getStunnedChance() + sixth);
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				s.setImmobileChance(s.getImmobileChance() + sixth);
				break;
			case -3:
				s.setShakenChance(s.getShakenChance() + (4.0 * sixth));
				s.setStunnedChance(s.getStunnedChance() + sixth);
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				break;
			case -4:
				s.setShakenChance(s.getShakenChance() + (5.0 * sixth));
				s.setStunnedChance(s.getStunnedChance() + sixth);
				break;
			case -5:
				s.setShakenChance(s.getShakenChance() + (6.0 * sixth));
				break;
			case 0:
			default:
				s.setShakenChance(s.getShakenChance() + sixth);
				s.setStunnedChance(s.getStunnedChance() + sixth);
				s.setWeaponDestroyedChance(s.getWeaponDestroyedChance() + sixth);
				s.setImmobileChance(s.getImmobileChance() + sixth);
				s.setWreakedChance(s.getWreakedChance() + sixth);
				s.setExplodeChance(s.getExplodeChance() + sixth);  
    	}
	}

	private double calcToHit(int bs,boolean twinLinked,boolean onFailureRR,boolean onSuccessRR) {
		double prob = bs * ANYONESIDE;
    	if(twinLinked) prob = (ONE - Math.pow((ONE - prob),FACTORTWO));
    	
    	return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	private double calcToAssaultHit(int aWS,int tWS,boolean onFailureRR,boolean onSuccessRR) {
		int needToHit = assaultToHit[aWS-1][tWS-1];
		double prob = (DICEINVERSION - needToHit) * ANYONESIDE;
		
    	return factorRerolls(prob,onFailureRR,onSuccessRR);
	}

	private double calcToWound(int strength,int toughness,boolean onFailureRR,boolean onSuccessRR,boolean rending,boolean sniper) {
		int diff = woundTable[strength-1][toughness-1];
		
		if(sniper) diff = 4;
		
		if(diff == -1) return ZERO;
		
		if(rending) diff++;
		
		double prob;
		if(diff == -1) 
			prob = ZERO;
		else 
			prob = (DICEINVERSION - diff) * ANYONESIDE;			
		
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	//s.getStrength(),s.getToughness(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR(),s.isRending(),s.isWitchBlade(),s.getPoisoned()
	private double calcToAssaultWound(int strength,int toughness,boolean onFailureRR,boolean onSuccessRR,boolean rending, boolean witchBlade, int poisoned) {
		int diff = woundTable[strength-1][toughness-1];				
		if(poisoned > 0) diff = poisoned;
		if(witchBlade) diff = 2;
		
		if(diff == -1) return ZERO;
		if(rending) diff++;
		
		double prob;
		if(diff == -1) 
			prob = ZERO;
		else 
			prob = (DICEINVERSION - diff) * ANYONESIDE;			
		
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}

	private double calcArmorSave(int armor,int weaponAP,boolean onFailureRR,boolean onSuccessRR,boolean armorIgnored) {
		if((weaponAP > 0 && weaponAP <= armor) || armor < 2 || armorIgnored) return ONE;

		double prob = ONE - ((DICEINVERSION - armor) * ANYONESIDE);			
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	private double calcInvSave(int inv,boolean onFailureRR,boolean onSuccessRR) {
		if(inv <= 1) return ONE;
		if(inv > 6) return ONE;
		
		double prob = ONE - ((DICEINVERSION - inv) * ANYONESIDE);	
		return factorRerolls(prob,onFailureRR,onSuccessRR);
	}
	
	private double calcCover(int cover) { //TODO - make sure that it doesn't need to be inverted
		if(cover == 0) return ONE;
		
		return ONE - ((DICEINVERSION - cover) * ANYONESIDE);
	}
	
	private double calcFNP(boolean fnp,int weaponAP,boolean armorIgnored) {
		if(fnp == false || (weaponAP >= 1 && weaponAP <= 2) || armorIgnored) return ONE;
		return 0.5;
	}
	
	private double calcToGlance(int str,int armor,boolean melta,boolean ord,boolean rending,boolean onFailureRR,boolean onSuccessRR) {
		int diff = armor - str;
		int dice = 0; // This is for the outcome table, index 0 is one dice, index 2 is two dice
		
		if(melta) dice++; // Meltas get an extra dice
		
		if(diff < (dice + 1)) return ZERO; // If you can't roll the exact number needed then it will always be a pen hit.
		
		// If the difference is larger when what be rolled it will always fail
		if(diff > 12 || (diff > 9 && !melta) || (diff > 6 && !rending && !melta)) return ZERO;
			
		if(!melta && rending && diff > 6) { 
			return factorRerolls((ANYONESIDE * (ANYONESIDE * 2.0)),onFailureRR,onSuccessRR); // For getting a 6 then getting 1-3 on the d3
		} 	
		
		if(ord && !melta) {
			// Get the combination the 2d6 pick highest table;
			int combinations = twoDHighest[diff][0];
			// Use the combination to work out the odd of getting a glance
			return factorRerolls((combinations * (ONE / twoDHighest[0][0])),onFailureRR,onSuccessRR);
		} 
		
		// We want the # of combinations for rolling difference 
		int combinations = outcomes[diff][dice] - outcomes[diff+1][dice];
		
		//Take the # of combinations and multiple it by the chance of getting one combinations
		return factorRerolls((combinations * (ONE / outcomes[0][dice])),onFailureRR,onSuccessRR);
	}
	
	private double calcToPen(int str,int armor,boolean melta,boolean ord,boolean rending,boolean onFailureRR,boolean onSuccessRR) {
		int diff = armor - str;
		int dice = 0; // This is for the outcome table, index 0 is one dice, index 2 is two dice
		
		if(melta) dice++; // Meltas get an extra dice
		
		if(diff < (dice + 1)) return ONE; // If you can't roll the exact number needed then it will always be a pen hit.
		
		// If the difference is larger when what be rolled it will always fail
		if(diff > 12 || (diff > 9 && !melta) || (diff > 6 && !rending && !melta)) return ZERO;			
		
		if(!melta && rending && diff >= 6) { 
			int inverse = 4 - ((diff - 6) + 1);			
			if(inverse < 1) return ZERO;			
			return factorRerolls((ANYONESIDE * (inverse * (ANYONESIDE * 2.0))),onFailureRR,onSuccessRR); // For getting a 6 then getting 1-3 on the d3
		}
		
		if(ord && !melta) {
			// Get the combination the 2d6 pick highest table;
			int combinations = twoDHighest[diff+1][1];
			// Use the combination to work out the odd of getting a pen
			return factorRerolls((combinations * (ONE / twoDHighest[0][1])),onFailureRR,onSuccessRR);
		} 
		
		// We want the # of combinations for rolling difference 
		int combinations = outcomes[diff+1][dice];		
		//Take the # of combinations and multiple it by the chance of getting any acceptable combination
		return factorRerolls((combinations * (ONE / outcomes[0][dice])),onFailureRR,onSuccessRR);
	}
		
	private double factorRerolls(double prob,boolean onFailureRR,boolean onSuccessRR) {
		if(prob == ZERO) return ZERO;
		
    	if(onFailureRR) prob = Math.pow(prob,FACTORTWO); 	
    	if(onSuccessRR) prob = (ONE - Math.pow((ONE - prob),FACTORTWO));
		
		return prob;
	}
}