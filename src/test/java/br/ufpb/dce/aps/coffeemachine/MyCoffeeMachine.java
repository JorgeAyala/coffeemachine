
public class MyCoffeeMachine implements CoffeeMachine{


    MyCoffeeMachine coffee;

    public MyCoffeeMachine(ComponentsFactory factory){
        
        verify(coffee).display().info("Teste Correto");
    }

}
