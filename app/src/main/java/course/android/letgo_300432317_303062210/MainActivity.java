package course.android.letgo_300432317_303062210;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//The activity will be helpful in the next chapter
public class MainActivity extends AppCompatActivity {

    FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
        //actionBar.setIcon(null);

        MyInfoManager.getInstance().openDataBase(this);

        fManager = getFragmentManager();
        FragmentTransaction ft = fManager.beginTransaction();
        Fragment fragment = new FolderListFragment();
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.addToBackStack(null);
        ft.commit();

        hideKeyboard();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                FragmentTransaction ft = fManager.beginTransaction();
                Fragment fragment = new FolderListFragment();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
