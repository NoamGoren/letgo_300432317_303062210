package course.android.letgo_300432317_303062210.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import course.android.letgo_300432317_303062210.Adapters.InfoItemListAdapter;
import course.android.letgo_300432317_303062210.DB.MyInfoManager;
import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.R;
import course.android.letgo_300432317_303062210.Classes.User;
import course.android.letgo_300432317_303062210.interfaces.CallBackListener;
import course.android.letgo_300432317_303062210.utils.NetworkResListener;
import course.android.letgo_300432317_303062210.utils.ResStatus;
import course.android.letgo_300432317_303062210.utils.NetworkConnector;

public class ItemListFragment extends Fragment  {
	private ListView itemsList;
	private InfoItemListAdapter adapter;
	private Button newItemBtn = null;
	private Button favItemBtn = null;
	private Button soldItemBtn = null;
	private Activity ctx;

	private ListView foldersList;

	private Context context = null;
	private Button newFolderBtn = null;

	//creates and returns the view hierarchy associated with the fragment.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_iten_list, container, false);

		ctx = getActivity();

		User folder = MyInfoManager.getInstance().getSelectedFolder();

		if (folder != null) {

			String txt = folder.getName();
			ctx.setTitle(txt);
		}





		itemsList = (ListView) rootView.findViewById(R.id.itemList);
		itemsList.setOnItemClickListener(itemClickListener);

		//new item
		newItemBtn  = (Button) rootView.findViewById(R.id.new_item_btn);
		newItemBtn.setOnClickListener(newItemListener);

		//favotite items
		favItemBtn  = (Button) rootView.findViewById(R.id.favItems);
		favItemBtn.setOnClickListener(favItemListener);

		//sold items
		soldItemBtn  = (Button) rootView.findViewById(R.id.soldItems);
		soldItemBtn.setOnClickListener(soldItemListener);

		List<Product> list = MyInfoManager.getInstance().getFolderItems(folder);
		adapter = new InfoItemListAdapter(ctx, R.layout.item_list_item, list);
		itemsList.setAdapter(adapter);


		//NetworkConnector.getInstance().updateProductsFeed(this);
		return rootView;
	}


	//Create new item
	private OnClickListener newItemListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			MyInfoManager.getInstance().setSelectedItem(null);
			EditItemFragment itemfragment = new EditItemFragment();
			FragmentManager fManager =ctx.getFragmentManager();
			FragmentTransaction ft = fManager.beginTransaction();
			ft.replace(R.id.content_frame, itemfragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};

	//go to favorite
	private OnClickListener favItemListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			MyInfoManager.getInstance().setSelectedItem(null);
			FavoriteItemFragment favItemfragment = new FavoriteItemFragment();
			FragmentManager fManager =ctx.getFragmentManager();
			FragmentTransaction ft = fManager.beginTransaction();
			ft.replace(R.id.content_frame, favItemfragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};

	//go to sold items
	private OnClickListener soldItemListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			MyInfoManager.getInstance().setSelectedItem(null);
			SoldItemFragment soldItemfragment = new SoldItemFragment();
			FragmentManager fManager =ctx.getFragmentManager();
			FragmentTransaction ft = fManager.beginTransaction();
			ft.replace(R.id.content_frame, soldItemfragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};

//	//go to sold items
//	private OnClickListener soldItemListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View arg0) {
//			MyInfoManager.getInstance().setSelectedItem(null);
//			FavoriteItemFragment favItemfragment = new FavoriteItemFragment();
//			FragmentManager fManager =ctx.getFragmentManager();
//			FragmentTransaction ft = fManager.beginTransaction();
//			ft.replace(R.id.content_frame, favItemfragment);
//			ft.addToBackStack(null);
//			ft.commit();
//
//		}
//	};





	//Get item
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			Product item = (Product) parent.getItemAtPosition(position);
			if (item != null) {
				MyInfoManager.getInstance().setSelectedItem(item);
				DisplayItemDetailsFragment itemfragment = new DisplayItemDetailsFragment();
				FragmentManager fManager =ctx.getFragmentManager();
				FragmentTransaction ft = fManager.beginTransaction();
				ft.replace(R.id.content_frame, itemfragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		}
	};

//	@Override
//	public void onSaveButtonClicked() {
//
//	}
//
//	@Override
//	public void onPreUpdate() {
//
//	}
//
//	@Override
//	public void onProductUpdate(byte[] res, ResStatus status) {
//
//	}
//
//	@Override
//	public void onProductUpdate(JSONObject res, ResStatus status) {
//		MyInfoManager.getInstance().updateItems(res);
//	}
//
//	@Override
//	public void onProductUpdate(Bitmap res, ResStatus status) {
//	}

}
