package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CoinManagement {

	private int total;
	private int indice;
	private String tipoDePagamento = "";
	private ArrayList<Coin> moedas = new ArrayList<Coin>();
	private ArrayList<Coin> listaDeTroco = new ArrayList<Coin>();
	private Coin coin;
	private ComponentsFactory factory;
	private Coin[] reverso = Coin.reverse();
	private DrinkManagement gerenciadorDeBebidas;

	public CoinManagement(ComponentsFactory factory) {
		this.factory = factory;
		this.gerenciadorDeBebidas = new DrinkManagement(this.factory);

	}

	public void receberMoedas(Coin coin) throws CoffeeMachineException {
		if (this.tipoDePagamento.equals("badge")) {
			this.factory.getDisplay().warn(Messages.CAN_NOT_INSERT_COINS);
			this.liberarMoedasFromBadge(coin);
			return;

		} else {

			try {

				this.total += coin.getValue();
				this.coin = coin;
				this.moedas.add(coin);
				this.indice++;

				this.factory.getDisplay().info(
						"Total: US$ " + this.total / 100 + "." + this.total
								% 100);
			} catch (NullPointerException e) {
				throw new CoffeeMachineException("Erro ao inserir moeda(s)");
			}
		}
	}

	public void cancelar() {
		if (this.total == 0) {
			throw new CoffeeMachineException("Moedas não inseridas.");
		}

		this.liberarMoedas(true);
	}

	public void liberarMoedas(boolean confirmacao) {
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
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public boolean PrepararCaixaParaTroco(double valorDaBebida) {

		// this.reverso = Coin.reverse();
		// for (Coin c : this.reverso) {
		//
		// if (c.getValue() <= troco && this.factory.getCashBox().count(c) > 0)
		// {
		// troco -= c.getValue();
		// }
		// }
		//
		// return (troco == 0);
		double troco = this.total - valorDaBebida;
		this.reverso = Coin.reverse();
		for (Coin moeda : this.reverso) {
			// if (moeda.getValue() <= troco &&
			// factory.getCashBox().count(moeda) > 0) {
			// while (moeda.getValue() <= troco) {
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

	public void liberarTrocoCaixa(double valorDaBebida) {
		// this.reverso = Coin.reverse();
		// for (Coin c : this.reverso) {
		// while (c.getValue() <= troco) {
		// this.factory.getCashBox().release(c);
		// troco -= c.getValue();
		// }
		//
		// }
		this.reverso = Coin.reverse();
		for (Coin moeda : this.reverso) {
			for (Coin moedaDeTroco : this.listaDeTroco) {
				if (moedaDeTroco == moeda) {
					factory.getCashBox().release(moeda);
				}
			}
		}
	}

	public boolean conferirDinheiroInserido(double valorDaBebida) {
		if (this.total < valorDaBebida || this.total == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.liberarMoedas(false);
			return false;
		}
		return true;
	}

	public boolean conferirDisponibiliadadeDeTroco(double valorDaBebida) {

		if (!this.PrepararCaixaParaTroco(valorDaBebida)) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			this.liberarMoedas(false);
			return false;
		}

		return true;
	}

	public void liberarMoedas(Boolean confirmacao) {
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
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void liberarMoedasFromBadge(Coin moeda) {
		this.factory.getCashBox().release(moeda);

	}

	public void tipoDePagamento(String tipoDePagamento) {
		this.tipoDePagamento = tipoDePagamento;
	}

	public void prepararDrink(Drink drink) {

		this.gerenciadorDeBebidas.iniciarDrink(drink);

		if (this.total < this.gerenciadorDeBebidas.getValorDaBebida()
				|| this.total == 0) {
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.liberarMoedas(false);
			return;
		}

		if (!this.gerenciadorDeBebidas.analisarIngredientes(drink)) {
			this.liberarMoedas(false);
			return;
		}
		if (!this.gerenciadorDeBebidas.verificarOpcaoAcucar()) {
			this.liberarMoedas(false);
			return;
		}

		if (!this.conferirDisponibiliadadeDeTroco(this.gerenciadorDeBebidas
				.getValorDaBebida())) {
			return;
		}

		this.gerenciadorDeBebidas.misturar();
		this.gerenciadorDeBebidas.release();

		if (this.getTotalDeMoedas() >= this.gerenciadorDeBebidas
				.getValorDaBebida()) {
			this.liberarTrocoCaixa(this.gerenciadorDeBebidas.getValorDaBebida());
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		this.limparCaixaDeMoedas();
	}

	public int getTotalDeMoedas() {

		return this.total;
	}

	public void limparCaixaDeMoedas() {
		this.moedas = new ArrayList<Coin>();
		this.total = 0;

	}

}