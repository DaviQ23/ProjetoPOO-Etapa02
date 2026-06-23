public class Profissional extends Pessoa {
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private String[] diasDisponiveis;
    private int totalDias;

    public Profissional(String nome, String especialidade) {
        super(nome, "CPF-NAO-INFORMADO-" + nome);
        setEspecialidade(especialidade);
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, "CPF-NAO-INFORMADO-" + nome);
        setEspecialidade(especialidade);
        this.registroProfissional = registroProfissional;
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    public Profissional(String nome, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        super(nome, "CPF-NAO-INFORMADO-" + nome);
        setEspecialidade(especialidade);
        this.registroProfissional = registroProfissional;
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;

        for (int i = 0; i < totalDias && i < this.diasDisponiveis.length; i++) {
            this.diasDisponiveis[i] = dias[i];
            this.totalDias++;
        }
    }

    public void atualizar(String registro, double valor) {
        this.registroProfissional = registro;
        setValorConsulta(valor);
    }

    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        this.registroProfissional = registro;
        setValorConsulta(valor);
        this.totalDias = 0;

        for (int i = 0; i < totalDias && i < this.diasDisponiveis.length; i++) {
            this.diasDisponiveis[i] = dias[i];
            this.totalDias++;
        }
    }

    public boolean atendeNoDia(String dia) {
        for (int i = 0; i < totalDias; i++) {
            if (diasDisponiveis[i] != null && diasDisponiveis[i].equalsIgnoreCase(dia)) {
                return true;
            }
        }
        return false;
    }

    public static boolean especialidadeValida(String esp) {
        if (esp == null) {
            return false;
        }

        if (esp.equalsIgnoreCase("clinica geral")) return true;
        if (esp.equalsIgnoreCase("fisioterapia")) return true;
        if (esp.equalsIgnoreCase("psicologia")) return true;
        if (esp.equalsIgnoreCase("nutricao")) return true;

        return false;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade nao pode ser vazia.");
        }

        this.especialidade = especialidade;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null) {
            this.registroProfissional = "";
        } else {
            this.registroProfissional = registroProfissional;
        }
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        if (valorConsulta < 0) {
            throw new IllegalArgumentException("Valor da consulta nao pode ser negativo.");
        }

        this.valorConsulta = valorConsulta;
    }

    public int getTotalDias() {
        return totalDias;
    }

    public String getDiaDisponivel(int indice) {
        if (indice < 0 || indice >= totalDias) {
            return "";
        }

        return diasDisponiveis[indice];
    }

    public String[] getDiasDisponiveis() {
        String[] copia = new String[totalDias];

        for (int i = 0; i < totalDias; i++) {
            copia[i] = diasDisponiveis[i];
        }

        return copia;
    }

    protected String gerarResumoProfissional() {
        return "Nome: " + nome
                + " | Espec: " + especialidade
                + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta;
    }

    // SOBRESCRITA: Profissional redefine o comportamento abstrato herdado de Pessoa.
    @Override
    public String exibirResumo() {
        String dias = "";

        for (int i = 0; i < totalDias; i++) {
            if (i > 0) {
                dias = dias + ", ";
            }

            dias = dias + diasDisponiveis[i];
        }

        return gerarResumoProfissional() + " | Dias: " + dias;
    }
}