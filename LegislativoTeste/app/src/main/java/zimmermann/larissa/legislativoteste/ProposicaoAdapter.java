package zimmermann.larissa.legislativoteste;

import android.content.Context;
import android.support.v4.provider.DocumentFile;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.uncopt.android.widget.text.justify.JustifiedTextView;


import com.bluejamesbond.text.DocumentView;
//import com.codesgood.views.JustifiedTextView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import zimmermann.larissa.legislativoteste.rest.StringUtils;

public class ProposicaoAdapter extends RecyclerView.Adapter<ProposicaoAdapter.ProposicaoViewHolder> {

    private List<Proposicao> props;
    private int rowLayout;
    private Context context;


    public static class ProposicaoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout propsLayout;
        TextView dadosLabel;
        TextView dados;
        DocumentView ementa;

        public ProposicaoViewHolder(View v) {
            super(v);
            propsLayout = (LinearLayout) v.findViewById(R.id.proposicao_layout);
            dadosLabel = (TextView) v.findViewById(R.id.dadosLabel);
            dados = (TextView) v.findViewById(R.id.dados);
            ementa = (DocumentView) v.findViewById(R.id.ementa);
        }
    }

    public ProposicaoAdapter(List<Proposicao> props, int rowLayout, Context context) {
        this.props = props;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ProposicaoAdapter.ProposicaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProposicaoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProposicaoViewHolder holder, final int position) {

        holder.ementa.setText("Ementa: " + props.get(position).getEmenta().toUpperCase());

        holder.dadosLabel.setText("Número: ");
        holder.dados.setText(props.get(position).getId() +
                             " - Ano: " + props.get(position).getAno());

    }

    @Override
    public int getItemCount() {
        return props.size();
    }
}