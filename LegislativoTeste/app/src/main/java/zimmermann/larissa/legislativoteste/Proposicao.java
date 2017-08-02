package zimmermann.larissa.legislativoteste;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by laris on 01/08/2017.
 */

public class Proposicao implements Serializable{
    //@SerializedName("id")
    private int id;
    //@SerializedName("uri")
    private String uri;
    //@SerializedName("siglaTipo")
    private String sigla;
    //@SerializedName("idTipo")
    private int idTipo;
    //@SerializedName("numero")
    private int numero;
    //@SerializedName("ano")
    private int ano;
    //@SerializedName("ementa")
    private String ementa;

    public Proposicao(int id, String uri, String sigla, int idTipo, int numero, int ano, String ementa) {
        this.id = id;
        this.uri = uri;
        this.sigla = sigla;
        this.idTipo = idTipo;
        this.numero = numero;
        this.ano = ano;
        this.ementa = ementa;
    }

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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
}
