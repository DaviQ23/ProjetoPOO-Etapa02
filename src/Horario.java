// Etapa 5 — Criar classe Horario para encapsular a gestão de tempo dos agendamentos
public class Horario {
    private String hora;

    public Horario(String hora) {
        if (hora == null || !hora.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Formato de horario invalido. Use HH:MM");
        }
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    @Override
    public String toString() {
        return hora;
    }
}