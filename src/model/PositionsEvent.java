package model;

import controller.Game;
import tools.CardType;
import tools.PositionType;

public class PositionsEvent {
	
	public static void verificaEvento(int positionID) {
		Position p = BoardPositions.getPosition(positionID);
		PositionType tipo = p.getPositionType();
		
		Game.getInstance().setPreviewDefault();
		
		if(tipo == PositionType.TERRITORIO || tipo == PositionType.EMPRESA) {
			Empreendimento e = (Empreendimento) p;
			Game.getInstance().setCurrentPositionEmp(e);
			Game.getInstance().updatePreviewLocation(150, 450, 140, 190);
			Game.getInstance().updatePreviewIcon(e.getImageIcon());
			verificaEmpreendimento(e, tipo);
		}
		else if(tipo == PositionType.CARD) {
			Card c = Cards.retiraCard();
			Game.getInstance().updatePreviewLocation(430, 120, 140, 190);
			Game.getInstance().updatePreviewIcon( c.getImageIcon());
			verificaCardType(c);
		}
		else if(tipo == PositionType.INICIO) {
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: INICIO");
		}
		else if(tipo == PositionType.PRISAO) {
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: PRISÃO");
		}
		else if(tipo == PositionType.PARADALIVRE) {
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: PARADA LIVRE");
		}
		else if(tipo == PositionType.IRPRISAO) {
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: IR PRISÃO");
			Game.getInstance().showMessageDialog("Você foi preso! :(", "Alerta");
			Game.getInstance().getCurrentPlayer().setInJail();
		}
		else if(tipo == PositionType.ESPECIAL && positionID == 17) { // POS 17 (Receba $200 - Propinas e Extorsões)
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: ESPECIAL");
			Game.getInstance().showMessageDialog("Propinas e Extorsões - Receba $200", "Especial");
			Game.getInstance().getCurrentPlayer().recebeMoney(200);
		}
		else if(tipo == PositionType.ESPECIAL && positionID == 34) {
			Game.getInstance().updatePreviewText("POSIÇÃO ATUAL: ESPECIAL");
			Game.getInstance().showMessageDialog("Custos de Campanha - Pague $200", "Especial");
			Game.getInstance().getCurrentPlayer().payBank(200);
		}
	}
	
	private static void verificaEmpreendimento(Empreendimento e, PositionType tipo) {
		if(!e.temDono()) {  // Não tem dono (Pode comprar)
			Game.getInstance().comprarBtnSetVisible(true);
		}
		else { // Tem dono (Paga aluguel)
			if(Game.getInstance().getCurrentPlayerID() != e.getDonoID()) { // Se não é o dono, paga taxa
				int taxa = 0;
				if(tipo == PositionType.TERRITORIO) {
					taxa = ((Territorio) e).calculaTaxa();
				}
				else if(tipo == PositionType.EMPRESA) {
					taxa = ((Empresa) e).calculaTaxa();
				}
				Game.getInstance().showMessageDialog("Pague a taxa de $" + taxa + " ao Jogador " + (e.getDonoID() + 1), "Alerta");
		
				Game.getInstance().getCurrentPlayer().payPlayer(taxa, Game.getInstance().getPlayer(e.getDonoID()));
			}
		}
	}
	
	private static void verificaCardType(Card c) {
		
		if(c.getType() == CardType.SORTE) {
			if(c.getGotoID() == 0) { // Vai pro Início
				Game.getInstance().getCurrentPlayer().recebeMoney(c.getValor());
				Game.getInstance().getCurrentPlayer().movePin(0);
				Cards.devolveCard(c);
			}
			else if(c.getValor() == 0) { // Ganha Card sai da prisão
				Game.getInstance().getCurrentPlayer().recebeCardSairPrisao(c);
			}
			else {
				Game.getInstance().getCurrentPlayer().recebeMoney(c.getValor());
				Cards.devolveCard(c);
			}
		}
		else if(c.getType() == CardType.REVES) {
			
			if(c.getGotoID() == 9) { // Vai Preso
				Game.getInstance().getCurrentPlayer().setInJail();
			}
			else {
				Game.getInstance().getCurrentPlayer().payBank(c.getValor());
			}
			Cards.devolveCard(c);
		}
	}

}
