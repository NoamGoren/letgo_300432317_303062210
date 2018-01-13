package course.android.letgo_300432317_303062210.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.List;

import course.android.letgo_300432317_303062210.Activities.HomeActivity;
import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.Classes.User;
import course.android.letgo_300432317_303062210.DB.MyInfoManager;
import course.android.letgo_300432317_303062210.Fragments.ProductsDetailsFragment;
import course.android.letgo_300432317_303062210.R;
import course.android.letgo_300432317_303062210.utils.NetworkConnector;
import course.android.letgo_300432317_303062210.utils.NetworkResListener;
import course.android.letgo_300432317_303062210.utils.ResStatus;

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

    public static boolean isEnable;

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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final String id = mId[position];
        final String name = mNames[position];
        final String description = mDescriptions[position];
        final String price = mPrices[position];
        final String location = mLocations[position];
        final String category = mCategories[position];
        final Bitmap imageResId = mImageResIds[position];
        final String userId = mUserId[position];
        viewHolder.setData(imageResId, name,id);
      User user=MyInfoManager.getInstance().readFolder("ishai");
      Product item= MyInfoManager.getInstance().readItem(id);
      boolean test=user.getFavoritesProducts().contains(item);
      List<Product> afb=user.getFavoritesProducts();
        boolean flag=false;
      for (Product product:afb) {
        if(product.getId().equals(item.getId())){
          isEnable=true;
          flag=true;
        }

      }
      if (flag){
        viewHolder.getFavoriteButton().setImageResource(android.R.drawable.btn_star_big_on);
      }else{
        int a=0;


        viewHolder.getFavoriteButton().setImageResource(android.R.drawable.btn_star_big_off);
      }



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



        viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          User user=MyInfoManager.getInstance().readFolder("ishai");
          Product item= MyInfoManager.getInstance().readItem(id);
//          user.setFavoritesProducts(item);
        try{
          List<Product> afb=user.getFavoritesProducts();
          if (!isEnable){
            user.setFavoritesProducts(item);
            viewHolder.getFavoriteButton().setImageResource(android.R.drawable.btn_star_big_on);

          }else{
            Product p1=null;
            for (Product product:afb) {
              if(product.getId().equals(item.getId())){
                p1=product;

              }

            }


            user.RemoveFavoritesProducts(p1);
            List<Product>ap=user.getFavoritesProducts();
            int ahp=ap.size();
            viewHolder.getFavoriteButton().setImageResource(android.R.drawable.btn_star_big_off);
          }
          isEnable = !isEnable;
        }catch(Exception e){

          Intent intent= new Intent(context, HomeActivity.class);

          context.startActivity(intent);
        }


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
  public ImageButton getFavoriteButton(){
    return favoriteButton;
  }

    //public TextView mNameTextView;
    private String prodid;
    public ViewHolder(View itemView) {
        super(itemView);

        // Get references to image and name.
        mImageView = (ImageView) itemView.findViewById(R.id.image);
        favoriteButton=(ImageButton) itemView.findViewById(R.id.favorite_button);



       //mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    public void setData(Bitmap imageResId, String name, String productid) {
      this.prodid = productid;
        mImageView.setImageBitmap(imageResId);
        //mNameTextView.setText(name);


      NetworkConnector.getInstance().sendImageRequestToServer(NetworkConnector.GET_PRODUCT_IMAGE_REQ, prodid, new NetworkResListener() {
        @Override
        public void onPreUpdate() {
          //Toast.makeText(context,"start download img... id=" + post.getId(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProductUpdate(byte[] res, ResStatus status) {

        }

        @Override
        public void onProductUpdate(JSONObject res, ResStatus status) {

        }
        @Override
        public void onUserUpdate(JSONObject res,ResStatus status){

        }

        @Override
        public void onProductUpdate(Bitmap res, ResStatus status) {
          //Toast.makeText(context,"download img finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
          if(status == ResStatus.SUCCESS){
            if(res!=null) {
              if(mImageView!=null) {
                mImageView.setImageBitmap(res);
              }

              MyInfoManager.getInstance().updateProductImage(prodid,res);
            }
          }

        }
      });

    }
}



