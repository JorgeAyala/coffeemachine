package br.ufpb.dce.aps.coffeemachine.impl.bebidas;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CaldoDeCarne extends Bebidas{
	
	public CaldoDeCarne (Button botao) {
		this.botao = Button.BUTTON_5;
	}
	
	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
//		factory.getDisplay().info(Messages.RELEASING);
//		factory.getCupDispenser().release(1);
	}
	

}
