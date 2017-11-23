package course.android.letgo_300432317_303062210;

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

/**
 * Created by admin on 11/22/2016.
 */

public  class ProductsDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;


    protected int[] mImageResIds;
    protected String[] mNames;
    protected String[] mDescriptions;
    protected Context context;


    public ProductsDataAdapter(Context context, int[] mImageResIds, String[] mNames, String[] mDescriptions) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mImageResIds=mImageResIds;
        this.mNames = mNames;
        this.mDescriptions = mDescriptions;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.recycler_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int imageResId = mImageResIds[position];
        final String name = mNames[position];
        final String description = mDescriptions[position];
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

class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    public ImageView mImageView;
    public TextView mNameTextView;

    public ViewHolder(View itemView) {
        super(itemView);

        // Get references to image and name.
        mImageView = (ImageView) itemView.findViewById(R.id.animal_image);
        mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    public void setData(int imageResId, String name) {
        mImageView.setImageResource(imageResId);
        mNameTextView.setText(name);
    }
}


