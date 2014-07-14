package br.ufpb.dce.aps.coffeemachine;

import br.ufpb.dce.aps.coffeemachine.impl.MyCoffeeMachine;

public class MyCoffeeMachineTest extends CoffeeMachineTest{

	@Override
	protected CoffeeMachine createFacade(ComponentsFactory factory) {
		// TODO Auto-generated method stub
		return new MyCoffeeMachine(factory);
	}
	
		
	

}
