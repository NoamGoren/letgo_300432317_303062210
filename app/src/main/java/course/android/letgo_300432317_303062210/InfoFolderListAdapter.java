package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoFolderListAdapter extends ArrayAdapter<User> {

	private Context context;
	private List<User> folders;

	public InfoFolderListAdapter(Context context, int resource, List<User> objects) {
		super(context, resource, objects);
		this.context = context;
		folders = objects;
	}

	@Override
	public int getCount() {
		return folders.size();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View rootView = mInflater.inflate(R.layout.folder_list_item, null);
		final User currentFolder = getItem(position);
		TextView title = (TextView) rootView.findViewById(R.id.folderTitle);
		title.setText(currentFolder.getName());


		ImageView deleteIcon = (ImageView) rootView.findViewById(R.id.deleteIcon);
		deleteIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String title = "Delete Folder";
				final String msg = "Are you sure?";
				AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
				builder.setTitle(title);
				builder.setMessage(msg);

				builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						MyInfoManager.getInstance().deleteFolder(currentFolder);
						InfoFolderListAdapter.this.remove(currentFolder);
						InfoFolderListAdapter.this.notifyDataSetChanged();
					}
				});

				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

				AlertDialog alertDialog = builder.create();
				alertDialog .show();
			}
		});


		return rootView;
	}




	@Override
	public User getItem(int position) {
		return folders.get(position);
	}


}
