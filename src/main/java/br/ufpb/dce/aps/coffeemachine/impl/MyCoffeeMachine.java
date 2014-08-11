package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

import java.util.ArrayList;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private int total;
	private int indice;
	private ArrayList<Coin> moedas = new ArrayList<Coin>();
	private DrinkManagement manager;
	private Coin coin;
	private Coin[] reverso = Coin.reverse();

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.manager = new DrinkManagement(this.factory);
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		

	}

	public void insertCoin(Coin coin) {
		if (coin == null) {
			throw new CoffeeMachineException("Coin null");
		}

		this.total += coin.getValue();
		this.coin = coin;
		moedas.add(coin);
		this.indice++;

		this.factory.getDisplay().info(
				"Total: US$ " + this.total / 100 + "." + this.total % 100);

	}

	public void cancel() throws CoffeeMachineException {
		
		this.cancel(true);

	}

	public void cancel(Boolean confirm) {
		if (this.total == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}
		if (this.moedas.size() > 0) {
			if (confirm) {
				this.factory.getDisplay().warn(Messages.CANCEL);
			}
			for (Coin re : this.reverso) {
				for (Coin aux : this.moedas) {
					if (aux == re) {
						this.factory.getCashBox().release(aux);
					}
				}
			}
			this.total = 0;
			this.moedas.clear();
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void liberarTroco(double troco) {
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			while (c.getValue() <= troco) {
				this.factory.getCashBox().release(c);
				troco -= c.getValue();
			}
		}
	}

	public void PlanoDeLiberarTroco(double troco) {
		double trocoProvisorio = troco;
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			while (c.getValue() <= trocoProvisorio) {
				this.factory.getCashBox().count(c);
				trocoProvisorio -= c.getValue();
			}
		}
	}

	public void select(Drink drink) {

		this.manager.iniciarDrink(drink);
		if (!this.manager.conferirIngredientes()) {
			this.cancel(false);
			return;
		}
		if (!this.manager.verificaAcucar()) {
			this.cancel(false);
			return;
		}
		if (this.total % this.manager.getValorDaBebida() != 0
				&& this.total > this.manager.getValorDaBebida()) {
			this.PlanoDeLiberarTroco(this.total
					- this.manager.getValorDaBebida());
		}

		this.manager.Mix();
		this.manager.release();

		if (this.total % this.manager.getValorDaBebida() != 0
				&& this.total > this.manager.getValorDaBebida()) {
			this.liberarTroco(this.total - this.manager.getValorDaBebida());
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		this.moedas.clear();
	}

}
