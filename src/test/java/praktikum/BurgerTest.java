package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    Database db = new Database();

    private Bun bun = new Bun("black bun", 100f);
    private Ingredient ingredient = Mockito.spy(db.availableIngredients().get(0));
    private Ingredient ingredient2 = db.availableIngredients().get(3);
    private Ingredient ingredient3 = db.availableIngredients().get(2);

    @Test
    public void setBuns() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(ingredient);
        assertTrue(burger.ingredients.contains(ingredient));
        int expected = 1;
        int actual = burger.ingredients.size();
        assertEquals(expected, actual);
    }

    @Test
    public void removeIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.contains(ingredient2));
        assertTrue(burger.ingredients.contains(ingredient3));
    }

    @Test
    public void moveIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);
        burger.moveIngredient(2, 0);
        assertEquals(ingredient3, burger.ingredients.get(0));
    }

    @Test
    public void getPrice() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient3);
        Mockito.when(ingredient.getPrice()).thenReturn(110f);
        float actual = burger.getPrice();
        float expected = 610;
        assertEquals(actual, expected, 0);
    }

    @Test
    public void getReceipt() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient3);
        burger.getReceipt();
        assertTrue(burger.getReceipt().contains("==== black bun ===="));
        assertTrue(burger.getReceipt().contains(ingredient3.getType().toString().toLowerCase()));
        assertTrue(burger.getReceipt().contains("Price: " + 600));
    }
}
