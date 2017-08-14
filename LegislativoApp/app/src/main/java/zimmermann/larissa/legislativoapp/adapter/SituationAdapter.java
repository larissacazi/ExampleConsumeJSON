package zimmermann.larissa.legislativoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zimmermann.larissa.legislativoapp.R;
import zimmermann.larissa.legislativoapp.communication.Situation;

/**
 * Created by laris on 14/08/2017.
 */

public class SituationAdapter extends RecyclerView.Adapter<SituationAdapter.SituationViewHolder> {

    private List<Situation> situationList;
    private int rowLayout;
    private Context context;


    public static class SituationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout situationLayout;
        TextView id;
        TextView nome;

        public SituationViewHolder(View v) {
            super(v);
            situationLayout = (LinearLayout) v.findViewById(R.id.situation_prop_list_layout);
            id = (TextView) v.findViewById(R.id.id_situation_prop_list);
            nome = (TextView) v.findViewById(R.id.nome_situation_prop_list);
        }
    }

    public SituationAdapter(List<Situation> situationList, int rowLayout, Context context) {
        this.situationList = situationList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SituationAdapter.SituationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        SituationAdapter.SituationViewHolder holder = new SituationAdapter.SituationViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final SituationAdapter.SituationViewHolder holder, final int position) {
        holder.nome.setText("Situação: " + situationList.get(position).getNome());
        holder.id.setText("ID: " + String.valueOf(situationList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return situationList.size();
    }

    @Override
    public long getItemId(int position) {
        return situationList.get(position).getId();
    }

}