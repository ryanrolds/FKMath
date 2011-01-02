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
	
	public void processScenario(FKScenario s) {
		double prob = 1.0;
		
		if(s.getType() == FKScenarioType.SHOOTING) {
			double hitChance = calcToHit(s.getBS(),s.isTWL(),s.isOnHitSuccessRR(),s.isOnHitFailureRR());
			
			if(s.getTarget() == FKScenarioTarget.UNARMORED) {
				double toWound = calcToWound(s.getStrength(),s.getToughness(),s.isOnWoundSuccessRR(),s.isOnWoundFailureRR(),s.isRending(),s.isSniper());
				double saveChance = calcArmorSave(s.getArmor(),s.getAP(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
				double coverChance = calcCover(s.getCover());
				double invChance = calcInvSave(s.getInv(),s.isOnSaveSuccessRR(),s.isOnSaveFailureRR());
				double fnpChance = calcFNP(s.isFNP(),s.getAP());
				
				double bestSave = saveChance;
				if(bestSave > coverChance) bestSave = coverChance;
				if(bestSave > invChance) bestSave = invChance;
				
				prob = hitChance;
				prob = prob * toWound;
				prob = prob * bestSave;
				prob = prob * fnpChance;
				
				if(s.isRending()) prob = prob + (hitChance * ANYONESIDE * invChance);			
			} else { // TODO Shooting Armored Target				
				double glanceChance = calcToGlance(s.getStrength(),s.getArmor(),s.isMelta(),s.isOrdnance(),s.isRending());
				double penChance = calcToPen(s.getStrength(),s.getArmor(),s.isMelta(),s.isOrdnance(),s.isRending());
				double coverChance = calcCover(s.getCover());
			
				prob = hitChance * (glanceChance + penChance) * coverChance;				
			}			
		} else  {
			// TODO Close Combat
		}
		
		s.setProbability(prob);
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
	
	private double calcCover(int cover) { //TODO - make sure that it doesn't need to be inverted
		if(cover == 0) return ONE;
		
		return (DICEINVERSION - cover) * ANYONESIDE;
	}
	
	private double calcFNP(boolean fnp,int weaponAP) {
		if(fnp == false || (weaponAP < 3 && weaponAP != -1)) return ONE;		
		return 0.5;		
	}
	
	private double calcToGlance(int str,int armor,boolean melta,boolean ord,boolean rending) {
		int diff = armor - str;
		int dice = 0; // This is for the outcome table, index 0 is one dice, index 2 is two dice
		
		if(melta) dice++; // Meltas get an extra dice
		
		if(diff < (dice + 1)) return ZERO; // If you can't roll the exact number needed then it will always be a pen hit.
		
		// If the difference is larger when what be rolled it will always fail
		if(diff > 12 || (diff > 9 && !melta) || (diff > 6 && !rending)) return ZERO;
			
		if(!melta && rending && diff > 6) { 
			return ANYONESIDE * (ANYONESIDE * 2.0); // For getting a 6 then getting 1-3 on the d3
		} 	
		
		if(ord && !melta) {
			// Get the combination the 2d6 pick highest table;
			int combinations = twoDHighest[diff][0];
			// Use the combination to work out the odd of getting a glance
			return combinations * (ONE / twoDHighest[0][0]);
		} 
		
		// We want the # of combinations for rolling difference 
		int combinations = outcomes[diff][dice] - outcomes[diff+1][dice];
		
		//Take the # of combinations and multiple it by the chance of getting one combinations
		return combinations * (ONE / outcomes[0][dice]);
	}
	
	private double calcToPen(int str,int armor,boolean melta,boolean ord,boolean rending) {
		int diff = armor - str;
		int dice = 0; // This is for the outcome table, index 0 is one dice, index 2 is two dice
		
		if(melta) dice++; // Meltas get an extra dice
		
		if(diff < (dice + 1)) return ONE; // If you can't roll the exact number needed then it will always be a pen hit.
		
		// If the difference is larger when what be rolled it will always fail
		if(diff > 12 || (diff > 9 && !melta) || (diff > 6 && !rending)) return ZERO;			
		
		if(!melta && rending && diff >= 6) { 
			int inverse = 4 - ((diff - 6) + 1);			
			if(inverse < 1) return ZERO;			
			return ANYONESIDE * (inverse * (ANYONESIDE * 2.0)); // For getting a 6 then getting 1-3 on the d3
		}
		
		if(ord && !melta) {
			// Get the combination the 2d6 pick highest table;
			int combinations = twoDHighest[diff+1][1];
			// Use the combination to work out the odd of getting a pen
			return combinations * (ONE / twoDHighest[0][1]);
		} 
		
		// We want the # of combinations for rolling difference 
		int combinations = outcomes[diff+1][dice];		
		//Take the # of combinations and multiple it by the chance of getting any acceptable combination
		return combinations * (ONE/ outcomes[0][dice]);
	}
		
	private double factorRerolls(double prob,boolean onFailureRR,boolean onSuccessRR) {
		if(prob == ZERO) return ZERO;
		
    	if(onFailureRR) prob = Math.pow(prob,FACTORTWO); 	
    	if(onSuccessRR) prob = (ONE - Math.pow((ONE - prob),FACTORTWO));
		
		return prob;
	}
}