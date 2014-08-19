package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class Bouillon extends Bebidas{
	
	public Bouillon (Drink drink, ComponentsFactory factory) {
		this.factory = factory;
		this.drink = drink.BOUILLON;
	}
	
	public void release() {
		factory.getWaterDispenser().release(100);
//		factory.getDisplay().info(Messages.RELEASING);
//		factory.getCupDispenser().release(1);
	}
	

}
