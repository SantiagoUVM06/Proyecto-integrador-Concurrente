import java.util.*;

// -------------------- Clase Miembro --------------------
class Miembro {
    protected String nombre;

    public Miembro(String nombre) {
        this.nombre = nombre;
    }
}

// -------------------- Subclases --------------------
class Explorador extends Miembro {
    public Explorador(String nombre) {
        super(nombre);
    }

    public boolean detectarBomba() {
        System.out.println(nombre + " está buscando bombas...");
        return true; // 🔥 ahora siempre detecta (mejor para pruebas)
    }
}

class Especialista extends Miembro {
    public Especialista(String nombre) {
        super(nombre);
    }

    public void desactivar() {
        System.out.println(nombre + " desactivando bomba...");
    }
}

class Operador extends Miembro {
    public Operador(String nombre) {
        super(nombre);
    }

    public void manejarEquipo() {
        System.out.println(nombre + " preparando equipo...");
    }
}

// -------------------- Clase Bomba --------------------
class Bomba {
    private int id;
    private boolean activa;
    private Ubicacion ubicacion;

    public Bomba(int id, Ubicacion ubicacion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.activa = true;
    }

    public void desactivar() {
        if (activa) {
            activa = false;
            System.out.println("✅ Bomba " + id + " desactivada en " + ubicacion.getNombre());
        }
    }
}

// -------------------- Clase Ubicacion --------------------
class Ubicacion {
    private String nombre;
    private List<Bomba> bombas;

    public Ubicacion(String nombre) {
        this.nombre = nombre;
        this.bombas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarBomba(Bomba b) {
        bombas.add(b);
    }

    public List<Bomba> getBombas() {
        return bombas;
    }
}

// -------------------- Clase Equipo --------------------
class Equipo {
    private int idEquipo;
    private Explorador explorador;
    private Especialista especialista;
    private Operador operador;

    public Equipo(int id, Explorador e, Especialista s, Operador o) {
        this.idEquipo = id;
        this.explorador = e;
        this.especialista = s;
        this.operador = o;
    }

    public void moverse(Ubicacion u) {
        System.out.println("Equipo " + idEquipo + " se mueve a " + u.getNombre());
    }

    public void buscarBomba(Ubicacion u) {
        if (explorador.detectarBomba() && !u.getBombas().isEmpty()) {
            System.out.println("Equipo " + idEquipo + " encontró bomba");
            operador.manejarEquipo();
            especialista.desactivar();
            u.getBombas().get(0).desactivar();
        } else {
            System.out.println("Equipo " + idEquipo + " no encontró nada");
        }
    }
}

// -------------------- Clase Maniaco --------------------
class Maniaco {
    private int contador = 0;

    public void colocarBomba(Ubicacion u) {
        Bomba b = new Bomba(++contador, u);
        u.agregarBomba(b);
        System.out.println("⚠️ Maniaco colocó bomba en " + u.getNombre());
    }
}

// -------------------- Main --------------------
public class Main {

    public static void main(String[] args) {
        pruebaEquipo();
        pruebaManiaco();
        pruebaJuegoControlado();
        pruebaConcurrenteControlada();
    }

    // 🔍 Prueba clara de equipo
    public static void pruebaEquipo() {
        System.out.println("\n=== PRUEBA EQUIPO CONTROLADA ===");

        Ubicacion u = new Ubicacion("Metro");
        Bomba b = new Bomba(1, u);
        u.agregarBomba(b);

        Equipo eq = new Equipo(1,
                new Explorador("Explorador A"),
                new Especialista("Especialista A"),
                new Operador("Operador A"));

        eq.moverse(u);
        eq.buscarBomba(u);
    }

    // 😈 Prueba maniaco
    public static void pruebaManiaco() {
        System.out.println("\n=== PRUEBA MANIACO CONTROLADA ===");

        Ubicacion u = new Ubicacion("Aeropuerto");
        Maniaco m = new Maniaco();

        m.colocarBomba(u);
        m.colocarBomba(u);

        System.out.println("Total bombas: " + u.getBombas().size());
    }

    // 🎮 Prueba tipo simulación controlada
    public static void pruebaJuegoControlado() {
        System.out.println("\n=== PRUEBA JUEGO CONTROLADO ===");

        Ubicacion u = new Ubicacion("Hospital");
        Maniaco m = new Maniaco();

        m.colocarBomba(u);

        Equipo eq = new Equipo(2,
                new Explorador("Explorador B"),
                new Especialista("Especialista B"),
                new Operador("Operador B"));

        eq.moverse(u);
        eq.buscarBomba(u);
    }

    // 🔥 Prueba concurrente controlada
    public static void pruebaConcurrenteControlada() {
        System.out.println("\n=== PRUEBA CONCURRENTE CONTROLADA ===");

        Ubicacion u = new Ubicacion("Centro");

        Equipo eq1 = new Equipo(1,
                new Explorador("E1"),
                new Especialista("S1"),
                new Operador("O1"));

        Equipo eq2 = new Equipo(2,
                new Explorador("E2"),
                new Especialista("S2"),
                new Operador("O2"));

        Maniaco m = new Maniaco();

        Thread t1 = new Thread(() -> {
            eq1.moverse(u);
            eq1.buscarBomba(u);
        });

        Thread t2 = new Thread(() -> {
            eq2.moverse(u);
            eq2.buscarBomba(u);
        });

        Thread t3 = new Thread(() -> {
            m.colocarBomba(u);
        });
