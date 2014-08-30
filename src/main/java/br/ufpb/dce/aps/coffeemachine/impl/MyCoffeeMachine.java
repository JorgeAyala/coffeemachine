package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida.AskManagement;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.CoinManagement;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.ReleaseCoinManagement;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private CoinManagement gerenciadorDeMoedas = new CoinManagement();
	private ReleaseCoinManagement gerenteDeLiberarMoedas = new ReleaseCoinManagement();
	private AskManagement gerenteDePedidos = new AskManagement();
	private CoinManagement gerenteDeMoedas = new CoinManagement();
	
	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;
		this.gerenteDePedidos.startWithCoins(factory);

	}

	public void insertCoin(Coin coin) {
		this.gerenteDeMoedas.receberMoedas(this.factory, coin,
				this.gerenciadorDeMoedas.pegarTipoDePagamento());

	}

	public void cancel() {

		this.gerenteDeLiberarMoedas.cancelar(this.factory);

	}

	public void select(Button botao) {

		this.gerenteDePedidos.startRequestDrink(this.factory, botao);
	}

	public void readBadge(int badgeCode) {
		this.gerenteDePedidos.startWithBadge(this.factory, badgeCode);

	}

}
