package zimmermann.larissa.legislativoapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;

import zimmermann.larissa.legislativoapp.communication.PropListResponse;

/**
 * Created by laris on 22/08/2017.
 */

public class PropConnectionService extends AsyncTask<String, Integer, PropListResponse> {

    public interface propCallBack {
        public void updateProps(PropListResponse responseFromServer);
    }

    private propCallBack callback;

    public void registerCallback(propCallBack callback){
        this.callback = callback;
    }

    @Override
    protected PropListResponse doInBackground(String... sURL) {
        try {
            URL url = new URL(sURL[0]);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            PropListResponse respostaServidor = new Gson().fromJson(reader, PropListResponse.class);
            return respostaServidor;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(PropListResponse result) {
        callback.updateProps(result);
    }
}
