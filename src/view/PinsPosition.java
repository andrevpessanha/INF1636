package view;

import java.util.HashMap;
import java.util.Map;

public class PinsPosition {
	private static final Map<Key, PinPosition> positions;
	static {
		int x, y = 0;
		positions = new HashMap<Key, PinPosition>();
		
		for(int i=0; i<6; i++) {  // POS 0 (INICIO)
			x = (i % 2 == 0) ? 30 : 40;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,0), new PinPosition(x, y));
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 1 (Territorio - Curicica)
			positions.put(new Key(i,1), new PinPosition(x, 590));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 2 (Sorte/Reves)
			positions.put(new Key(i,2), new PinPosition(x, 530));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 3 (Empresa - Caça Niquel)
			positions.put(new Key(i,3), new PinPosition(x, 460));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 4 (Territorio - LEME)
			positions.put(new Key(i,4), new PinPosition(x, 390));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 5 (Territorio - Vilar Carioca)
			positions.put(new Key(i,5), new PinPosition(x, 320));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 6 (Sorte/Reves)
			positions.put(new Key(i,6), new PinPosition(x, 250));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 7 (Empresa - Botijão de Gás)
			positions.put(new Key(i,7), new PinPosition(x, 170));
			x -= 10;
		}
		
		x = 50;
		for(int i=0; i<6; i++) { // POS 8 (Territorio - Morro do 18)
			positions.put(new Key(i,8), new PinPosition(x, 110));
			x -= 10;
		}
		
		for(int i=0; i<6; i++) { // POS 9 (PRISÃO)
			x = (i % 2 == 0) ? 30 : 40;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,9), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 10 (Territorio - Guaporé)
			x = (i % 2 == 0) ? 100 : 110;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,10), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 11 (Territorio - Tanque)
			x = (i % 2 == 0) ? 165 : 175;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,11), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 12 (Territorio - Botafogo)
			x = (i % 2 == 0) ? 225 : 235;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,12), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 13 (Sorte/Reves)
			x = (i % 2 == 0) ? 290 : 300;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,13), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 14 (Territorio - Batan)
			x = (i % 2 == 0) ? 360 : 370;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,14), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 15 (Empresa - Transporte Alt)
			x = (i % 2 == 0) ? 420 : 430;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,15), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 16 (Territorio - Barbante)
			x = (i % 2 == 0) ? 490 : 500;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,16), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 17 (Receba $200 - Propinas e Extorsões)
			x = (i % 2 == 0) ? 550 : 560;
			y = (i <= 1) ? 10 : (i <= 3) ? 30 : 50;
			positions.put(new Key(i,17), new PinPosition(x, y));
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 18 (PARADA LIVRE)
			positions.put(new Key(i,18), new PinPosition(x, 30));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 19 (Empresa - Segurança)
			positions.put(new Key(i,19), new PinPosition(x, 110));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 20 (Territorio - Gardenia Azul) 
			positions.put(new Key(i,20), new PinPosition(x, 180));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 21 (Territorio - Caixa D'água) 
			positions.put(new Key(i,21), new PinPosition(x, 250));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 22 (Territorio - Kelsons)
			positions.put(new Key(i,22), new PinPosition(x, 320));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 23 (Sorte/Reves)
			positions.put(new Key(i,23), new PinPosition(x, 390));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) {  // POS 24 (Empresa - Serviço Moto-Táxi)
			positions.put(new Key(i,24), new PinPosition(x, 450));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) { // POS 25 (Territorio - Quintugo)
			positions.put(new Key(i,25), new PinPosition(x, 530));
			x += 10;
		}
		
		x = 610;
		for(int i=0; i<6; i++) { // POS 26 (Territorio - Rio das Pedras)
			positions.put(new Key(i,26), new PinPosition(x, 590));
			x += 10;
		}
		
		for(int i=0; i<6; i++) {  // POS 27 (IR PRISÃO) 
			x = (i % 2 == 0) ? 610 : 620;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,27), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 28 (Territorio - Fubá)
			x = (i % 2 == 0) ? 550 : 560;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,28), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 29 (Sorte/Revés)
			x = (i % 2 == 0) ? 485 : 495;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,29), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 30 (Territorio - Carobinha)
			x = (i % 2 == 0) ? 420 : 430;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,30), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 31 (Empresa - Sinal TV a gato)
			x = (i % 2 == 0) ? 360 : 370;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,31), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 32 (Territorio - Fumacê)
			x = (i % 2 == 0) ? 295 : 305;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,32), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 33 (Sorte/Revés)
			x = (i % 2 == 0) ? 230 : 240;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,33), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 34 (Pague $200 - Custos de Campanha)
			x = (i % 2 == 0) ? 160 : 170;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,34), new PinPosition(x, y));
		}
		
		for(int i=0; i<6; i++) {  // POS 35 (Territorio - Cidade Alta)
			x = (i % 2 == 0) ? 100 : 110;
			y = (i <= 1) ? 650 : (i <= 3) ? 670 : 700;
			positions.put(new Key(i,35), new PinPosition(x, y));
		}
		
	}
	
	public static PinPosition getPosition(int playerID, int positionID) {
		return positions.get(new Key(playerID, positionID));
	}
}
