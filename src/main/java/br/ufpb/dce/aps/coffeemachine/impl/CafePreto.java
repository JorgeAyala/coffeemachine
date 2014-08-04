package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class CafePreto extends Bebidas{
	
	public CafePreto(Drink bebida, ComponentsFactory factory){
		this.factory = factory;
		if (bebida == Drink.BLACK) {
			bebida = Drink.BLACK;

		} else {
			bebida = Drink.BLACK_SUGAR;
		}
		
	}
	
	
	
	

}
