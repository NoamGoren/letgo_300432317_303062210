package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InfoItemListAdapter extends ArrayAdapter<Product> {
	private Context context;
	private List<Product> items;

	public InfoItemListAdapter(Context context, int resource, List<Product> objects) {
		super(context, resource, objects);
		this.context = context;
		items = objects;
	}


	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		View rootView = mInflater.inflate(R.layout.item_list_item, null);

		TextView title = (TextView) rootView.findViewById(R.id.itemTitle);

		Product rowItem = getItem(position);

		title.setText(rowItem.getTitle());


		return rootView;
	}

	@Override
	public Product getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getCount() {
		return items.size();
	}
}
