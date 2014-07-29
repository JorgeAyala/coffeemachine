package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
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

		this.factory.getDisplay().warn(Messages.CANCEL_MESSAGE);

		for (int i = 0; i < moedas.size(); i++) {

			Coin.reverse();

			for (int j = 0; j < moedas.size(); j++) {
				
				if(moedas.get(i) != moedas.get(j)){
				
					devolverCash.release(moedas.get(j));
				
				}else{
					devolverCash.release(moedas.get(i));
				}
			}

		}
		
		this.moedas.clear();

		this.factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);

	}
	
	

}
