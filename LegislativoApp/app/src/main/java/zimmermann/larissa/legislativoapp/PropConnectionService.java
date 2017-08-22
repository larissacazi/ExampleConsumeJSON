package zimmermann.larissa.legislativoapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import zimmermann.larissa.legislativoapp.communication.PropListResponse;

/**
 * Created by laris on 22/08/2017.
 */

public class PropConnectionService extends AsyncTask<String, Void, PropListResponse>{

    @Override
    protected PropListResponse doInBackground(String... sURL) {
        try {
            URL url = new URL(sURL[0]);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            PropListResponse respostaServidor = new Gson().fromJson(reader, PropListResponse.class);
            return respostaServidor;
        } catch (Exception e){
            Log.d("MainActivity", "loadPropsFromUrl::doInBackground: " + e.toString());

            return null;
        }
    }
}
