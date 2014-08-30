package br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.Bebidas;

public class ItemManagement {

	private Bebidas drinks;

	public boolean analisarIngredientes(ComponentsFactory factory, Button botao) {
		if (this.drinks.getDrink() == Button.BUTTON_1
				|| this.drinks.getDrink() == Button.BUTTON_3) {
			return (this.analisarIngredientes(factory, botao, 1, 100, 15, 0, 0));

		} else if (this.drinks.getDrink() == Button.BUTTON_2
				|| this.drinks.getDrink() == Button.BUTTON_4) {
			return (this.analisarIngredientes(factory, botao, 1, 80, 15, 20, 0));
		} else {
			return (this.analisarIngredientes(factory, botao, 1, 100, 0, 0, 10));
		}
	}

	public boolean analisarIngredientes(ComponentsFactory factory,
			Button botao, int copo, int agua, int po, int creme, int caldo) {
		if (copo > 0) {
			if (!factory.getCupDispenser().contains(copo)) {
				factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
			}
		}
		if (!factory.getWaterDispenser().contains(agua)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		if (po > 0) {
			if (!factory.getCoffeePowderDispenser().contains(po)) {
				factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				return false;
			}
		}
		if (this.drinks.getDrink() == Button.BUTTON_2
				|| this.drinks.getDrink() == Button.BUTTON_4) {
			if (!factory.getCreamerDispenser().contains(creme)) {
				factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
		if (caldo > 0) {
			if (!factory.getBouillonDispenser().contains(caldo)) {
				factory.getDisplay().warn(Messages.OUT_OF_BOUILLON_POWDER);
				return false;
			}
		}
		return true;
	}

}
