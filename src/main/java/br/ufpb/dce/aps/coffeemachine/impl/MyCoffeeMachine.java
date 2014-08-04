package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

import java.util.ArrayList;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private int divisao, resto;
	CashBox devolverCash;
	ArrayList<Coin> moedas = new ArrayList<Coin>();

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
		this.devolverCash = factory.getCashBox();
		this.divisao = 0;
		this.resto = 0;
	}

	public void insertCoin(Coin coin) {
		if (coin == null) {
			throw new CoffeeMachineException("Coin null");
		}

		this.divisao += coin.getValue() / 100;
		this.resto += coin.getValue() % 100;
		this.factory.getDisplay().info(
				"Total: US$ " + this.divisao + "." + this.resto);

		moedas.add(coin);

	}

	public void cancel() {
		if (this.divisao == 0 && this.resto == 0) {
			throw new CoffeeMachineException("Não tem moedas inseridas");
		}

		this.factory.getDisplay().warn(Messages.CANCEL);

		for (Coin c : Coin.reverse()) {

			for (int j = 0; j < moedas.size(); j++) {

				if (c == moedas.get(j)) {

					devolverCash.release(moedas.get(j));

				}
			}

		}

		this.moedas.clear();

		this.factory.getDisplay().info(Messages.INSERT_COINS);

	}
	
	public void cancelWithoutIngredients() {

		//int devolverMoedas = this.moedas.hashCode();
		
		Coin[] contrario = Coin.reverse();

		for (Coin re : contrario) {

			for (Coin auxiliar : this.moedas) {

				if (auxiliar == re) {

					this.factory.getCashBox().release(auxiliar);

				}
			}

		}
		this.moedas.clear();

		this.factory.getDisplay().info(Messages.INSERT_COINS);

	}

	public void select(Drink drink) {
		this.factory.getCupDispenser().contains(1);
		this.factory.getWaterDispenser().contains(3);

		if (!this.factory.getCoffeePowderDispenser().contains(200)) {

			this.factory.getDisplay().warn("Out of Coffee Powder");

			this.cancelWithoutIngredients();

		} else {
//			this.factory.getCoffeePowderDispenser().contains(200);

			if (drink == Drink.BLACK_SUGAR) {

				this.factory.getSugarDispenser().contains(200);
			}

			this.factory.getDisplay().info("Mixing ingredients.");

			this.factory.getCoffeePowderDispenser().release(200);
			this.factory.getWaterDispenser().release(3);

			if (drink == Drink.BLACK_SUGAR) {

				this.factory.getSugarDispenser().release(200);

			}

			this.factory.getDisplay().info(Messages.RELEASING);
			this.factory.getCupDispenser().release(1);
			this.factory.getDrinkDispenser().release(1);
			this.factory.getDisplay().info(Messages.TAKE_DRINK);

			this.factory.getDisplay().info(Messages.INSERT_COINS);

			this.moedas.clear();
		}

	}

	

}
