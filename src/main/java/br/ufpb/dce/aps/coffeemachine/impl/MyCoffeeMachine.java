package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private int divisao, resto;
	CashBox devolverCash;
	

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
		devolverCash = factory.getCashBox();
		this.divisao = 0;
		this.resto =  0;
	}

	public void insertCoin(Coin dime) {
		if (dime == null) {
			throw new CoffeeMachineException("Coin null");
		}
		
		this.divisao += dime.getValue() / 100;
		this.resto += dime.getValue() % 100;
		this.factory.getDisplay().info(
				"Total: US$ " + this.divisao + "." + this.resto);
	}

	public void cancel() {
		if (this.divisao == 0 && this.resto == 0) {
			throw new CoffeeMachineException("Não tem moedas inseridas");
		}
		
			this.factory.getDisplay().warn(Messages.CANCEL_MESSAGE);
									
			devolverCash.release(Coin.halfDollar);
			
			this.factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
			
	}

}
