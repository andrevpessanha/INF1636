package model;

import java.util.ArrayList;
import java.util.Random;

import tools.CardType;

public class Cards {
	private static Random random = new Random();
	private static ArrayList<Card> cards;
	
	public static void criaCards() {
		cards = new ArrayList<Card>();
		cards.add(new Card(0, CardType.REVES, 25, null));
		cards.add(new Card(1, CardType.REVES, 45, null));
		cards.add(new Card(2, CardType.REVES, 45, null));
		cards.add(new Card(3, CardType.REVES, 30, null));
		cards.add(new Card(4, CardType.REVES, 40, null));
		cards.add(new Card(5, CardType.REVES, 50, null));
		cards.add(new Card(6, CardType.REVES, 100, null));
		cards.add(new Card(7, CardType.REVES, 50, null));
		cards.add(new Card(8, CardType.REVES, 30, null));
		cards.add(new Card(9, CardType.REVES, 30, null));
		cards.add(new Card(10, CardType.REVES, 25, null));
		cards.add(new Card(11, CardType.REVES, 0, 9));  // Ir prisão
		cards.add(new Card(12, CardType.REVES, 100, null));
		cards.add(new Card(13, CardType.REVES, 50, null));
		cards.add(new Card(14, CardType.REVES, 15, null));
		cards.add(new Card(15, CardType.SORTE, 100, null));
		cards.add(new Card(16, CardType.SORTE, 50, null));
		cards.add(new Card(17, CardType.SORTE, 50, null));
		cards.add(new Card(18, CardType.SORTE, 20, null));
		cards.add(new Card(19, CardType.SORTE, 25, null));
		cards.add(new Card(20, CardType.SORTE, 200, 0)); // Ir Inicio
		cards.add(new Card(21, CardType.SORTE, 100, null));
		cards.add(new Card(22, CardType.SORTE, 100, null));
		cards.add(new Card(23, CardType.SORTE, 45, null));
		cards.add(new Card(24, CardType.SORTE, 50, null));
		cards.add(new Card(25, CardType.SORTE, 200, null));
		cards.add(new Card(26, CardType.SORTE, 150, null));
		cards.add(new Card(27, CardType.SORTE, 80, null));
		cards.add(new Card(28, CardType.SORTE, 100, null));
		cards.add(new Card(29, CardType.SORTE, 0, null)); // Sair da prisão
	}
	
	public static Card retiraCard() {
		int index = (int) (random.nextInt(cards.size()));
		return cards.remove(index);
	}
	
	public static void devolveCard(Card c) {
		cards.add(cards.size() - 1, c);
	}

	public static Card retiraCardPrisao() {
		return cards.remove(29);
	}
}
