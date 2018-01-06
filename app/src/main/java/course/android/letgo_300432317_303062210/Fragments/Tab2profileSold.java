package course.android.letgo_300432317_303062210.Fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import course.android.letgo_300432317_303062210.R;

/**
 * Created by Ishai Levi on 11/28/2017.
 */

public class Tab2profileSold extends Fragment {

    private Activity ctx;

    //creates and returns the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2profile_sold, container, false);

        ctx = getActivity();

//        ItemListFragment f1 = new ItemListFragment();
//        FragmentManager fManager =ctx.getFragmentManager();
//        FragmentTransaction ft = fManager.beginTransaction();
//        ft.replace(R.id.content2_frame, f1);
//        ft.addToBackStack(null);
//        ft.commit();

        return rootView;
    }
}