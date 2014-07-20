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
	private DrinkManagement gerenciadorDeBebidas;
	private CoinManagement gerenciadorDeMoedas;
	

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.gerenciadorDeBebidas = new DrinkManagement(this.factory);
		this.gerenciadorDeMoedas = new CoinManagement(this.factory);
		this.factory.getDisplay().info(Messages.INSERT_COINS);

	}

	public void insertCoin(Coin coin) {
		this.gerenciadorDeMoedas.receberMoedas(coin);

	}

	public void cancel() throws CoffeeMachineException {

		this.gerenciadorDeMoedas.cancelar();

	}

	public void select(Drink drink) {

		this.gerenciadorDeMoedas.prepararDrink(drink);
	}
	
	

}

