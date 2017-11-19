package course.android.letgo_300432317_303062210;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noam on 11/17/2017.
 */

public final class MyAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("cars",       R.drawable.car_category));
        mItems.add(new Item("Sad",   R.drawable.electronics_category));
        mItems.add(new Item("Angry", R.drawable.free_category));
        mItems.add(new Item("Gray",      R.drawable.garden_category));
        mItems.add(new Item("Green",     R.drawable.baby_category));
        mItems.add(new Item("Cyan",      R.drawable.fashion_category));
        mItems.add(new Item("Cyan",      R.drawable.music_category));
        mItems.add(new Item("Cyan",      R.drawable.sport_category));
        mItems.add(new Item("Cyan",      R.drawable.other_category));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);


        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}