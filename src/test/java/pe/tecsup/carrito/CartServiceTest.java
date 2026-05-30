package pe.tecsup.carrito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CartService - reglas de negocio del carrito")
class CartServiceTest {

    private CartService cart;

    @BeforeEach
    void setUp() {
        cart = new CartService();
    }

    @Test
    @DisplayName("Carrito vacío: total debe ser 0.0")
    void getTotal_carritoVacio_retornaCero() {

        // Arrange
        // Hecho en setUp()

        // Act
        double total = cart.getTotal();

        // Assert
        assertEquals(0.0, total);
    }

    //Interacion 2
    @Test
    @DisplayName("Agregar producto: total refleja el precio del producto")
    void addProduct_unProducto_totalIgualAlPrecio() {

        // Arrange
        Product laptop = new Product("Laptop", 850.0);

        // Act
        cart.addProduct(laptop);

        // Assert
        assertEquals(850.0, cart.getTotal());
    }

    //Interacion 3
    @Test
    @DisplayName("Agregar mismo producto dos veces: total es precio × 2")
    void addProduct_mismoProductoDosVeces_totalEsDoble() {

        // Arrange
        Product mouse = new Product("Mouse", 25.0);

        // Act
        cart.addProduct(mouse);
        cart.addProduct(mouse);

        // Assert
        assertEquals(50.0, cart.getTotal());
    }
    //Interacion 4
    @Test
    @DisplayName("Precio negativo: debe lanzar IllegalArgumentException")
    void addProduct_precioNegativo_lanzaExcepcion() {

        // Arrange
        Product invalido = new Product("Item inválido", -10.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> cart.addProduct(invalido));
    }

    //Interacion 5
    @Test
    @DisplayName("Producto nulo: debe lanzar NullPointerException")
    void addProduct_productoNulo_lanzaExcepcion() {

        assertThrows(NullPointerException.class,
                () -> cart.addProduct(null));
    }

    //Interacion 6.1
    @Test
    @DisplayName("Total mayor a 100: aplica descuento del 10%")
    void getTotalWithDiscount_totalMayorACien_aplicaDescuento() {

        // Arrange
        cart.addProduct(new Product("TV", 120.0));

        // Act
        double totalConDescuento = cart.getTotalWithDiscount();

        // Assert
        assertEquals(108.0, totalConDescuento);
    }

    //Interacion 6.2
    @Test
    @DisplayName("Total igual a 100: NO aplica descuento")
    void getTotalWithDiscount_totalExactamenteCien_sinDescuento() {

        // Arrange
        cart.addProduct(new Product("Audífonos", 100.0));

        // Act
        double totalConDescuento = cart.getTotalWithDiscount();

        // Assert
        assertEquals(100.0, totalConDescuento);
    }

    //Interacion 7.1
    @Test
    @DisplayName("Eliminar producto existente: total se reduce correctamente")
    void removeProduct_productoExistente_totalSeReduce() {

        // Arrange
        Product libro = new Product("Libro", 40.0);

        cart.addProduct(libro);
        cart.addProduct(new Product("Cuaderno", 15.0));

        // Act
        cart.removeProduct(libro);

        // Assert
        assertEquals(15.0, cart.getTotal());
    }

    //Interacion 7.2
    @Test
    @DisplayName("Eliminar producto inexistente: no lanza excepción")
    void removeProduct_productoNoExiste_sinExcepcion() {

        // Arrange
        Product fantasma = new Product("Fantasma", 99.0);

        // Act + Assert
        assertDoesNotThrow(() -> cart.removeProduct(fantasma));
    }

    //Interacion 10 - Reto Adicional
    @Test
    @DisplayName("No permite más de 10 productos")
    void addProduct_masDeDiezProductos_lanzaExcepcion() {

        for (int i = 1; i <= 10; i++) {
            cart.addProduct(new Product("Producto" + i, 10.0));
        }

        assertThrows(IllegalStateException.class,
                () -> cart.addProduct(new Product("Producto11", 10.0)));
    }

    //Interacion 11 - Reto Adicional
    @Test
    @DisplayName("Retorna la cantidad de productos del carrito")
    void getProductCount_retornaCantidadCorrecta() {

        cart.addProduct(new Product("Mouse", 25.0));
        cart.addProduct(new Product("Teclado", 50.0));

        assertEquals(2, cart.getProductCount());
    }

    //Interacion 12 - Reto Adicional
    @Test
    @DisplayName("Clear vacía el carrito")
    void clear_vaciaCarrito_totalCero() {

        cart.addProduct(new Product("Laptop", 1000.0));
        cart.addProduct(new Product("Mouse", 50.0));

        cart.clear();

        assertEquals(0.0, cart.getTotal());
        assertEquals(0, cart.getProductCount());
    }
}
