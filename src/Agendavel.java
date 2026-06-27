// Etapa 6 — Interface Agendavel para padronizar os compromissos da clínica
public interface Agendavel {
    String getData();
    String getHorario();
    void remarcar();
    void cancelar();
}