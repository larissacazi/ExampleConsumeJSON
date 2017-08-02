package zimmermann.larissa.legislativoteste;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by laris on 01/08/2017.
 */

public interface RetrofitService {

    @GET("proposicoes")
    Call<Data> getProposicaoList();        //Unidade final
}
