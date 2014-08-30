package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida.DrinkManagement;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.CoinManagement;

import java.util.ArrayList;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private DrinkManagement gerenciadorDeBebidas;
	private CoinManagement gerenciadorDeMoedas;

	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;
		this.gerenciadorDeBebidas = new DrinkManagement(this.factory);
		this.gerenciadorDeMoedas = new CoinManagement(this.factory);
		this.gerenciadorDeMoedas.iniciarComMoedas();

	}

	public void insertCoin(Coin coin) {
		this.gerenciadorDeMoedas.receberMoedas(coin,
				this.gerenciadorDeMoedas.pegarTipoDePagamento());

	}

	public void cancel(){

		this.gerenciadorDeMoedas.cancelar();

	}

	public void select(Button botao) {

		this.gerenciadorDeMoedas.iniciarPedidoDeBebida(botao);
	}

	public void readBadge(int badgeCode) {
		this.gerenciadorDeMoedas.iniciarComCracha(badgeCode);

	}

}
