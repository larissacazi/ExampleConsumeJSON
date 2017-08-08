package zimmermann.larissa.legislativoteste;

import android.util.Log;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by laris on 01/08/2017.
 */

public interface RetrofitService {

    @GET("proposicoes")
    Call<PropListResponse> getDefaultProposicaoList();        //Unidade final

    @GET("proposicoes/{id}")
    Call<PropResponse> getProposicaoById(@Path("id") int id);

}
