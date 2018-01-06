package course.android.letgo_300432317_303062210.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import course.android.letgo_300432317_303062210.R;

public class ProductsDetailsFragment extends Fragment {

    public static final String ARGUMENT_ID = "id";
    public static final String ARGUMENT_NAME = "name";
    public static final String ARGUMENT_DESCRIPTION = "description";
    public static final String ARGUMENT_PRICE= "price";
    public static final String ARGUMENT_LOCATION = "location";
    public static final String ARGUMENT_CATEGORY = "category";
    public static final String ARGUMENT_IMAGE_RES_ID = "imageResId";
    public static final String ARGUMENT_USER_ID= "userId";

    //creates and returns the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_details, container, false);

        TextView idTextView = (TextView) view.findViewById(R.id.id);
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        TextView priceTextView =(TextView) view.findViewById(R.id.price);
        TextView locationTextView = (TextView) view.findViewById(R.id.location);
        TextView categoryTextView = (TextView) view.findViewById(R.id.category);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        Bundle args = getArguments();

        idTextView.setText(args.getString(ARGUMENT_ID));
        nameTextView.setText(args.getString(ARGUMENT_NAME));
        descriptionTextView.setText(args.getString(ARGUMENT_DESCRIPTION));
        priceTextView.setText(args.getString(ARGUMENT_PRICE));
        locationTextView.setText(args.getString(ARGUMENT_LOCATION));
        categoryTextView.setText(args.getString(ARGUMENT_CATEGORY));

        Bitmap bitmapimage = args.getParcelable(ARGUMENT_IMAGE_RES_ID);
        imageView.setImageBitmap(bitmapimage);

        return view;
    }
}
