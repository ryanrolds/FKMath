package com.mobilebuttes.fortyk.test;

import com.mobilebuttes.fortyk.FKMath;
import com.mobilebuttes.fortyk.FKScenario;
import com.mobilebuttes.fortyk.FKScenarioTarget;
import com.mobilebuttes.fortyk.FKScenarioType;
import com.mobilebuttes.fortyk.OutOfAcceptableRange;

class UnitTest {
	public static void main(String[] args) throws OutOfAcceptableRange {
		FKMath calc = new FKMath();
		
		final int numTests = 32;
		FKScenario[] scenario = new FKScenario[numTests];
		double[] expected = new double[numTests];
		boolean[] passed = new boolean[numTests];
		
		// Test 1 - DEFAULT
		scenario[0] = new FKScenario();
		expected[0] = 0.16666666666666666;
		// Test 2 - FNP
		scenario[1] = new FKScenario();
		scenario[1].setFNP(true);
		expected[1] = 0.08333333333333333;
		// Test 3 - TWL
		scenario[2] = new FKScenario();
		scenario[2].setTWL(true);
		expected[2] = 0.2222222222222222;
		// Test 4 - TWL
		scenario[3] = new FKScenario();
		scenario[3].setAP(2);
		expected[3] = 0.3333333333333333;
		// Test 5 - AP 2 + FNP
		scenario[4] = new FKScenario();
		scenario[4].setAP(2);
		scenario[4].setFNP(true);
		expected[4] = 0.3333333333333333;
		// Test 6 - AP 2 + FNP
		scenario[5] = new FKScenario();
		scenario[5].setAP(2);
		scenario[5].setFNP(true);
		scenario[5].setInv(4);
		expected[5] = 0.16666666666666666;
		// Test 7 - Rending
		scenario[6] = new FKScenario();
		scenario[6].setRending(true);
		expected[6] = 0.2222222222222222;
		// Test 8 - Sniper
		scenario[7] = new FKScenario();
		scenario[7].setSniper(true);
		expected[7] = 0.2222222222222222;
		// Test 9 - Sniper + MC
		scenario[8] = new FKScenario();
		scenario[8].setSniper(true);
		scenario[8].setToughness(6);
		expected[8] = 0.2222222222222222;
		// Test 10 - Rending + MC
		scenario[9] = new FKScenario();
		scenario[9].setRending(true);
		scenario[9].setToughness(6);
		expected[9] = 0.1111111111111111;
		// Test 10 - Bad armor w/ cover
		scenario[10] = new FKScenario();
		scenario[10].setArmor(6);
		scenario[10].setCover(4);
		expected[10] = 0.16666666666666666;
		// Test 11 - Vehicle - Armor 12 - S4 weapon
		scenario[11] = new FKScenario();
		scenario[11].setTarget(FKScenarioTarget.ARMORED);
		scenario[11].setArmor(14);
		expected[11] = 0.0;
		// Test 12 - Vehicle - Armor 14 - S 10 weapon
		scenario[12] = new FKScenario();
		scenario[12].setTarget(FKScenarioTarget.ARMORED);
		scenario[12].setArmor(14);
		scenario[12].setStrength(10);
		expected[12] = 0.3333333333333333;
		// Test 13 - Vehicle - Armor 14 - S 10 weapon
		scenario[12] = new FKScenario();
		scenario[12].setTarget(FKScenarioTarget.ARMORED);
		scenario[12].setArmor(14);
		scenario[12].setStrength(10);
		expected[12] = 0.3333333333333333;
		// Test 14 - Vehicle - Armor 14 - S10 weapon
		scenario[13] = new FKScenario();
		scenario[13].setTarget(FKScenarioTarget.ARMORED);
		scenario[13].setArmor(10);
		scenario[13].setStrength(10);
		expected[13] = 0.6666666666666666;
		// Test 15 - Vehicle - Armor 14 - S10 weapon
		scenario[14] = new FKScenario();
		scenario[14].setTarget(FKScenarioTarget.ARMORED);
		scenario[14].setArmor(11);
		scenario[14].setStrength(10);
		expected[14] = 0.6666666666666665;
		// Test 16 - Vehicle - Armor 14 - S10 weapon w/ melta
		scenario[15] = new FKScenario();
		scenario[15].setTarget(FKScenarioTarget.ARMORED);
		scenario[15].setArmor(14);
		scenario[15].setStrength(10);
		scenario[15].setMelta(true);
		expected[15] = 0.611111111111111;
		// Test 17 - Vehicle - Armor 14 - S8 weapon w/ melta
		scenario[16] = new FKScenario();
		scenario[16].setTarget(FKScenarioTarget.ARMORED);
		scenario[16].setArmor(14);
		scenario[16].setStrength(8);
		scenario[16].setMelta(true);
		expected[16] = 0.4814814814814814;
		// Test 18 - Vehicle - Armor 14 - S10 weapon w/ ord
		scenario[17] = new FKScenario();
		scenario[17].setTarget(FKScenarioTarget.ARMORED);
		scenario[17].setArmor(14);
		scenario[17].setStrength(10);
		scenario[17].setOrdnance(true);
		expected[17] = 0.5;
		// Test 19 - Vehicle - Armor 14 - S10 weapon /w rending
		scenario[18] = new FKScenario();
		scenario[18].setTarget(FKScenarioTarget.ARMORED);
		scenario[18].setArmor(14);
		scenario[18].setStrength(10);
		scenario[18].setRending(true);
		expected[18] = 0.3333333333333333;
		// Test 20 - Vehicle - Armor 12 - S5 weapon /w rending
		scenario[19] = new FKScenario();
		scenario[19].setTarget(FKScenarioTarget.ARMORED);
		scenario[19].setArmor(12);
		scenario[19].setStrength(5);
		scenario[19].setRending(true);
		expected[19] = 0.1111111111111111;
		// Test 21 - CC - Basic
		scenario[20] = new FKScenario();
		scenario[20].setType(FKScenarioType.CLOSE_COMBAT);
		expected[20] = 0.125;
		// Test 22 - CC - Basic w/ PF
		scenario[21] = new FKScenario();
		scenario[21].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[21].setPowerFist(true);
		expected[21] = 0.41666666666666663;
		// Test 23 - CC - Basic w/ PF and Inv 3+
		scenario[22] = new FKScenario();
		scenario[22].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[22].setPowerFist(true);
		scenario[22].setInv(3);
		scenario[22].setFNP(true);
		expected[22] = 0.1388888888888889;
		// Test 24 -
		scenario[23] = new FKScenario();
		scenario[23].setAP(2);
		scenario[23].setInv(3);
		expected[23] = 0.11111111111111112;
		// Test 25 -
		scenario[24] = new FKScenario();
		scenario[24].setAP(2);
		scenario[24].setCover(4);
		expected[24] = 0.16666666666666666;
		// Test 26 -
		scenario[25] = new FKScenario();
		scenario[25].setArmor(2);
		scenario[25].setCover(4);
		expected[25] = 0.05555555555555558;
		// Test 27 - CC - Basic w/ WB
		scenario[26] = new FKScenario();
		scenario[26].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[26].setWitchBlade(true);
		expected[26] = 0.20833333333333331;
		// Test 28 - CC - Basic w/ Poisoned
		scenario[27] = new FKScenario();
		scenario[27].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[27].setPoisoned(4);
		expected[27] = 0.1875;
		// Test 29 - CC - Poisoned
		scenario[28] = new FKScenario();
		scenario[28].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[28].setPoisoned(4);	
		scenario[28].setToughness(5);
		expected[28] = 0.125;
		// Test 30 - CC - Thunderhammer
		scenario[29] = new FKScenario();
		scenario[29].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[29].setThunderHammer(true);
		expected[29] = 0.41666666666666663;
		// Test 30 - CC - Lighting claws
		scenario[30] = new FKScenario();
		scenario[30].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[30].setLightningClaws(true);
		expected[30] = 0.375;
		// Test 30 - CC - Lighting claws and inv
		scenario[31] = new FKScenario();
		scenario[31].setType(FKScenarioType.CLOSE_COMBAT);
		scenario[31].setLightningClaws(true);
		scenario[31].setInv(3);
		expected[31] = 0.125;
		
		for(int i = 0;i < scenario.length; i++) {
			System.out.println();
			System.out.println("Test "+(i+1));
			System.out.println("-----------------");
			calc.processScenario(scenario[i]);
			System.out.print(scenario[i].getProbability());
			if(scenario[i].getProbability() == expected[i]) {
				passed[i] = true;			
			} else {
				passed[i] = false;
			}
			
			System.out.println((passed[i]) ? " Passed" : " Failed");
		}
		
		System.out.println("\n");
		
		boolean allTestsPassed = true;
		for(int i = 0;i < scenario.length; i++) {
			if(passed[i] == false) allTestsPassed = false;
			System.out.println("Test "+(i+1)+" "+((passed[i]) ? "Passed" : "Failed"));	
		}
		
		System.out.println("-----------------");
		System.out.println((allTestsPassed) ? "ALL TESTS PASSED" : "!!!SOME TESTS FAILED!!!");				
	}
}