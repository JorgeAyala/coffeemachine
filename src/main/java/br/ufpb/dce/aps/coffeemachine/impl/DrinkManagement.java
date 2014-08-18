package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class DrinkManagement {

	private ComponentsFactory factory;
	private Bebidas drinks;
	private double valor = 35;
	private CoinManagement gerenciadorMoedas;
	private Drink drink;

	public DrinkManagement(ComponentsFactory factory) {
		this.factory = factory;

	}

	public void iniciarDrink(Drink drink) {
		if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.drinks = new CafePreto(drink, this.factory);
		} else {
			this.drinks = new Creme(drink, this.factory);
		}
	}

	public boolean conferirIngredientes() {
		if (!this.factory.getCupDispenser().contains(1)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
			return false;
		} else if (!this.factory.getWaterDispenser().contains(3)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		} else if (!this.factory.getCoffeePowderDispenser().contains(200)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			return false;

		} else if (this.drinks.getDrink() == Drink.WHITE
				|| this.drinks.getDrink() == Drink.WHITE_SUGAR) {
			if (!this.factory.getCreamerDispenser().contains(150)) {
				return false;
			}
		}

		return true;
	}

	public boolean verificarOpcaoAcucar() {
		if (this.drinks.getDrink() == Drink.BLACK_SUGAR
				|| this.drinks.getDrink() == Drink.WHITE_SUGAR) {
			if (!this.factory.getSugarDispenser().contains(200)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void misturar() {
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(200);
		this.factory.getWaterDispenser().release(3);
	}

	public void release() {
		this.drinks.release();
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(1);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	public double getValorDaBebida() {
		return this.valor;
	}
	
	
}

