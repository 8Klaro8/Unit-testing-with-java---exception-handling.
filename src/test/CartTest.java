package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;

import src.main.models.Cart;
import src.main.models.Item;

public class CartTest {

    Cart cart;

    @Before
    public void setup() {
        cart = new Cart();
        cart.addItem(new Item("item_1", 1.1));
    }

    @Test
    public void testAdded() {
        assertTrue(cart.contains(new Item("item_1", 1.1)));
    }

    @Test
    public void testRemove() {
        cart.removeItem("item_1");
        assertFalse(cart.contains(new Item("item_1", 1.1)));
    }

    @Test
    public void removeEmptyCart() {
        cart.removeItem("item_1");
        assertFalse(cart.contains(new Item("item_1", 1.1)));
    }

    @Test
    public void subtotalIsValid() {
        assertEquals(1.1, cart.getSubtotal());
    }

    @Test
    public void taxIsValid() {
        assertEquals(.143, cart.getTax(1.1));
    }

    @Test
    public void totalIsValid() {
        assertEquals(1.243, cart.getTotal(0.13, 1.1));
        System.out.println(cart.checkout());
    }

    @Test(expected = IllegalStateException.class)
    public void invalidRemoveState() {
        cart.clear();
        cart.removeItem("item_1");
    }

    @Test(expected = IllegalStateException.class)
    public void invalidCheckoutState() {
        cart.clear();
        cart.checkout();
    }
    
}
