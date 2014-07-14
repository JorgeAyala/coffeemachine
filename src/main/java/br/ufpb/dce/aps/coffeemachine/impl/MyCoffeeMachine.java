package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Mockito.verify;

import org.mockito.internal.verification.AtLeast;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;



public class MyCoffeeMachine implements CoffeeMachine{

	
	
	
		
	public MyCoffeeMachine(ComponentsFactory factory){
		
		verify(teste).createFacade(factory);
		
		//verify(teste,atLeastOnce()).createFacade(factory);
		
				
	}
	
	MyCoffeeMachine teste;
	
	private CoffeeMachine createFacade(ComponentsFactory factory){
		
		
		
		
		return null;
				
	}
	
		
	

}
