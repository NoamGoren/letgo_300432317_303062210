package course.android.letgo_300432317_303062210.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import course.android.letgo_300432317_303062210.Fragments.ProductsDetailsFragment;
import course.android.letgo_300432317_303062210.R;

/**
 * Created by admin on 11/22/2016.
 */

public  class ProductsDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;


    protected int[] mImageResIds;
    protected String[] mNames;
    protected String[] mDescriptions;
    protected String[] mLocations;
    protected String[] mCategories;
    protected int[] mPrices;
    protected Context context;

    //Constructor
    public ProductsDataAdapter(Context context, int[] mImageResIds, String[] mNames, String[] mDescriptions,String[] mLocations,String[] mCategories,int[] mPrices) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mImageResIds=mImageResIds;
        this.mNames = mNames;
        this.mDescriptions = mDescriptions;
        this.mLocations = mLocations;
        this.mCategories = mCategories;
        this.mPrices = mPrices;
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.recycler_item, viewGroup, false));
    }
    //Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int imageResId = mImageResIds[position];
        final String name = mNames[position];
        final String description = mDescriptions[position];
        final String location = mLocations[position];
        final String category = mCategories[position];
        final int price = mPrices[position];
        viewHolder.setData(imageResId, name);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductsDetailsFragment detailsFragment = new ProductsDetailsFragment();
                // send data to fragment
                Bundle args = new Bundle();
                args.putInt(ProductsDetailsFragment.ARGUMENT_IMAGE_RES_ID, imageResId);
                args.putString(ProductsDetailsFragment.ARGUMENT_NAME, name);
                args.putString(ProductsDetailsFragment.ARGUMENT_DESCRIPTION, description);
                args.putString(ProductsDetailsFragment.ARGUMENT_LOCATION, location);
                args.putString(ProductsDetailsFragment.ARGUMENT_CATEGORY, category);
                args.putInt(ProductsDetailsFragment.ARGUMENT_PRICE, price);
                detailsFragment.setArguments(args);
                // open fragment
                FragmentManager fm = ((Activity)context).getFragmentManager();
                FragmentTransaction tr= fm.beginTransaction();
                tr.replace(R.id.root_layout, detailsFragment);
                tr.addToBackStack(null);
                tr .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

}
// ViewHolder class describes an item view and metadata about its place within the RecyclerView.
class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    public ImageView mImageView;
    public TextView mNameTextView;

    public ViewHolder(View itemView) {
        super(itemView);

        // Get references to image and name.
        mImageView = (ImageView) itemView.findViewById(R.id.image);
        mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    public void setData(int imageResId, String name) {
        mImageView.setImageResource(imageResId);
        mNameTextView.setText(name);
    }
}



