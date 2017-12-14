package course.android.letgo_300432317_303062210;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//This activity show the entrance page of the application
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        }

    public void newFolder(View view) {
        Intent i= new Intent(Main3Activity.this,HomeActivity.class);
        startActivity(i);
    }
}
