package zimmermann.larissa.legislativoapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import zimmermann.larissa.legislativoapp.R;
import zimmermann.larissa.legislativoapp.communication.Deputado;
import zimmermann.larissa.legislativoapp.communication.Situation;

import static zimmermann.larissa.legislativoapp.R.id.imageView;

/**
 * Created by laris on 16/08/2017.
 */

public class DeputadoAdapter extends RecyclerView.Adapter<DeputadoAdapter.DeputadoViewHolder> {

    private List<Deputado> deputadoList;
    private int rowLayout;
    private Context context;


    public static class DeputadoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout deputadoLayout;
        ImageView fotoDeputado;
        TextView nomeDeputado;
        TextView partidoDeputado;
        TextView ufDeputado;

        public DeputadoViewHolder(View v) {
            super(v);
            deputadoLayout = (LinearLayout) v.findViewById(R.id.deputado_list_layout);

            fotoDeputado = (ImageView) v.findViewById(R.id.fotoDeputado);
            nomeDeputado = (TextView) v.findViewById(R.id.nomeDeputado);
            partidoDeputado = (TextView) v.findViewById(R.id.partidoDeputado);
            ufDeputado = (TextView) v.findViewById(R.id.ufDeputado);
        }
    }

    public DeputadoAdapter(List<Deputado> deputadoList, int rowLayout, Context context) {
        this.deputadoList = deputadoList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public DeputadoAdapter.DeputadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        DeputadoAdapter.DeputadoViewHolder holder = new DeputadoAdapter.DeputadoViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final DeputadoAdapter.DeputadoViewHolder holder, final int position) {

        Picasso.with(this.context).load(deputadoList.get(position).getUrlFoto()).into(holder.fotoDeputado);

        Log.d("MainActivity", "Enter::fotoDeputado!");

        holder.nomeDeputado.setText("Nome:  " + deputadoList.get(position).getNome());
        holder.partidoDeputado.setText("Partido:    " + deputadoList.get(position).getSiglaPartido());
        holder.ufDeputado.setText("UF:    " + deputadoList.get(position).getSiglaUf());
    }

    @Override
    public int getItemCount() {
        return deputadoList.size();
    }

    @Override
    public long getItemId(int position) {
        return deputadoList.get(position).getId();
    }

}