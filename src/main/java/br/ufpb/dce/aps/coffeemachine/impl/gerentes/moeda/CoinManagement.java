package br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida.DrinkManagement;

public class CoinManagement {

	private int total;
	private static String tipoPagamento = "";
	private Coin coin;
	private int indice;
	private ArrayList<Coin> moedas = new ArrayList<Coin>();
	private ArrayList<Coin> listaDeTroco = new ArrayList<Coin>();
	private Coin[] reverso = Coin.reverse();
	

	private ReleaseCoinManagement gerenteDeLiberarMoedas = new ReleaseCoinManagement();

	public void receberMoedas(ComponentsFactory factory, Coin coin, String badge)
			throws CoffeeMachineException {
		if (badge.equals("badge")) {
			factory.getDisplay().warn(Messages.CAN_NOT_INSERT_COINS);
			this.gerenteDeLiberarMoedas.liberarMoedasFromBadge(factory, coin);
			return;

		} else {

			try {

				this.total += coin.getValue();
				this.coin = coin;
				this.moedas.add(coin);
				this.indice++;

				factory.getDisplay().info(
						"Total: US$ " + this.total / 100 + "." + this.total
								% 100);
			} catch (NullPointerException e) {
				throw new CoffeeMachineException("Erro ao inserir moeda(s)");
			}
		}
	}
	
	public void liberarMoedas(ComponentsFactory factory, boolean confirmacao) {
		if (confirmacao) {
			factory.getDisplay().warn(Messages.CANCEL);
		}
		for (Coin re : this.reverso) {
			for (Coin aux : this.moedas) {
				if (aux == re) {
					factory.getCashBox().release(aux);
				}
			}
		}
		this.total = 0;
		this.limparCaixaDeMoedas();
		CoinManagement.setarTipoDePagamento(" ");
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public boolean PrepararCaixaParaTroco(ComponentsFactory factory,
			double valorDaBebida) {

		double troco = this.total - valorDaBebida;
		this.reverso = Coin.reverse();
		for (Coin moeda : this.reverso) {
			if (moeda.getValue() <= troco) {
				int count = factory.getCashBox().count(moeda);
				while (moeda.getValue() <= troco && count > 0) {
					troco = troco - moeda.getValue();
					this.listaDeTroco.add(moeda);
				}
			}
		}
		return (troco == 0);
	}

	public boolean conferirDinheiroInserido(ComponentsFactory factory,
			double valorDaBebida) {
		if (this.total < valorDaBebida || this.total == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.liberarMoedas(factory, false);
			return false;
		}
		return true;
	}

	public boolean conferirDisponibiliadadeDeTroco(ComponentsFactory factory,
			double valorDaBebida) {

		if (!this.PrepararCaixaParaTroco(factory, valorDaBebida)) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			this.liberarMoedas(factory, false);
			return false;
		}

		return true;
	}

	public static void setarTipoDePagamento(String tipoDePagamento) {
		CoinManagement.tipoPagamento = tipoDePagamento;
	}

	public String pegarTipoDePagamento() {
		return CoinManagement.tipoPagamento;
	}

	public int getTotalDeMoedas() {

		return this.total;
	}

	public void limparCaixaDeMoedas() {
		this.moedas = new ArrayList<Coin>();
		this.total = 0;

	}

}