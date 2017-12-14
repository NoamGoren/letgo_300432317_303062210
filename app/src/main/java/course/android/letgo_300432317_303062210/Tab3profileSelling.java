package course.android.letgo_300432317_303062210;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ishai Levi on 11/28/2017.
 */

public class Tab3profileSelling extends Fragment {
    //creates and returns the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3profile_selling, container, false);

        return rootView;
    }
}