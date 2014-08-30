package br.ufpb.dce.aps.coffeemachine.impl.bebidas;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class CafePreto extends Bebidas {

	public CafePreto(Button botao) {
		if (botao == Button.BUTTON_1) {
			this.botao = Button.BUTTON_1;

		} else {
			this.botao = Button.BUTTON_3;
		}

	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
		if (botao == Button.BUTTON_3) {
			factory.getSugarDispenser().release(5);
		}
		
	}

}
