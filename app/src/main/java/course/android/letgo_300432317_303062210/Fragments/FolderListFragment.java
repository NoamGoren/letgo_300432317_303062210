package course.android.letgo_300432317_303062210.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import org.json.JSONObject;

import course.android.letgo_300432317_303062210.Adapters.InfoFolderListAdapter;
import course.android.letgo_300432317_303062210.DB.MyInfoManager;
import course.android.letgo_300432317_303062210.R;
import course.android.letgo_300432317_303062210.Classes.User;
import course.android.letgo_300432317_303062210.utils.NetworkResListener;
import course.android.letgo_300432317_303062210.utils.ResStatus;
import course.android.letgo_300432317_303062210.interfaces.CallBackListener;
import course.android.letgo_300432317_303062210.utils.NetworkConnector;

public class FolderListFragment extends Fragment  {
	private ListView foldersList;
	private InfoFolderListAdapter adapter;
	private Context context = null;
	private Button newFolderBtn = null;
	private Activity ctx;

	//creates and returns the view hierarchy associated with the fragment.
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_folder_list, container, false);
		
		context = rootView.getContext();
		
		ctx = getActivity();
		if (ctx != null) {
			ctx.setTitle("Users Profiles");
		}
		
		foldersList = (ListView) rootView.findViewById(R.id.folderList);

		foldersList.setOnItemClickListener(folderClickListener);

		newFolderBtn = (Button) rootView.findViewById(R.id.new_folder_btn);
		newFolderBtn.setVisibility(View.VISIBLE);


		newFolderBtn.setOnClickListener(newFloderListener);

		List<User> list = MyInfoManager.getInstance().getAllFolders();
		adapter = new InfoFolderListAdapter(context, R.layout.folder_list_item, list);
		foldersList.setAdapter(adapter);

		//NetworkConnector.getInstance().updateUsersFeed(this);

		return rootView;
	}





	//Create New Folder
	public OnClickListener newFloderListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			final String title = "Uploading Item To Letgo";
			final String msg = "Enter Your Name";
			final EditText answerText = new EditText(context);
			AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
			builder.setTitle(title);
			builder.setMessage(msg);
			builder.setView(answerText);

			builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {

						String name = answerText.getText().toString();
				    	 User folder = new User(name);
						MyInfoManager.getInstance().createFolder(folder);
						List<User> list = MyInfoManager.getInstance().getAllFolders();
						adapter = new InfoFolderListAdapter(context, R.layout.folder_list_item, list);
						foldersList.setAdapter(adapter);
				    }
			});

			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				    }
			});


			AlertDialog alertDialog = builder.create();

			alertDialog .show();
			
		}
	};


	//Enter Folder
	private OnItemClickListener folderClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
			User folder = (User) parent.getItemAtPosition(position);
			if (folder != null) {

					MyInfoManager.getInstance().setSelectedFolder(folder);
					ItemListFragment itemlistfragment = new ItemListFragment();
					FragmentManager fManager =ctx.getFragmentManager();
					FragmentTransaction ft = fManager.beginTransaction();
					ft.replace(R.id.content_frame, itemlistfragment);
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
//		MyInfoManager.getInstance().updateUsers(res);
//	}
//
//	@Override
//	public void onProductUpdate(Bitmap res, ResStatus status) {
//
//	}
}
