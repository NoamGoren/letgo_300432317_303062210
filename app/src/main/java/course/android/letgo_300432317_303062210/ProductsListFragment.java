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
    private String[] mLocations;
    private String[] mCategories;
    private int[] mPrices;
    private TypedArray typedArray;

    //Called when a fragment is first attached to its context.
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);


        Bundle bundle = this.getArguments();
        final Resources resources = context.getResources();
        //Cases for the 8 Categories
        if (bundle!=null) {
            int myValue = bundle.getInt("message");
            switch (myValue) {
                case 1:
                    mNames = resources.getStringArray(R.array.car_names);
                    mDescriptions = resources.getStringArray(R.array.car_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.cars);
                    mLocations= resources.getStringArray(R.array.car_locations);
                    mCategories= resources.getStringArray(R.array.car_cagegories);
                    mPrices= resources.getIntArray(R.array.car_prices);
                    break;
                case 2:
                    mNames = resources.getStringArray(R.array.home_names);
                    mDescriptions = resources.getStringArray(R.array.home_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.home);
                    mLocations= resources.getStringArray(R.array.home_locations);
                    mCategories= resources.getStringArray(R.array.home_cagegories);
                    mPrices= resources.getIntArray(R.array.home_prices);
                    break;
                case 3:
                    mNames = resources.getStringArray(R.array.motors_names);
                    mDescriptions = resources.getStringArray(R.array.motors_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.motors);
                    mLocations= resources.getStringArray(R.array.motors_locations);
                    mCategories= resources.getStringArray(R.array.motors_cagegories);
                    mPrices= resources.getIntArray(R.array.motors_prices);
                    break;
                case 4:
                    mNames = resources.getStringArray(R.array.fashion_names);
                    mDescriptions = resources.getStringArray(R.array.fashion_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.fashion);
                    mLocations= resources.getStringArray(R.array.fashion_locations);
                    mCategories= resources.getStringArray(R.array.fashion_cagegories);
                    mPrices= resources.getIntArray(R.array.fashion_prices);
                    break;
                case 5:
                    mNames = resources.getStringArray(R.array.other_names);
                    mDescriptions = resources.getStringArray(R.array.other_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.other);
                    mLocations= resources.getStringArray(R.array.other_locations);
                    mCategories= resources.getStringArray(R.array.other_cagegories);
                    mPrices= resources.getIntArray(R.array.other_prices);
                    break;
                case 6:
                    mNames = resources.getStringArray(R.array.child_names);
                    mDescriptions = resources.getStringArray(R.array.child_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.child);
                    mLocations= resources.getStringArray(R.array.child_locations);
                    mCategories= resources.getStringArray(R.array.child_cagegories);
                    mPrices= resources.getIntArray(R.array.child_prices);
                    break;
                case 7:
                    mNames = resources.getStringArray(R.array.entertiment_names);
                    mDescriptions = resources.getStringArray(R.array.entertiment_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.entertiment);
                    mLocations= resources.getStringArray(R.array.entertiment_locations);
                    mCategories= resources.getStringArray(R.array.entertiment_cagegories);
                    mPrices= resources.getIntArray(R.array.entertiment_prices);
                    break;
                case 8:
                    mNames = resources.getStringArray(R.array.leisure_names);
                    mDescriptions = resources.getStringArray(R.array.leisure_descriptions);
                    typedArray = resources.obtainTypedArray(R.array.leisure);
                    mLocations= resources.getStringArray(R.array.leisure_locations);
                    mCategories= resources.getStringArray(R.array.leisure_cagegories);
                    mPrices= resources.getIntArray(R.array.leisure_prices);
                    break;
            }
        }
        else {
            mNames = resources.getStringArray(R.array.all_names);
            mDescriptions = resources.getStringArray(R.array.all_descriptions);
            mLocations= resources.getStringArray(R.array.all_locations);
            mCategories= resources.getStringArray(R.array.all_cagegories);
            mPrices= resources.getIntArray(R.array.all_prices);

            // Get images.
            typedArray = resources.obtainTypedArray(R.array.all);
        }




        final int imageCount = mNames.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
    }

    //creates and returns the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        Activity activity = getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(new ProductsDataAdapter(activity, mImageResIds, mNames, mDescriptions,mLocations,mCategories,mPrices));
        return view;
    }


}
