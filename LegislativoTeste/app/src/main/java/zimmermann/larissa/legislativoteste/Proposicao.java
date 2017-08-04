package zimmermann.larissa.legislativoteste;

/**
 * Created by laris on 01/08/2017.
 */

public class Proposicao{

    private int id;
    private String uri;
    private String siglaTipo;
    private int idTipo;
    private int numero;
    private int ano;
    private String ementa;
    private String dataApresentacao;
    private String uriOrgaoNumerador;
    private String uriUltimoRelator;
    private StatusProposicao statusProposicao;
    private String tipoAutor;
    private int idTipoAutor;
    private String uriAutores;
    private String descricaoTipo;
    private String ementaDetalhada;
    private String keywords;
    private String uriPropPrincipal;
    private String uriPropAnterior;
    private String uriPropPosterior;
    private String urlInteiroTeor;
    private String urnFinal;
    private String texto;
    private String justificativa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSiglaTipo() {
        return siglaTipo;
    }

    public void setSiglaTipo(String siglaTipo) {
        this.siglaTipo = siglaTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(String dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public String getUriOrgaoNumerador() {
        return uriOrgaoNumerador;
    }

    public void setUriOrgaoNumerador(String uriOrgaoNumerador) {
        this.uriOrgaoNumerador = uriOrgaoNumerador;
    }

    public String getUriUltimoRelator() {
        return uriUltimoRelator;
    }

    public void setUriUltimoRelator(String uriUltimoRelator) {
        this.uriUltimoRelator = uriUltimoRelator;
    }

    public StatusProposicao getStatusProposicao() {
        return statusProposicao;
    }

    public void setStatusProposicao(StatusProposicao statusProposicao) {
        this.statusProposicao = statusProposicao;
    }

    public String getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(String tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public int getIdTipoAutor() {
        return idTipoAutor;
    }

    public void setIdTipoAutor(int idTipoAutor) {
        this.idTipoAutor = idTipoAutor;
    }

    public String getUriAutores() {
        return uriAutores;
    }

    public void setUriAutores(String uriAutores) {
        this.uriAutores = uriAutores;
    }

    public String getDescricaoTipo() {
        return descricaoTipo;
    }

    public void setDescricaoTipo(String descricaoTipo) {
        this.descricaoTipo = descricaoTipo;
    }

    public String getEmentaDetalhada() {
        return ementaDetalhada;
    }

    public void setEmentaDetalhada(String ementaDetalhada) {
        this.ementaDetalhada = ementaDetalhada;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUriPropPrincipal() {
        return uriPropPrincipal;
    }

    public void setUriPropPrincipal(String uriPropPrincipal) {
        this.uriPropPrincipal = uriPropPrincipal;
    }

    public String getUriPropAnterior() {
        return uriPropAnterior;
    }

    public void setUriPropAnterior(String uriPropAnterior) {
        this.uriPropAnterior = uriPropAnterior;
    }

    public String getUriPropPosterior() {
        return uriPropPosterior;
    }

    public void setUriPropPosterior(String uriPropPosterior) {
        this.uriPropPosterior = uriPropPosterior;
    }

    public String getUrlInteiroTeor() {
        return urlInteiroTeor;
    }

    public void setUrlInteiroTeor(String urlInteiroTeor) {
        this.urlInteiroTeor = urlInteiroTeor;
    }

    public String getUrnFinal() {
        return urnFinal;
    }

    public void setUrnFinal(String urnFinal) {
        this.urnFinal = urnFinal;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
