public class Profissional extends Pessoa {
    private static final String CPF_NAO_INFORMADO_PREFIXO = "CPF-NAO-INFORMADO-";
    private static final String SEM_REGISTRO = "";
    private static final int LIMITE_DIAS_DISPONIVEIS = 7;
    private static final double VALOR_PADRAO_CONSULTA = 0.0;

    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private String[] diasDisponiveis;
    private int totalDias;

    public Profissional(String nome, String especialidade) {
        super(nome, gerarCpfProvisorio(nome));
        setEspecialidade(especialidade);
        this.registroProfissional = SEM_REGISTRO;
        this.valorConsulta = VALOR_PADRAO_CONSULTA;
        inicializarDiasDisponiveis();
    }

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, gerarCpfProvisorio(nome));
        setEspecialidade(especialidade);
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        inicializarDiasDisponiveis();
    }

    public Profissional(String nome, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        super(nome, gerarCpfProvisorio(nome));
        setEspecialidade(especialidade);
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        inicializarDiasDisponiveis();
        adicionarDias(dias, totalDias);
    }

    public void atualizar(String registro, double valor) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
    }

    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
        inicializarDiasDisponiveis();
        adicionarDias(dias, totalDias);
    }

    public boolean atendeNoDia(String dia) {
        if (dia == null || dia.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < totalDias; i++) {
            if (diasDisponiveis[i] != null && diasDisponiveis[i].equalsIgnoreCase(dia.trim())) {
                return true;
            }
        }

        return false;
    }

    public static boolean especialidadeValida(String esp) {
        if (esp == null || esp.trim().isEmpty()) {
            return false;
        }

        String especialidadeNormalizada = esp.trim();

        if (especialidadeNormalizada.equalsIgnoreCase("clinica geral")) return true;
        if (especialidadeNormalizada.equalsIgnoreCase("fisioterapia")) return true;
        if (especialidadeNormalizada.equalsIgnoreCase("psicologia")) return true;
        if (especialidadeNormalizada.equalsIgnoreCase("nutricao")) return true;

        return false;
    }

    public boolean possuiCadastroCompleto() {
        return possuiRegistro() && valorConsulta > 0 && totalDias > 0;
    }

    public boolean possuiRegistro() {
        return registroProfissional != null && !registroProfissional.trim().isEmpty();
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade nao pode ser vazia.");
        }

        this.especialidade = especialidade.trim();
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.trim().isEmpty()) {
            this.registroProfissional = SEM_REGISTRO;
        } else {
            this.registroProfissional = registroProfissional.trim();
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

    private void inicializarDiasDisponiveis() {
        this.diasDisponiveis = new String[LIMITE_DIAS_DISPONIVEIS];
        this.totalDias = 0;
    }

    private void adicionarDias(String[] dias, int totalDias) {
        if (dias == null || totalDias <= 0) {
            return;
        }

        for (int i = 0; i < totalDias && i < this.diasDisponiveis.length; i++) {
            if (dias[i] != null && !dias[i].trim().isEmpty()) {
                this.diasDisponiveis[this.totalDias] = dias[i].trim();
                this.totalDias++;
            }
        }
    }

    private static String gerarCpfProvisorio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return CPF_NAO_INFORMADO_PREFIXO + "PROFISSIONAL";
        }

        return CPF_NAO_INFORMADO_PREFIXO + nome.trim();
    }

    protected String gerarResumoProfissional() {
        return "Nome: " + nome
                + " | Espec: " + especialidade
                + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta;
    }

    private String obterDiasFormatados() {
        if (totalDias == 0) {
            return "Nenhum";
        }

        String dias = "";

        for (int i = 0; i < totalDias; i++) {
            if (i > 0) {
                dias = dias + ", ";
            }

            dias = dias + diasDisponiveis[i];
        }

        return dias;
    }

    // SOBRESCRITA: Profissional redefine o comportamento abstrato herdado de Pessoa.
    @Override
    public String exibirResumo() {
        return gerarResumoProfissional() + " | Dias: " + obterDiasFormatados();
    }
}