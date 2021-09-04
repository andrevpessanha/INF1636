package model;

import java.util.ArrayList;

import tools.PositionType;
import tools.UIColors;

public class BoardPositions {
	private static ArrayList<Position> positions;
	
	public static void criaPositions() {
		positions = new ArrayList<Position>();
		positions.add(new Position(0, PositionType.INICIO));
		positions.add(new Territorio(1, "CURICICA", UIColors.ORANGE, 160, 200, 200, new int[] {28,150,450,1000,1200}, 1400));
		positions.add(new Position(2, PositionType.CARD));
		positions.add(new Empresa(3, "MÁQUINAS DE CAÇA-NÍQUEL", 50, 100));
		positions.add(new Territorio(4, "LEME", UIColors.RED, 150, 200, 200, new int[] {26,130,390,900,1100}, 1275));
		positions.add(new Territorio(5, "VILAR CARIOCA", UIColors.YELLOW, 110, 150, 150, new int[] {18,90,250,700,875}, 1050));
		positions.add(new Position(6, PositionType.CARD));
		positions.add(new Empresa(7, "BOTIJÃO DE GÁS", 40, 75));
		positions.add(new Territorio(8, "MORRO DO 18", UIColors.PURPLE, 70, 100, 100, new int[] {10,50,150,450,625}, 750));
		positions.add(new Position(9, PositionType.PRISAO));
		positions.add(new Territorio(10, "GUAPORÉ", UIColors.BLUE, 30, 50, 50, new int[] {4,20,60,180,320}, 450));
		positions.add(new Territorio(11, "TANQUE", UIColors.ORANGE, 90, 100, 100, new int[] {14,70,200,550,750}, 950));
		positions.add(new Territorio(12, "BOTAFOGO", UIColors.RED, 150, 200, 200, new int[] {26,130,390,900,1100}, 1275));
		positions.add(new Position(13, PositionType.CARD));
		positions.add(new Territorio(14, "BATAN", UIColors.GREEN, 130, 150, 150, new int[] {22,110,330,800,975}, 1150));
		positions.add(new Empresa(15, "TRANSPORTE ALTERNATIVO", 50, 100));
		positions.add(new Territorio(16, "BARBANTE", UIColors.YELLOW, 110, 150, 150, new int[] {18,90,250,700,875}, 1050));
		positions.add(new Position(17, PositionType.ESPECIAL));
		positions.add(new Position(18, PositionType.PARADALIVRE));
		positions.add(new Empresa(19, "SEGURANÇA PATRIMONIAL", 50, 100));
		positions.add(new Territorio(20, "GARDÊNIA AZUL", UIColors.ORANGE, 90, 100, 100, new int[] {14,70,200,550,750}, 950));
		positions.add(new Territorio(21, "CAIXA D'ÁGUA", UIColors.PURPLE, 70, 100, 100, new int[] {10,50,150,450,625}, 750));
		positions.add(new Territorio(22, "KELSON'S", UIColors.GREEN, 60, 50, 50, new int[] {8,40,100,300, 450}, 600));
		positions.add(new Position(23, PositionType.CARD));
		positions.add(new Empresa(24, "SERVIÇO DE MOTO-TÁXI", 50, 100));
		positions.add(new Territorio(25, "QUITUNGO", UIColors.BLUE, 30, 50, 50, new int[] {4,20,60,180,320}, 450));
		positions.add(new Territorio(26, "RIO DAS PEDRAS", UIColors.RED, 130, 150, 150, new int[] {22,110,330,800, 975}, 1150));
		positions.add(new Position(27, PositionType.IRPRISAO));
		positions.add(new Territorio(28, "FUBÁ", UIColors.PURPLE, 80, 100, 100, new int[] {12,60,180,500, 700}, 900));
		positions.add(new Position(29, PositionType.CARD));
		positions.add(new Territorio(30, "CAROBINHA", UIColors.YELLOW, 120, 150, 150, new int[] {20,100,300,750, 925}, 1100));
		positions.add(new Empresa(31, "SINAL DE TV À GATO", 40, 75));
		positions.add(new Territorio(32, "FUMACÊ", UIColors.GREEN, 50, 50, 50, new int[] {6,30,90,270,400}, 500));
		positions.add(new Position(33, PositionType.CARD));
		positions.add(new Position(34, PositionType.ESPECIAL));
		positions.add(new Territorio(35, "CIDADE ALTA", UIColors.BLUE, 50, 50, 50, new int[] {6,30,90,270,400},500));
	}
	
	public static Position getPosition(int positionID) {
		return positions.get(positionID);
	}
}
