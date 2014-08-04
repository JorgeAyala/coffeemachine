package br.ufpb.dce.aps.coffeemachine.impl;

import org.hamcrest.Factory;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class Creme extends Bebidas {

	public Creme(Drink bebida, ComponentsFactory factory) {
		this.factory = factory;
		if (bebida == Drink.WHITE) {
			bebida = Drink.WHITE;

		} else {
			bebida = Drink.WHITE_SUGAR;
		}
	}

}
