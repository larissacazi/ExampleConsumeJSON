package zimmermann.larissa.legislativoapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;

import zimmermann.larissa.legislativoapp.communication.Deputado;
import zimmermann.larissa.legislativoapp.communication.DeputadoListResponse;

/**
 * Created by laris on 22/08/2017.
 */

public class DepConnectionService extends AsyncTask<String, Void, DeputadoListResponse> {

    public interface depCallBack {
        public void updateDeps(DeputadoListResponse responseFromServer);
    }

    private depCallBack callback;

    public void registerCallback(depCallBack callback){
        this.callback = callback;
    }

    @Override
    protected DeputadoListResponse doInBackground(String... sURL) {
        try {
            URL url = new URL(sURL[0]);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            DeputadoListResponse respostaServidor = new Gson().fromJson(reader, DeputadoListResponse.class);
            return respostaServidor;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(DeputadoListResponse result) {
        callback.updateDeps(result);
    }
}