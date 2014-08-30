package br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.Bebidas;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CafeComCreme;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CafePreto;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CaldoDeCarne;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.CoinManagement;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.ReleaseCoinManagement;

public class DrinkManagement {

	private Bebidas drinks;
	private int valor = 35;
	private int valorDoCaldo = 25;
	private ReleaseCoinManagement gerenteDeLiberarMoedas = new ReleaseCoinManagement();

	public void startDrink(ComponentsFactory factory, Button botao) {
		if (botao == Button.BUTTON_1 || botao == Button.BUTTON_3) {
			this.drinks = new CafePreto(botao);
		} else if (botao == Button.BUTTON_2 || botao == Button.BUTTON_4) {
			this.drinks = new CafeComCreme(botao);
		} else {
			this.drinks = new CaldoDeCarne(botao);
			this.valor = this.valorDoCaldo;
		}
	}

	public boolean checkSugar(ComponentsFactory factory) {
		if (this.drinks.getDrink() == Button.BUTTON_3
				|| this.drinks.getDrink() == Button.BUTTON_4) {
			if (!factory.getSugarDispenser().contains(5)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void mixing(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.MIXING);
		if (this.drinks.getDrink() == Button.BUTTON_5) {
			factory.getBouillonDispenser().release(10);
		} else {
			factory.getCoffeePowderDispenser().release(15);
		}
	}

	public void release(ComponentsFactory factory) {
		this.drinks.release(factory);
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(100.0);
		factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	
	
	public void prepararDrink(ComponentsFactory factory, CoinManagement gerenciadorDeMoedas, AskManagement gerenteDePedidos, ItemManagement gerenteDeItems, Button botao) {

		this.startDrink(factory, botao);

		if (!gerenciadorDeMoedas.conferirDinheiroInserido(factory,
				this.getValorDaBebida())) {
			return;
		}

		if (!gerenteDeItems.analisarIngredientes(factory, botao)) {
			gerenciadorDeMoedas.liberarMoedas(factory, false);
			return;
		}
		if (!this.checkSugar(factory)) {
			gerenciadorDeMoedas.liberarMoedas(factory, false);
			return;
		}

		if (!gerenciadorDeMoedas.conferirDisponibiliadadeDeTroco(factory,
				this.getValorDaBebida())) {
			return;
		}

		this.mixing(factory);
		this.release(factory);

		if (gerenciadorDeMoedas.getTotalDeMoedas() >= this
				.getValorDaBebida()) {
			this.gerenteDeLiberarMoedas.liberarTrocoCaixa(factory,
					this.getValorDaBebida());
		}
		gerenteDePedidos.restart(factory);
		gerenciadorDeMoedas.limparCaixaDeMoedas();
	}
	
	public int getValorDaBebida() {
		return this.valor;
	}

}
