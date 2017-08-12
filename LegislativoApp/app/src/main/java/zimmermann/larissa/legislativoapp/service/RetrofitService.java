package zimmermann.larissa.legislativoapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zimmermann.larissa.legislativoapp.communication.PropListResponse;
import zimmermann.larissa.legislativoapp.communication.PropResponse;

/**
 * Created by laris on 11/08/2017.
 */

public interface RetrofitService {

    @GET("proposicoes")
    Call<PropListResponse> getDefaultProposicaoList();        //Unidade final

    @GET("proposicoes/{id}")
    Call<PropResponse> getProposicaoById(@Path("id") int id);

}
