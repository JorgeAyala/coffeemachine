package br.ufpb.dce.aps.coffeemachine.impl;


import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class Creme extends Bebidas {

	public Creme(Drink drink, ComponentsFactory factory) {
		this.factory = factory;
		if (drink == drink.WHITE) {
			this.drink = drink.WHITE;

		} else {
			this.drink = drink.WHITE_SUGAR;
		}
	}

	public void release() {
		factory.getWaterDispenser().release(80);
		this.factory.getCreamerDispenser().release(20);
		if (this.drink == drink.WHITE_SUGAR) {
			this.factory.getSugarDispenser().release(5);
		}
	}

}
