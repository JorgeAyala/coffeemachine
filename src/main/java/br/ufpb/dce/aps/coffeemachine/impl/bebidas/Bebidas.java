package br.ufpb.dce.aps.coffeemachine.impl.bebidas;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public abstract class Bebidas {

	
	protected Button botao;
	protected ComponentsFactory factory;

	public abstract void release(); 
	
	public Button getDrink() {
		return this.botao;

	}
}
