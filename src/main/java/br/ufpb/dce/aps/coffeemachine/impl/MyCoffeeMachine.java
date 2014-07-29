package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;
import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

import java.util.ArrayList;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private int divisao, resto;
	CashBox devolverCash;
	ArrayList<Coin> moedas = new ArrayList<Coin>();
	private Drink bebida;
	private Dispenser dispensa;
	
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

		for (Coin c: Coin.reverse()) {


			for (int j = 0; j < moedas.size(); j++) {
				
				if(c== moedas.get(j)){
				
					devolverCash.release(moedas.get(j));
				
				}
			}

		}
		
		this.moedas.clear();

		this.factory.getDisplay().info(Messages.INSERT_COINS);

	}

	public void select(Drink drink) {
		this.factory.getCupDispenser().contains(1);
		this.factory.getWaterDispenser().contains(anyDouble());
		this.factory.getCoffeePowderDispenser().contains(anyDouble());
		
		
		
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(anyDouble());
		this.factory.getWaterDispenser().release(anyDouble());

		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(0.1);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);
		
		
		
		this.factory.getDisplay().info(Messages.INSERT_COINS);

		moedas.clear();
		
		
	}
	
	
	
	/*
	public void select(Drink drink) {
		factory.getCupDispenser().contains(1);
		factory.getWaterDispenser().contains(anyDouble());
		if(factory.getCoffeePowderDispenser().contains(anyDouble())){
		factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);

		for (int i = 0; i < this.moedas.size(); i++){
		cb.release(this.moedas.get(i));
		}

		factory.getDisplay().info(Messages.INSERT_COINS);
		}

		if (drink == Drink.BLACK_SUGAR){
		factory.getSugarDispenser().contains(1.0);
		}

		if (drink == Drink.BLACK_SUGAR){
		factory.getSugarDispenser().release(1.0);
		}

		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(1.0);
		factory.getDisplay().info(Messages.TAKE_DRINK);

		factory.getWaterDispenser().release(1.0);
		factory.getDisplay().info(Messages.MIXING);

		factory.getCoffeePowderDispenser().release(1.0);



		this.moedas.clear();

		factory.getDisplay().info(Messages.INSERT_COINS);

		}
	
	*/

}
