package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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

public class FolderListFragment extends Fragment {
	private ListView foldersList;
	private InfoFolderListAdapter adapter;
	private Context context = null;
	private Button newFolderBtn = null;
	private Activity ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_folder_list, container, false);
		
		context = rootView.getContext();
		
		ctx = getActivity();
		if (ctx != null) {
			ctx.setTitle(ctx.getResources().getString(R.string.myinfo));
		}
		
		foldersList = (ListView) rootView.findViewById(R.id.folderList);

		foldersList.setOnItemClickListener(folderClickListener);

		newFolderBtn = (Button) rootView.findViewById(R.id.new_folder_btn);
		newFolderBtn.setVisibility(View.VISIBLE);


		newFolderBtn.setOnClickListener(newFloderListener);

		List<User> list = MyInfoManager.getInstance().getAllFolders();
		adapter = new InfoFolderListAdapter(context, R.layout.folder_list_item, list);
		foldersList.setAdapter(adapter);

		return rootView;
	}






	private OnClickListener newFloderListener = new OnClickListener() {
		
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
				    	 String proptAnswer = answerText.getText().toString();
				    	 User folder = new User(proptAnswer);
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

}
