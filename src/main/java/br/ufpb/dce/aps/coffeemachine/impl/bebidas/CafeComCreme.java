package br.ufpb.dce.aps.coffeemachine.impl.bebidas;


import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class CafeComCreme extends Bebidas {

	public CafeComCreme(Drink drink, ComponentsFactory factory) {
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
