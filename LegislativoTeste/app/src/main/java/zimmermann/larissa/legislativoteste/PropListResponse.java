package zimmermann.larissa.legislativoteste;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoteste.rest.StringUtils;

/**
 * Created by laris on 01/08/2017.
 */

public class PropListResponse implements Serializable{

    private List<Proposicao> dados;
    private List<Link> links;

    public PropListResponse(List<Proposicao> dados, List<Link> links) {
        this.dados = dados;
        this.links = links;
    }

    public List<Proposicao> getDados() {
        return dados;
    }

    public void setDados(List<Proposicao> dados) {
        this.dados = dados;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
