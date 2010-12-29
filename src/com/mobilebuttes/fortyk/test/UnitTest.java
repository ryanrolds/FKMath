package com.mobilebuttes.fortyk.test;

import com.mobilebuttes.fortyk.FKMath;
import com.mobilebuttes.fortyk.FKScenario;
import com.mobilebuttes.fortyk.OutOfAcceptableRange;

class UnitTest {
	public static void main(String[] args) throws OutOfAcceptableRange {
		FKMath calc = new FKMath();
		
		final int numTests = 10;
		FKScenario[] scenario = new FKScenario[numTests];
		double[] expected = new double[numTests];
		double[] results = new double[numTests];
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
		
		for(int i = 0;i < scenario.length; i++) {
			System.out.println();
			System.out.println("Test "+(i+1));
			System.out.println("-----------------");
			results[i] = calc.getProbability(scenario[i]);
			System.out.print(results[i]+" ");
			if(results[i] == expected[i]) {
				passed[i] = true;			
			} else {
				passed[i] = false;
			}
			
			System.out.println((passed[i]) ? " Passed" : "Failed");
		}
		
		System.out.println();
		System.out.println();
		
		boolean allTestsPassed = true;
		for(int i = 0;i < scenario.length; i++) {
			if(passed[i] == false) allTestsPassed = false;
			System.out.println("Test "+(i+1)+" "+((passed[i]) ? "Passed" : "Failed"));	
		}
		
		System.out.println("-----------------");
		System.out.println((allTestsPassed) ? "ALL TESTS PASSED" : "SOME TESTS FAILED");				
	}
}