package zimmermann.larissa.legislativoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zimmermann.larissa.legislativoapp.R;

/**
 * Created by gms19 on 15/08/2017.
 */

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {

    private List<SpannableStringBuilder> dataList;
    private int rowLayout;


    public static class StringViewHolder extends RecyclerView.ViewHolder {
        TextView data;

        public StringViewHolder(View v) {
            super(v);
            data = (TextView) v.findViewById(R.id.data);
        }
    }

    public StringAdapter(List<SpannableStringBuilder> dataList, int rowLayout) {
        this.dataList = dataList;
        this.rowLayout = rowLayout;
    }

    @Override
    public StringAdapter.StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        StringAdapter.StringViewHolder holder = new StringAdapter.StringViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StringAdapter.StringViewHolder holder, final int position) {
        holder.data.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}