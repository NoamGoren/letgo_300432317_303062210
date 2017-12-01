package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayItemDetailsFragment extends Fragment {
	
	
	private TextView itemDescriptionView;
	private TextView itemTitleView;
	private TextView itemPriceView;
	private TextView itemLocationView;
	private TextView itemCategoryView;
	private ImageView itemImage1;
	Activity ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.display_item_details_fragment, container, false);
		
		ctx = getActivity();

		Product item = MyInfoManager.getInstance().getSelectedItem();
		String txt = item.getTitle();
		ctx.setTitle(txt);

		
		itemDescriptionView = (TextView) rootView.findViewById(R.id.item_desc_txt);
		itemDescriptionView.setText(item.getDescription());

		itemTitleView = (TextView) rootView.findViewById(R.id.item_title_txt);
		itemTitleView.setText(item.getTitle());

		//itemCategoryView = (TextView) rootView.findViewById(R.id.category_spinner);
		//itemCategoryView.setText(item.getCategory());

		//itemPriceView = (TextView) rootView.findViewById(R.id.item_price_txt);
		//itemPriceView.setText(item.getPrice());

		itemLocationView = (TextView) rootView.findViewById(R.id.item_location_txt);
		itemLocationView.setText(item.getLocation());


		itemImage1 = (ImageView) rootView.findViewById(R.id.item_img1);
		Bitmap drawable1 = item.getImage1();
		if(drawable1!=null){
			itemImage1.setImageBitmap(drawable1);
		}

		setHasOptionsMenu(true);
		
		return rootView; 
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.myinfo_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   // handle item selection
	   switch (item.getItemId()) {
	      case R.id.action_edit:
	    	  EditItemFragment itemfragment = new EditItemFragment();
			  FragmentManager fManager =ctx.getFragmentManager();
			  FragmentTransaction ft = fManager.beginTransaction();
			  ft.replace(R.id.content_frame, itemfragment);
			  ft.addToBackStack(null);
			  ft.commit();
	         return true;
	      default:
	         return super.onOptionsItemSelected(item);
	   }
	}

}
