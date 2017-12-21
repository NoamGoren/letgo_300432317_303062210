package course.android.letgo_300432317_303062210.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import course.android.letgo_300432317_303062210.Adapters.MyAdapter;
import course.android.letgo_300432317_303062210.R;

public class CategoriesActivity extends AppCompatActivity {
//Responsible  for showing all the categories
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
    }
}
