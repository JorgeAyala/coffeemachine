package br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class ReleaseCoinManagement {

	private Coin[] reverso = Coin.reverse();
	private ArrayList<Coin> listaDeTroco = new ArrayList<Coin>();
	private CoinManagement gerenteDeMoedas = new CoinManagement();

	public void cancelar(ComponentsFactory factory) {
		if (this.gerenteDeMoedas.getTotalDeMoedas() == 0) {
			throw new CoffeeMachineException("Moedas não inseridas.");
		}

		this.gerenteDeMoedas.liberarMoedas(factory, true);
	}


	public void liberarTrocoCaixa(ComponentsFactory factory,
			double valorDaBebida) {
		this.reverso = Coin.reverse();
		for (Coin moeda : this.reverso) {
			for (Coin moedaDeTroco : this.listaDeTroco) {
				if (moedaDeTroco == moeda) {
					factory.getCashBox().release(moeda);
				}
			}
		}
	}

	public void liberarMoedasFromBadge(ComponentsFactory factory, Coin moeda) {
		factory.getCashBox().release(moeda);

	}

}
