package course.android.letgo_300432317_303062210;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class CameraActivity extends Activity {

    Uri outputFileUri;

    String selectedImagePath = "";

    ImageView mUserImage;

    ImageButton cameraButton;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

// TODO Auto-generated method stub

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        initiViews();

        Spinner spinner = (Spinner) findViewById(R.id.coin_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coin_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }



    private void initiViews() {

        cameraButton=(ImageButton) findViewById(R.id.cameraButton);

        mUserImage = (ImageView) findViewById(R.id.mUserImage);



        cameraButton.setOnClickListener(new View.OnClickListener() {



            @Override

            public void onClick(View arg0) {



                popmenu();

            }

        });



    }



    void popmenu() {

        final Dialog dialog = new Dialog(CameraActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.cameradialog);

        dialog.getWindow().setBackgroundDrawable(

                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button mCamerabtn = (Button) dialog.findViewById(R.id.cameradialogbtn);

        Button mGallerybtn = (Button) dialog

                .findViewById(R.id.gallerydialogbtn);

        Button btnCancel = (Button) dialog.findViewById(R.id.canceldialogbtn);



        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT,

                ActionBar.LayoutParams.FILL_PARENT);



        mCamerabtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = new File(Environment.getExternalStorageDirectory(),

                        "test.jpg");



                outputFileUri = Uri.fromFile(file);

                Log.d("TAG", "outputFileUri intent" + outputFileUri);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(intent, 0);



                dialog.cancel();

            }

        });



        mGallerybtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/*");

                startActivityForResult(intent, 1);

                dialog.cancel();

            }

        });



        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.cancel(); // dismissing the popup

            }

        });



        dialog.show();



    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode) {

            case 0:

                if (resultCode == RESULT_OK) {

                    Log.d("TAG", "outputFileUri RESULT_OK" + outputFileUri);

                    if (outputFileUri != null) {



                        Bitmap bitmap;

                        bitmap = decodeSampledBitmapFromUri(outputFileUri,

                                mUserImage.getWidth(), mUserImage.getHeight());



                        if (bitmap == null) {

                            Toast.makeText(

                                    getApplicationContext(),

                                    "the image data could not be decoded"

                                            + outputFileUri.getPath(),

                                    Toast.LENGTH_LONG).show();



                        } else {

                            selectedImagePath = getRealPathFromURI(CameraActivity.this,outputFileUri);// outputFileUri.getPath().

                            Toast.makeText(

                                    getApplicationContext(),

                                    "Decoded Bitmap: " + bitmap.getWidth() + " x "

                                            + bitmap.getHeight()

                                            + outputFileUri.getPath(),

                                    Toast.LENGTH_LONG).show();

                            mUserImage.setImageBitmap(bitmap);

                        }

                    }

                }

                break;

            case 1:

                if (resultCode == RESULT_OK) {

                    Uri targetUri = data.getData();

                    Log.d("TAG", "datae" + targetUri);

                    Bitmap bitmap;

                    bitmap = decodeSampledBitmapFromUri(targetUri,

                            mUserImage.getWidth(), mUserImage.getHeight());



                    if (bitmap == null) {

                        Toast.makeText(

                                getApplicationContext(),

                                "the image data could not be decoded"

                                        + targetUri.getPath(), Toast.LENGTH_LONG)

                                .show();



                    } else {

                        selectedImagePath = getPath(targetUri);// targetUri.getPath();

                        Toast.makeText(

                                getApplicationContext(),

                                "Decoded Bitmap: " + bitmap.getWidth() + " x "

                                        + bitmap.getHeight() + targetUri.getPath(),

                                Toast.LENGTH_LONG).show();

                        mUserImage.setImageBitmap(bitmap);

                    }

                }

                break;



            default:

                break;

        }

    }



    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor

                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }



    public String getRealPathFromURI(Context context, Uri contentUri) {

        Cursor cursor = null;

        try {



            if("content".equals(contentUri.getScheme())) {

                String[] proj = {MediaStore.Images.Media.DATA};

                cursor = context.getContentResolver().query(contentUri, proj, null, null, null);

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                return cursor.getString(column_index);

            }

            else{

                return contentUri.getPath();

            }





        } finally {

            if (cursor != null) {

                cursor.close();

            }

        }

    }



    public Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth,

                                             int reqHeight) {



        Bitmap bm = null;



        try {

// First decode with inJustDecodeBounds=true to check dimensions

            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(getContentResolver()

                    .openInputStream(uri), null, options);



// Calculate inSampleSize

            options.inSampleSize = calculateInSampleSize(options, reqWidth,

                    reqHeight);



// Decode bitmap with inSampleSize set

            options.inJustDecodeBounds = false;

            bm = BitmapFactory.decodeStream(getContentResolver()

                    .openInputStream(uri), null, options);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            Toast.makeText(getApplicationContext(), e.toString(),

                    Toast.LENGTH_LONG).show();

        }



        return bm;

    }



    public int calculateInSampleSize(BitmapFactory.Options options,

                                     int reqWidth, int reqHeight) {

// Raw height and width of image

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;



        if (height > reqHeight || width > reqWidth) {

            if (width > height) {

                inSampleSize = Math.round((float) height / (float) reqHeight);

            } else {

                inSampleSize = Math.round((float) width / (float) reqWidth);

            }

        }

        return inSampleSize;

    }



}
