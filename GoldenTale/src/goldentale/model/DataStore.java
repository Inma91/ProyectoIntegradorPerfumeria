package model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    // ── Modelos ────────────────────────────────────────────────────────────

    public static class Perfume {
        public String nombre, marca, categoria, publico, ubicacion;
        public int    stock, ml;
        public double precio;

        public Perfume(String n, String m, String c, String pub, int ml,
                       double precio, int s, String u) {
            nombre = n; marca = m; categoria = c; publico = pub;
            this.ml = ml; this.precio = precio; stock = s; ubicacion = u;
        }
        // Constructor corto (vistas empleado)
        public Perfume(String n, String m, String c, int s, String u) {
            this(n, m, c, "Unisex", 75, 0.0, s, u);
        }
    }

    public static class Pedido {
        public String ref, cliente, perfume, estado;
        public double total;
        public Pedido(String r, String c, String p, double t, String e) {
            ref = r; cliente = c; perfume = p; total = t; estado = e;
        }
    }

    public static class Pago {
        public String ref, cliente, fecha, estado;
        public double importe;
        public Pago(String r, String c, String f, double i, String e) {
            ref = r; cliente = c; fecha = f; importe = i; estado = e;
        }
    }

    public static class LineaPedido {
        public Perfume perfume;
        public int cantidad;
        public LineaPedido(Perfume p, int q) { perfume = p; cantidad = q; }
        public double subtotal() { return perfume.precio * cantidad; }
        public String label()    { return perfume.nombre + " ×" + cantidad; }
    }

    public static class PedidoCliente {
        public String ref, fecha, estado, metodoPago;
        public List<LineaPedido> lineas = new ArrayList<>();
        public PedidoCliente(String r, String f, String e, String mp) {
            ref = r; fecha = f; estado = e; metodoPago = mp;
        }
        public double total() {
            return lineas.stream().mapToDouble(LineaPedido::subtotal).sum();
        }
        public int unidades() {
            return lineas.stream().mapToInt(l -> l.cantidad).sum();
        }
        public String resumenLineas() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lineas.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(lineas.get(i).label());
            }
            return sb.toString();
        }
    }

    public static class Cliente {
        public String id, nombre, password;
        public List<PedidoCliente> pedidos = new ArrayList<>();
        public Cliente(String id, String nombre, String pwd) {
            this.id = id; this.nombre = nombre; this.password = pwd;
        }
    }

    // ── Catálogo (singleton) ───────────────────────────────────────────────

    private static List<Perfume> PERFUMES = null;

    public static synchronized List<Perfume> getPerfumes() {
        if (PERFUMES == null) {
            PERFUMES = new ArrayList<>();
            PERFUMES.add(new Perfume("Velvet Rose",      "Maison Luxe",  "Floral",    "Mujer",  50,  89.99, 12, "Estante A"));
            PERFUMES.add(new Perfume("Black Oud",        "Orient Noir",  "Oriental",  "Hombre", 100, 120.00,  8, "Estante B"));
            PERFUMES.add(new Perfume("Citrus Dream",     "Fresca & Co",  "Cítrico",   "Unisex",  75,  65.50, 15, "Estante C"));
            PERFUMES.add(new Perfume("Midnight Jasmine", "Maison Luxe",  "Floral",    "Mujer",  50,  99.95,  6, "Estante A"));
            PERFUMES.add(new Perfume("Aqua Force",       "BlueSea",      "Acuático",  "Hombre", 100,  75.00,  0, "Estante D"));
            PERFUMES.add(new Perfume("Golden Amber",     "Orient Noir",  "Amaderado", "Unisex",  75, 145.00,  4, "Estante B"));
        }
        return PERFUMES;
    }

    // ── Clientes (singleton inicializado explícitamente) ───────────────────

    private static List<Cliente> CLIENTES = null;

    public static synchronized List<Cliente> getClientes() {
        if (CLIENTES == null) {
            // Asegurar que los perfumes existen antes de crear las líneas
            List<Perfume> pf = getPerfumes();

            CLIENTES = new ArrayList<>();

            // ── Ana García C001 / 1234 ──────────────────────────────────
            Cliente ana = new Cliente("C001", "Ana García", "1234");

            PedidoCliente p1 = new PedidoCliente("#1042", "15/05/2025 10:30", "Pendiente", "Efectivo");
            p1.lineas.add(new LineaPedido(pf.get(0), 2)); // Velvet Rose ×2
            p1.lineas.add(new LineaPedido(pf.get(2), 1)); // Citrus Dream ×1
            ana.pedidos.add(p1);

            PedidoCliente p2 = new PedidoCliente("#1038", "10/05/2025 16:20", "Procesando", "Tarjeta");
            p2.lineas.add(new LineaPedido(pf.get(3), 1)); // Midnight Jasmine ×1
            ana.pedidos.add(p2);

            PedidoCliente p3 = new PedidoCliente("#1031", "03/05/2025 09:00", "Enviado", "Bizum");
            p3.lineas.add(new LineaPedido(pf.get(1), 2)); // Black Oud ×2
            ana.pedidos.add(p3);

            PedidoCliente p4 = new PedidoCliente("#1020", "25/04/2025 14:45", "Entregado", "Efectivo");
            p4.lineas.add(new LineaPedido(pf.get(5), 1)); // Golden Amber ×1
            ana.pedidos.add(p4);

            CLIENTES.add(ana);

            // ── Miguel Torres C002 / 1234 ────────────────────────────────
            Cliente miguel = new Cliente("C002", "Miguel Torres", "1234");

            PedidoCliente mp1 = new PedidoCliente("#1005", "27/05/2026 08:00", "Enviado", "Tarjeta");
            mp1.lineas.add(new LineaPedido(pf.get(3), 1)); // Midnight Jasmine ×1
            miguel.pedidos.add(mp1);

            CLIENTES.add(miguel);
        }
        return CLIENTES;
    }

    /**
     * Busca un cliente por ID y contraseña.
     * Devuelve null si no coincide ninguno.
     */
    public static Cliente findCliente(String id, String password) {
        if (id == null || password == null) return null;
        String idTrim   = id.trim();
        String passTrim = password.trim();
        for (Cliente c : getClientes()) {
            if (c.id.equals(idTrim) && c.password.equals(passTrim)) {
                return c;
            }
        }
        return null;
    }

    // ── Datos empleado ─────────────────────────────────────────────────────

    public static List<Pedido> getPedidos() {
        List<Pedido> list = new ArrayList<>();
        list.add(new Pedido("#1001", "Carlos M.",  "Black Oud ×1",    120.00, "Pendiente"));
        list.add(new Pedido("#1002", "Sofía Ram.", "Velvet Rose ×2",  179.50, "Pendiente"));
        list.add(new Pedido("#1003", "Elena M.",   "Golden Amb...",   145.00, "Procesando"));
        list.add(new Pedido("#1004", "Ana García", "Velvet Rose...",  245.00, "Enviado"));
        list.add(new Pedido("#1005", "Miguel To.", "Midnight Jas...",  99.90, "Enviado"));
        return list;
    }

    public static List<Pago> getPagos() {
        List<Pago> list = new ArrayList<>();
        list.add(new Pago("#1004", "Ana García", "27/05/2026", 245.00, "Confirmado"));
        list.add(new Pago("#1005", "Miguel To.", "27/05/2026",  99.90, "Confirmado"));
        list.add(new Pago("#1001", "Carlos M.",  "26/05/2026", 120.00, "Pendiente"));
        list.add(new Pago("#1002", "Sofía Ram.", "26/05/2026", 179.50, "Pendiente"));
        return list;
    }
}
