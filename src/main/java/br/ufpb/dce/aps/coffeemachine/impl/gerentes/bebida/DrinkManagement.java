package br.ufpb.dce.aps.coffeemachine.impl.gerentes.bebida;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.Bebidas;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CafeComCreme;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CafePreto;
import br.ufpb.dce.aps.coffeemachine.impl.bebidas.CaldoDeCarne;
import br.ufpb.dce.aps.coffeemachine.impl.gerentes.moeda.CoinManagement;

public class DrinkManagement {

	private ComponentsFactory factory;
	private Bebidas drinks;
	private double valor = 35;
	private double valorDoCaldo = 25;
	private CoinManagement gerenciadorMoedas;
	private Drink drink;

	public DrinkManagement(ComponentsFactory factory) {
		this.factory = factory;

	}

	//Bebidas
	public void iniciarDrink(Drink drink) {
		if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.drinks = new CafePreto(drink, this.factory);
		} else if (drink == drink.WHITE || drink == drink.WHITE_SUGAR) {
			this.drinks = new CafeComCreme(drink, this.factory);
		} else {
			this.drinks = new CaldoDeCarne(drink, this.factory);
			this.valor = this.valorDoCaldo;
		}
	}
	
	//Item Management
	public boolean analisarIngredientes(Drink drink) {
		if (this.drinks.getDrink() == drink.BLACK
				|| this.drinks.getDrink() == Drink.BLACK_SUGAR) {
			return (this.analisarIngredientes(drink, 1, 100, 15, 0, 0));

		} else if (this.drinks.getDrink() == drink.WHITE
				|| this.drinks.getDrink() == drink.WHITE_SUGAR) {
			return (this.analisarIngredientes(drink, 1, 80, 15, 20, 0));
		} else {
			return (this.analisarIngredientes(drink, 1, 100, 0, 0, 10));
		}
	}
	
	//Item Management
	public boolean analisarIngredientes(Drink drink, int copo, int agua,
			int po, int creme, int caldo) {
		if (copo > 0) {
			if (!this.factory.getCupDispenser().contains(copo)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
			}
		}
		if (!this.factory.getWaterDispenser().contains(agua)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		if (po > 0) {
			if (!this.factory.getCoffeePowderDispenser().contains(po)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				return false;
			}
		}
		if (this.drinks.getDrink() == Drink.WHITE
				|| this.drinks.getDrink() == Drink.WHITE_SUGAR) {
			if (!this.factory.getCreamerDispenser().contains(creme)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
		if (caldo > 0) {
			if (!this.factory.getBouillonDispenser().contains(caldo)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_BOUILLON_POWDER);
				return false;
			}
		}
		return true;
	}

	public boolean verificarOpcaoAcucar() {
		if (this.drinks.getDrink() == Drink.BLACK_SUGAR
				|| this.drinks.getDrink() == Drink.WHITE_SUGAR) {
			if (!this.factory.getSugarDispenser().contains(5)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void misturar() {
		this.factory.getDisplay().info(Messages.MIXING);
		if (this.drinks.getDrink() == Drink.BOUILLON) {
			this.factory.getBouillonDispenser().release(10);
		} else {
			this.factory.getCoffeePowderDispenser().release(15);
		}
	}

	public void release() {
		this.drinks.release();
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(100.0);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	public double getValorDaBebida() {
		return this.valor;
	}

}
