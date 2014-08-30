package br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida.DrinkManagement;

public class CoinManagement {

	private int total;
	private int indice;
	private int badge = 0;
	private static String tipoPagamento = "";

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

	// Insert Coin
	public void receberMoedas(Coin coin, String badge)
			throws CoffeeMachineException {
		if (badge.equals("badge")) {
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

	// Release Coin
	public void cancelar() {
		if (this.total == 0) {
			throw new CoffeeMachineException("Moedas não inseridas.");
		}

		this.liberarMoedas(true);
	}

	// Release Coin
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
		CoinManagement.setarTipoDePagamento(" ");
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public boolean PrepararCaixaParaTroco(double valorDaBebida) {

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

	// Release Coin
	public void liberarTrocoCaixa(double valorDaBebida) {
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
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
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

	// Release Coin
	public void liberarMoedasFromBadge(Coin moeda) {
		this.factory.getCashBox().release(moeda);

	}

	public void iniciarComMoedas() {
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		CoinManagement.setarTipoDePagamento("moedas");
	}

	public void iniciarComCracha(int badge) {
		if (this.getTotalDeMoedas() > 0) {
			this.factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		} else {
			this.factory.getDisplay().info(Messages.BADGE_READ);
			this.badge = badge;
			CoinManagement.setarTipoDePagamento("badge");
		}
	}

	public static void setarTipoDePagamento(String tipoDePagamento) {
		CoinManagement.tipoPagamento = tipoDePagamento;
	}

	public String pegarTipoDePagamento() {
		return CoinManagement.tipoPagamento;
	}

	public void iniciarPedidoDeBebida(Drink drink) {
		if (CoinManagement.tipoPagamento.equals("badge")) {
			this.iniciarPedidoComCracha(drink);
		} else {
			this.prepararDrink(drink);
		}

	}

	public void iniciarPedidoComCracha(Drink drink) {
		
		this.gerenciadorDeBebidas.iniciarDrink(drink);
		
		if (!this.gerenciadorDeBebidas.analisarIngredientes(drink)) {
			return;
		}
			
		if (!this.gerenciadorDeBebidas.verificarOpcaoAcucar()) {
			return;
		}
		
		//this.factory.getPayrollSystem().debit(
				//gerenciadorDeBebidas.getValorDaBebida(), this.badge);
		if (!this.factory.getPayrollSystem().debit(
				gerenciadorDeBebidas.getValorDaBebida(), this.badge)){
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.reiniciar();
			return;
		}

		this.gerenciadorDeBebidas.misturar();
		this.gerenciadorDeBebidas.release();

		//this.factory.getDisplay().info(Messages.INSERT_COINS);
		//CoinManagement.setarTipoDePagamento(" ");
		this.reiniciar();
	}
	public void reiniciar(){
		factory.getDisplay().info(Messages.INSERT_COINS);
		CoinManagement.setarTipoDePagamento(" ");
		
	}

	// Item Management
	public void prepararDrink(Drink drink) {

		this.gerenciadorDeBebidas.iniciarDrink(drink);

		if(!this.conferirDinheiroInserido(this.gerenciadorDeBebidas.getValorDaBebida())){
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
		this.reiniciar();
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