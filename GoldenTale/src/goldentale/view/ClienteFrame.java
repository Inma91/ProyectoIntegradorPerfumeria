package view;

import model.DataStore.Cliente;
import model.DataStore.LineaPedido;
import model.DataStore.PedidoCliente;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del cliente.
 * Gestiona la navegación entre Catálogo, Carrito, Mis Pedidos y Pago
 * mediante CardLayout. El carrito se almacena aquí como estado compartido.
 */
public class ClienteFrame extends JFrame {

    private final Cliente cliente;

    // Carrito compartido entre vistas
    private final List<LineaPedido> carrito = new ArrayList<>();

    // Vistas
    private CatalogoClientePanel   catalogoPanel;
    private CarritoPanel            carritoPanel;
    private MisPedidosPanel         pedidosPanel;
    private PagoPanel               pagoPanel;

    private final CardLayout  cardLayout  = new CardLayout();
    private final JPanel      contentArea = new JPanel(cardLayout);

    public ClienteFrame(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Golden Tale — Catálogo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(800, 540));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildViews();
        add(contentArea, BorderLayout.CENTER);
        showCatalogo();
    }

    // ─── Construcción de vistas ────────────────────────────────────────────

    private void buildViews() {
        catalogoPanel = new CatalogoClientePanel(this);
        carritoPanel  = new CarritoPanel(this);
        pedidosPanel  = new MisPedidosPanel(this);
        pagoPanel     = new PagoPanel(this);

        contentArea.add(catalogoPanel, "catalogo");
        contentArea.add(carritoPanel,  "carrito");
        contentArea.add(pedidosPanel,  "pedidos");
        contentArea.add(pagoPanel,     "pago");
    }

    // ─── Navegación pública ────────────────────────────────────────────────

    public void showCatalogo() {
        setTitle("Golden Tale — Catálogo");
        catalogoPanel.refresh();
        cardLayout.show(contentArea, "catalogo");
    }

    public void showCarrito() {
        setTitle("Golden Tale — Realizar pedido");
        carritoPanel.refresh();
        cardLayout.show(contentArea, "carrito");
    }

    public void showMisPedidos() {
        setTitle("Golden Tale — Mis pedidos");
        pedidosPanel.refresh();
        cardLayout.show(contentArea, "pedidos");
    }

    public void showPago(PedidoCliente pedidoPendiente) {
        setTitle("Golden Tale — Pago");
        pagoPanel.setPedido(pedidoPendiente);
        cardLayout.show(contentArea, "pago");
    }

    public void logout() {
        carrito.clear();
        dispose();
        new LoginFrame().setVisible(true);
    }

    // ─── Accesores compartidos ─────────────────────────────────────────────

    public Cliente        getCliente() { return cliente; }
    public List<LineaPedido> getCarrito() { return carrito; }

    /** Añade una unidad de un perfume al carrito (agrupa si ya existe). */
    public void addToCarrito(model.DataStore.Perfume p, int cantidad) {
        for (LineaPedido l : carrito) {
            if (l.perfume.nombre.equals(p.nombre)) {
                l.cantidad += cantidad;
                return;
            }
        }
        carrito.add(new LineaPedido(p, cantidad));
    }

    /** Elimina una línea del carrito. */
    public void removeFromCarrito(LineaPedido linea) {
        carrito.remove(linea);
    }

    /** Confirma el carrito como pedido y lo añade al historial del cliente. */
    public PedidoCliente confirmarPedido(String metodoPago) {
        if (carrito.isEmpty()) return null;
        int nextRef = 1043 + cliente.pedidos.size();
        PedidoCliente nuevo = new PedidoCliente(
            "#" + nextRef,
            java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            "Pendiente", metodoPago
        );
        nuevo.lineas.addAll(carrito);
        cliente.pedidos.add(0, nuevo);
        carrito.clear();
        return nuevo;
    }
}
