package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import course.android.letgo_300432317_303062210.R;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Ishai Levi on 11/20/2017.
 */

public class ProductsListFragment extends Fragment {

    private int[] mImageResIds;
    private String[] mNames;
    private String[] mDescriptions;
    private TypedArray typedArray;

    public void changeFragment(String[] mNames,String[] mDescriptions,TypedArray typedArray,Activity context){

        final Resources resources = context.getResources();
        this.mNames = resources.getStringArray(R.array.car_names);
        this.mDescriptions = resources.getStringArray(R.array.car_descriptions);
        typedArray = resources.obtainTypedArray(R.array.cars);

    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);


        Bundle bundle = this.getArguments();
        final Resources resources = context.getResources();

        if (bundle!=null) {
            int myValue = bundle.getInt("message");
            switch (myValue) {
                case 1:
                    mNames = resources.getStringArray(R.array.car_names);
                    mDescriptions = resources.getStringArray(R.array.car_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.cars);
                    break;
                case 2:
                    mNames = resources.getStringArray(R.array.home_names);
                    mDescriptions = resources.getStringArray(R.array.home_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.home);
                    break;
                case 3:
                    mNames = resources.getStringArray(R.array.motors_names);
                    mDescriptions = resources.getStringArray(R.array.motors_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.motors);
                    break;
                case 4:
                    mNames = resources.getStringArray(R.array.fashion_names);
                    mDescriptions = resources.getStringArray(R.array.fashion_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.fashion);
                    break;
                case 5:
                    mNames = resources.getStringArray(R.array.other_names);
                    mDescriptions = resources.getStringArray(R.array.other_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.other);
                    break;
                case 6:
                    mNames = resources.getStringArray(R.array.child_names);
                    mDescriptions = resources.getStringArray(R.array.child_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.child);
                    break;
                case 7:
                    mNames = resources.getStringArray(R.array.entertiment_names);
                    mDescriptions = resources.getStringArray(R.array.entertiment_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.entertiment);
                    break;
                case 8:
                    mNames = resources.getStringArray(R.array.leisure_names);
                    mDescriptions = resources.getStringArray(R.array.leisure_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.leisure);
                    break;
            }
        }
        else {
            mNames = resources.getStringArray(R.array.all_names);
            mDescriptions = resources.getStringArray(R.array.all_descriptions);
            // Get images.
            typedArray = resources.obtainTypedArray(R.array.all);
        }


//        if (bundle!=null){
//            int myValue = bundle.getInt("message");
//            mNames = resources.getStringArray(R.array.car_names);
//            mDescriptions = resources.getStringArray(R.array.car_descriptions);
//            typedArray = resources.obtainTypedArray(R.array.cars);
//        }
//
//        else{
//            mNames = resources.getStringArray(R.array.names);
//            mDescriptions = resources.getStringArray(R.array.descriptions);
//            // Get images.
//             typedArray = resources.obtainTypedArray(R.array.images);
//        }


        final int imageCount = mNames.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        Activity activity = getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(new ProductsDataAdapter(activity, mImageResIds, mNames, mDescriptions));
        return view;
    }


}
