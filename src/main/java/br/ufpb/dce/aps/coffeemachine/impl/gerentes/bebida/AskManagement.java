package br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.CoinManagement;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.ReleaseCoinManagement;

public class AskManagement {

	private DrinkManagement gerenciadorDeBebidas = new DrinkManagement();
	private ReleaseCoinManagement gerenteDeLiberarMoedas = new ReleaseCoinManagement();
	private CoinManagement gerenteDeMoedas = new CoinManagement();
	private ItemManagement gerenteDeItems = new ItemManagement();
	private int badge = 0;

	public void startRequestDrink(ComponentsFactory factory, Button botao) {
		if (gerenteDeMoedas.pegarTipoDePagamento().equals("badge")) {
			this.startOrderWithBadge(factory, botao);
		} else {
			this.startOrderWithCoin(factory, botao);
		}

	}

	public void startOrderWithBadge(ComponentsFactory factory, Button botao) {

		this.gerenciadorDeBebidas.startDrink(factory, botao);

		if (!this.gerenteDeItems.analisarIngredientes(factory, botao)) {
			return;
		}

		if (!this.gerenciadorDeBebidas.checkSugar(factory)) {
			return;
		}

		if (!factory.getPayrollSystem().debit(
				gerenciadorDeBebidas.getValorDaBebida(), this.badge)) {
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.restart(factory);
			return;
		}

		this.gerenciadorDeBebidas.mixing(factory);
		this.gerenciadorDeBebidas.release(factory);
		
		this.restart(factory);
	}
	
	public void startWithCoins(ComponentsFactory factory) {
		factory.getButtonDisplay().show("Black: $0.35", "White: $0.35",
				"Black with sugar: $0.35", "White with sugar: $0.35",
				"Bouillon: $0.25", null, null);
		factory.getDisplay().info(Messages.INSERT_COINS);
		CoinManagement.setarTipoDePagamento("moedas");
	}

	public void startWithBadge(ComponentsFactory factory, int badge) {
		if (this.gerenteDeMoedas.getTotalDeMoedas() > 0) {
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		} else {
			factory.getDisplay().info(Messages.BADGE_READ);
			this.badge = badge;
			CoinManagement.setarTipoDePagamento("badge");
		}
	}

	public void startOrderWithCoin(ComponentsFactory factory, Button botao) {

		this.gerenciadorDeBebidas.startDrink(factory, botao);

		if (!this.gerenteDeMoedas.conferirDinheiroInserido(factory,
				this.gerenciadorDeBebidas.getValorDaBebida())) {
			return;
		}

		if (!this.gerenteDeItems.analisarIngredientes(factory, botao)) {
			gerenteDeMoedas.liberarMoedas(factory, false);
			return;
		}
		if (!this.gerenciadorDeBebidas.checkSugar(factory)) {
			gerenteDeMoedas.liberarMoedas(factory, false);
			return;
		}

		if (!this.gerenteDeMoedas.conferirDisponibiliadadeDeTroco(factory,
				this.gerenciadorDeBebidas.getValorDaBebida())) {
			return;
		}

		this.gerenciadorDeBebidas.mixing(factory);
		this.gerenciadorDeBebidas.release(factory);

		if (this.gerenteDeMoedas.getTotalDeMoedas() >= this.gerenciadorDeBebidas
				.getValorDaBebida()) {
			gerenteDeLiberarMoedas.liberarTrocoCaixa(factory,
					this.gerenciadorDeBebidas.getValorDaBebida());
		}
		this.restart(factory);
		this.gerenteDeMoedas.limparCaixaDeMoedas();
	}

	public void restart(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
		CoinManagement.setarTipoDePagamento(" ");

	}

}
