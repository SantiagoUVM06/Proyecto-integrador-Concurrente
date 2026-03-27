import java.util.*;

// -------------------- Clase Miembro --------------------
class Miembro {
protected String nombre;

public Miembro(String nombre) {
    this.nombre = nombre;
}

public void realizarAccion() {
    System.out.println(nombre + " está realizando una acción.");
}

}

// -------------------- Subclases --------------------
class Explorador extends Miembro {
public Explorador(String nombre) {
super(nombre);
}

public boolean detectarBomba() {
    System.out.println(nombre + " está buscando bombas...");
    return new Random().nextBoolean();
}

}

class Especialista extends Miembro {
public Especialista(String nombre) {
super(nombre);
}

public void desactivar() {
    System.out.println(nombre + " está desactivando la bomba...");
}

}

class Operador extends Miembro {
public Operador(String nombre) {
super(nombre);
}

public void manejarEquipo() {
    System.out.println(nombre + " está preparando el equipo...");
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

public void explotar() {
    if (activa) {
        System.out.println("💥 Bomba " + id + " explotó en " + ubicacion.getNombre());
        activa = false;
    }
}

public boolean isActiva() {
    return activa;
}

public void desactivar() {
    activa = false;
    System.out.println("✅ Bomba " + id + " desactivada.");
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

public void removerBomba(Bomba b) {
    bombas.remove(b);
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
private String estado;

public Equipo(int id, Explorador e, Especialista s, Operador o) {
    this.idEquipo = id;
    this.explorador = e;
    this.especialista = s;
    this.operador = o;
    this.estado = "Inactivo";
}

public void buscarBomba(Ubicacion u) {
    estado = "Buscando";
    if (explorador.detectarBomba() && !u.getBombas().isEmpty()) {
        System.out.println("Equipo " + idEquipo + " encontró una bomba.");
        desactivarBomba(u.getBombas().get(0));
    } else {
        System.out.println("Equipo " + idEquipo + " no encontró nada.");
    }
}

public void desactivarBomba(Bomba b) {
    estado = "Desactivando";
    operador.manejarEquipo();
    especialista.desactivar();
    b.desactivar();
}

public void moverse(Ubicacion u) {
    estado = "Moviéndose";
    System.out.println("Equipo " + idEquipo + " se mueve a " + u.getNombre());
}

}

// -------------------- Clase Maniaco --------------------
class Maniaco {
private List<Bomba> listaBombas;
private int contador = 0;

public Maniaco() {
    listaBombas = new ArrayList<>();
}

public void colocarBomba(Ubicacion u) {
    Bomba b = new Bomba(++contador, u);
    listaBombas.add(b);
    u.agregarBomba(b);
    System.out.println("⚠️ Maniaco colocó bomba en " + u.getNombre());
}

}

// -------------------- Clase Juego --------------------
class Juego {
private List<Equipo> equipos;
private Maniaco maniaco;
private List<Ubicacion> ubicaciones;

public Juego() {
    equipos = new ArrayList<>();
    maniaco = new Maniaco();
    ubicaciones = new ArrayList<>();
}

public void iniciarSimulacion() {
    System.out.println("🚨 Simulación iniciada 🚨");

    // Crear ubicaciones
    Ubicacion u1 = new Ubicacion("Metro");
    Ubicacion u2 = new Ubicacion("Centro Comercial");
    ubicaciones.add(u1);
    ubicaciones.add(u2);

    // Crear equipos
    equipos.add(new Equipo(1, new Explorador("E1"), new Especialista("S1"), new Operador("O1")));
    equipos.add(new Equipo(2, new Explorador("E2"), new Especialista("S2"), new Operador("O2")));

    // Simulación básica
    maniaco.colocarBomba(u1);
    maniaco.colocarBomba(u2);

    for (Equipo eq : equipos) {
        eq.moverse(u1);
        eq.buscarBomba(u1);
    }
}

}
// -------------------- Main --------------------
public class Main {
public static void main(String[] args) {
Juego juego = new Juego();
juego.iniciarSimulacion();
}
}
