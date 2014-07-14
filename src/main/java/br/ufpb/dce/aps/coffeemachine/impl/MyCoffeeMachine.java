package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Mockito.verify;

import org.mockito.internal.verification.AtLeast;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;



public class MyCoffeeMachine implements CoffeeMachine{

	
	MyCoffeeMachine coffee;
	
		
	public MyCoffeeMachine(ComponentsFactory factory){
		
		verify(coffee).display().info("Teste passando");
		
	}
	
	
	
	private CoffeeMachine createFacade(ComponentsFactory factory){
		
		
		
		
		return null;
				
	}
	
		
	

}
