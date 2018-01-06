package course.android.letgo_300432317_303062210.Fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import course.android.letgo_300432317_303062210.Adapters.InfoItemListAdapter;
import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.Classes.User;
import course.android.letgo_300432317_303062210.DB.MyInfoManager;
import course.android.letgo_300432317_303062210.R;

/**
 * Created by Ishai Levi on 11/28/2017.
 */

public class Tab3profileSelling extends Fragment {

    private ListView itemsList;
    private InfoItemListAdapter adapter;

    private Activity ctx;

    private ListView foldersList;

    private Context context = null;



    //creates and returns the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3profile_selling, container, false);

        User folder= MyInfoManager.getInstance().readFolder("noam");
        itemsList = (ListView) rootView.findViewById(R.id.itemList);
        itemsList.setOnItemClickListener(itemClickListener);
        ctx = getActivity();

        List<Product> list = MyInfoManager.getInstance().getFolderItems(folder);
        adapter = new InfoItemListAdapter(ctx, R.layout.tab3profile_selling, list);
        itemsList.setAdapter(adapter);

        return rootView;
    }




    //Get item
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

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

}