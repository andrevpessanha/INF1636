package model;

import java.util.Random;

import controller.Game;


public class Dices {
	private static int[] currentRoll;
	private static Random random = new Random(); 
	
	public static void roll() {
		currentRoll = new int[2];
		
		if(!Game.getInstance().isInDebugMode()) {
			currentRoll[0] = (int) ( random.nextInt(6) + 1);
			currentRoll[1] = (int) (random.nextInt(6) + 1);
		}
		else {
			int res = Game.getInstance().showNumIntervalDialog("Selecione o valor do primeiro dado:", "Modo DEBUG", 1, 6);
			currentRoll[0] = (res != -1) ? res : 1; 
			res = Game.getInstance().showNumIntervalDialog("Selecione o valor do segundo dado:", "Modo DEBUG", 1, 6);
			currentRoll[1] = (res != -1) ? res : 1; 
		}
		
	}
	
	public static boolean dicesIguais() {
		return currentRoll[0] == currentRoll[1];
	}
	
	public static int getRollTotal() {
		return currentRoll[0] + currentRoll[1];
	}
	
	public static int[] getCurrentRoll() {
		return currentRoll;
	}
	
}
