// interface pra qualquer classe que precise gerar um resumo exportavel
// tanto Consulta quanto outras classes podem implementar isso
public interface Exportavel {

    // gera o resumo em texto - o padrao que a gente ja usa em toda classe
    String exibirResumo();

    // exporta num formato CSV basico - util pra relatorios ou salvar em arquivo
    String exportarCsv();
}
