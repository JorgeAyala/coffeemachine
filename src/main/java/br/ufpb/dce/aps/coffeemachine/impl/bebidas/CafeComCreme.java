package br.ufpb.dce.aps.coffeemachine.impl.bebidas;


import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class CafeComCreme extends Bebidas {

	public CafeComCreme(Button botao) {
		if (botao == Button.BUTTON_2) {
			this.botao = Button.BUTTON_2;

		} else {
			this.botao = Button.BUTTON_4;
		}
	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(80);
		factory.getCreamerDispenser().release(20);
		if (this.botao == Button.BUTTON_4) {
			factory.getSugarDispenser().release(5);
		}
		
	}

}
