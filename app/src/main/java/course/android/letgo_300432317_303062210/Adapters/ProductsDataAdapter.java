package course.android.letgo_300432317_303062210.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import course.android.letgo_300432317_303062210.Fragments.ProductsDetailsFragment;
import course.android.letgo_300432317_303062210.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by admin on 11/22/2016.
 */

public  class ProductsDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;

    protected String[] mId;
    protected String[] mNames;
    protected String[] mDescriptions;
    protected String[] mPrices;
    protected String[] mLocations;
    protected String[] mCategories;
    protected Bitmap[] mImageResIds;
    protected String[] mUserId;

    protected Context context;

    //Constructor
    public ProductsDataAdapter(Context context, String[] mId, String[] mNames, String[] mDescriptions,String[] mPrices,String[] mLocations,String[] mCategories,Bitmap[] mImageResIds,String[] mUserId) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mImageResIds=mImageResIds;
        this.mNames = mNames;
        this.mDescriptions = mDescriptions;
        this.mLocations = mLocations;
        this.mCategories = mCategories;
        this.mPrices = mPrices;
        this.mId = mId;
        this.mUserId = mUserId;
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.recycler_item, viewGroup, false));
    }
    //Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final String id = mId[position];
        final String name = mNames[position];
        final String description = mDescriptions[position];
        final String price = mPrices[position];
        final String location = mLocations[position];
        final String category = mCategories[position];
        final Bitmap imageResId = mImageResIds[position];
        final String userId = mUserId[position];
        viewHolder.setData(imageResId, name);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductsDetailsFragment detailsFragment = new ProductsDetailsFragment();
                // send data to fragment
                Bundle args = new Bundle();
                args.putString(ProductsDetailsFragment.ARGUMENT_ID, id);
                args.putString(ProductsDetailsFragment.ARGUMENT_NAME, name);
                args.putString(ProductsDetailsFragment.ARGUMENT_DESCRIPTION, description);
                args.putString(ProductsDetailsFragment.ARGUMENT_PRICE, price);
                args.putString(ProductsDetailsFragment.ARGUMENT_LOCATION, location);
                args.putString(ProductsDetailsFragment.ARGUMENT_CATEGORY, category);
                args.putParcelable(ProductsDetailsFragment.ARGUMENT_IMAGE_RES_ID, imageResId);
                args.putString(ProductsDetailsFragment.ARGUMENT_USER_ID, userId);
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
    public ImageButton favoriteButton;
    public boolean isEnable=false;
    //public TextView mNameTextView;

    public ViewHolder(View itemView) {
        super(itemView);

        // Get references to image and name.
        mImageView = (ImageView) itemView.findViewById(R.id.image);
      favoriteButton=(ImageButton) itemView.findViewById(R.id.favorite_button);
      favoriteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (isEnable){
            favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
          }else{
            favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
          }
          isEnable = !isEnable;
        }
      });
       //mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    public void setData(Bitmap imageResId, String name) {
        mImageView.setImageBitmap(imageResId);
        //mNameTextView.setText(name);
    }
}



