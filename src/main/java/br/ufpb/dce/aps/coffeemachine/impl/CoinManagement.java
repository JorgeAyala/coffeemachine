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
	private ArrayList<Coin> moedas = new ArrayList<Coin>();
	private Coin coin;
	private ComponentsFactory factory;
	private Coin[] reverso = Coin.reverse();
	private DrinkManagement gerenciadorDeBebidas;
	
	public CoinManagement(ComponentsFactory factory) {
		this.factory = factory;
		this.gerenciadorDeBebidas = new DrinkManagement(this.factory);
		
	}
	
	public void receberMoedas(Coin coin){
		if (coin == null) {
			throw new CoffeeMachineException("Coin null");
		}

		this.total += coin.getValue();
		this.coin = coin;
		moedas.add(coin);
		this.indice++;

		this.factory.getDisplay().info(
				"Total: US$ " + this.total / 100 + "." + this.total % 100);
	}
	
	public void cancelar(Boolean confirm) {
		if (this.total == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}
		if (this.moedas.size() > 0) {
			if (confirm) {
				this.factory.getDisplay().warn(Messages.CANCEL);
			}
			for (Coin re : this.reverso) {
				for (Coin aux : this.moedas) {
					if (aux == re) {
						this.factory.getCashBox().release(aux);
					}
				}
			}
			this.total = 0;
			this.limparCaixaDeMoedas();
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}
	
	public void liberarTroco(double troco) {
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			while (c.getValue() <= troco) {
				this.factory.getCashBox().release(c);
				troco -= c.getValue();
			}
		}
	}

	public void PlanoDeLiberarTroco(double troco) {
		double trocoProvisorio = troco;
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			while (c.getValue() <= trocoProvisorio) {
				this.factory.getCashBox().count(c);
				trocoProvisorio -= c.getValue();
			}
		}
	}
	
	public void prepararDrink(Drink drink) {

		this.gerenciadorDeBebidas.iniciarDrink(drink);
		if (!this.gerenciadorDeBebidas.conferirIngredientes()) {
			this.cancelar(false);
			return;
		}
		if (!this.gerenciadorDeBebidas.verificaAcucar()) {
			this.cancelar(false);
			return;
		}
		if (this.total % this.gerenciadorDeBebidas.getValorDaBebida() != 0
				&& this.total > this.gerenciadorDeBebidas.getValorDaBebida()) {
			this.PlanoDeLiberarTroco(this.total
					- this.gerenciadorDeBebidas.getValorDaBebida());
		}

		this.gerenciadorDeBebidas.Mix();
		this.gerenciadorDeBebidas.release();

		if (this.total % this.gerenciadorDeBebidas.getValorDaBebida() != 0
				&& this.total > this.gerenciadorDeBebidas.getValorDaBebida()) {
			this.liberarTroco(this.total - this.gerenciadorDeBebidas.getValorDaBebida());
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		this.limparCaixaDeMoedas();
	}
	
	
	public int getTotalDeMoedas() {
		
		return this.total;
	}
	
	public void limparCaixaDeMoedas() {
		this.moedas = new ArrayList<Coin>();
		
		
	}	
	

}