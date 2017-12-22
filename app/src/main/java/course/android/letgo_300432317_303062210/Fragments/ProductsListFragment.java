package course.android.letgo_300432317_303062210.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_300432317_303062210.Adapters.ProductsDataAdapter;
import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.DB.MyInfoManager;
import course.android.letgo_300432317_303062210.R;

/**
 * Created by Ishai Levi on 11/20/2017.
 */

public class ProductsListFragment extends Fragment {

    private Bitmap[] mImageResIds;
    private String[] mNames;
    private String[] mDescriptions;
    private String[] mLocations;
    private String[] mCategories;
    private int[] mPrices;
    private TypedArray typedArray;
    private List<Product> p1;


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
                    CreateResultArray("Cars");
                    break;
                case 2:
                    CreateResultArray("Home");
                    break;
                case 3:
                    CreateResultArray("Motors");
                    break;
                case 4:
                    CreateResultArray("Fashion");
                    break;
                case 5:
                    CreateResultArray("Other");
                    break;
                case 6:
                  CreateResultArray("Child");
                    break;
                case 7:
                   CreateResultArray("Entertaiment");
                    break;
                case 8:
                   CreateResultArray("Leisure");
                    break;
            }
        }
        else {
            CreateResultArray("All");


            // mNames = resources.getStringArray(R.array.all_names);
           // mDescriptions = resources.getStringArray(R.array.all_descriptions);
           // mLocations= resources.getStringArray(R.array.all_locations);
           // mCategories= resources.getStringArray(R.array.all_cagegories);
           // mPrices= resources.getIntArray(R.array.all_prices);

            // Get images.
//            typedArray = resources.obtainTypedArray(R.array.all);
        }


//        final int imageCount = mNames.length;
//        mImageResIds = new int[imageCount];
//        for (int i = 0; i < imageCount; i++) {
//            mImageResIds[i] = typedArray.getResourceId(i, 0);
//        }
//        typedArray.recycle();
    }

    public void CreateResultArray(String res){

        p1= MyInfoManager.getInstance().getAllItems();
        List<Product> p2=new ArrayList<Product>();
        if (res!="All"){
            for (Product item: p1) {
                if (item.getCategory().matches(res)){
                    p2.add(item);
                }

            }
        }else{
            for (Product item: p1) {
                    p2.add(item);
            }
        }

        int i=0;
        p2.size();
        mImageResIds = new Bitmap[p2.size()];
        mNames=new String[p2.size()];
        mDescriptions= new String[p2.size()];
        mLocations= new String[p2.size()];
        mCategories= new String[p2.size()];
        mPrices= new int[p2.size()];

        for (Product item:p2) {

            mNames[i]=item.getTitle();
            mImageResIds[i]=item.getImage1();
            mDescriptions[i]=item.getDescription();
            mLocations[i]=item.getLocation();
            mCategories[i]=item.getCategory();
            mPrices[i]=item.getPrice();

            i++;


        }
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
