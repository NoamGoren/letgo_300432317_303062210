package course.android.letgo_300432317_303062210;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductsDetailsFragment extends Fragment {

  public static final String ARGUMENT_IMAGE_RES_ID = "imageResId";
  public static final String ARGUMENT_NAME = "name";
  public static final String ARGUMENT_DESCRIPTION = "description";


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_products_details, container, false);

     ImageView imageView = (ImageView) view.findViewById(R.id.animal_image);
     TextView nameTextView = (TextView) view.findViewById(R.id.name);
     TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

    Bundle args = getArguments();
    imageView.setImageResource(args.getInt(ARGUMENT_IMAGE_RES_ID));
    nameTextView.setText(args.getString(ARGUMENT_NAME));
    String descText=  args.getString(ARGUMENT_DESCRIPTION);
    descriptionTextView.setText(descText);
    return view;
  }
}
