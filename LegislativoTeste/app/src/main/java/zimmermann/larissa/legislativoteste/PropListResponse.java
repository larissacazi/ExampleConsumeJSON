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

    public void fillData(){
        for(int i = 0; i < dados.size(); i++){
            final int pos = i;
            int id = dados.get(pos).getId();
            RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
            Call<PropResponse> call = service.getProposicaoById(id);

            call.enqueue(new Callback<PropResponse>() {
                @Override
                public void onResponse(Call<PropResponse> call, Response<PropResponse> response) {

                    Log.d("MainActivity", "Recebeu resposta.");
                    if (response.isSuccessful()) {
                        Log.d("MainActivity", "Resposta recebida com sucesso.");
                        PropResponse respostaServidor = response.body();

                        Log.d("MainActivity", "Response saved!");

                        //verifica aqui se o corpo da resposta não é nulo
                        if (respostaServidor != null) {
                            Log.d("MainActivity", "PropResponse structure received!");
                            dados.set(pos,respostaServidor.getDados());
                            dados.get(pos).setEmenta(new StringUtils().format((dados.get(pos).getEmenta())));
                        } else {
                            Log.d("MainActivity", "Error onResponse()");
                        }
                    } else {

                        Log.d("MainActivity", "Error OnResponse()");
                        // segura os erros de requisição
                        ResponseBody errorBody = response.errorBody();
                    }
                }

                @Override
                public void onFailure(Call<PropResponse> call, Throwable t) {
                    Log.d("MainActivity", "Error OnFailure()");
                }
            });
        }
    }
}
