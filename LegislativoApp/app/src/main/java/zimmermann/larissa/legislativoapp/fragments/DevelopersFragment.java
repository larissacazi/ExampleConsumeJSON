package zimmermann.larissa.legislativoapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zimmermann.larissa.legislativoapp.R;

/**
 * Created by laris on 17/08/2017.
 */

public class DevelopersFragment extends Fragment {
    private Activity mActivity;

    @Override
    public void onAttach(Activity act) {
        super.onAttach(act);

        this.mActivity = act;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.developers_list, container, false);

        //do whatever you want here - like adding a listview or anything

        return view;
    }
}
