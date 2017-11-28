package course.android.letgo_300432317_303062210;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditItemFragment extends Fragment {
	
	private EditText itemTitleView = null;
	private EditText itemDescriptionView = null;
	private Button itemSaveBtn = null;
	private ImageView itemImage1 = null;
	private TextView titleLblView = null;
	private TextView descriptionLblView = null;
	private Activity ctx = null;

    static final int REQUEST_TAKE_PHOTO = 111;
	private static final int PHOTO_W = 500;
	private static final int PHOTO_H = 300;


	protected static final int NEW_ITEM_TAG = -111;
	private File output=null;

	private Button deleteItemButton = null;
	private Button addPhotoBtn = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_item, container, false);
		
		itemTitleView = (EditText) rootView.findViewById(R.id.item_title_txt);
		itemDescriptionView = (EditText) rootView.findViewById(R.id.item_desc_txt);
		
		titleLblView  =(TextView) rootView.findViewById(R.id.item_title_lbl);
		descriptionLblView = (TextView) rootView.findViewById(R.id.item_desc_lbl);
		
		itemSaveBtn = (Button) rootView.findViewById(R.id.save_item_btn);
		itemImage1 = (ImageView) rootView.findViewById(R.id.item_img1);

		addPhotoBtn  = (Button) rootView.findViewById(R.id.add_photo_btn);
		deleteItemButton  = (Button) rootView.findViewById(R.id.delete_item_btn);

		ctx  = getActivity();

		itemSaveBtn.setOnClickListener(saveItemListener);
		addPhotoBtn.setOnClickListener(addPhotoListener);
		itemImage1.setOnClickListener(delImage1Listener);
		deleteItemButton.setOnClickListener(deleteItemListener );

		Product infoItem = MyInfoManager.getInstance().getSelectedItem();
		if(infoItem!=null){
			itemTitleView.setText(infoItem.getTitle());
			itemDescriptionView.setText(infoItem.getDescription());
			Bitmap img1 =infoItem.getImage1();
			if(img1!=null){
				itemImage1.setImageBitmap(img1);
				itemImage1.setVisibility(View.VISIBLE);

			}
		}
		
		return rootView; 
	}
	

	
	
	private OnClickListener delImage1Listener= new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			final String title = "Delete Photo";
			final String msg = "Do you want to delete this photo? ";
			
			   new AlertDialog.Builder(ctx)
				  .setTitle(title)
				  .setMessage(msg)
				  .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				    
						Product infoItem = MyInfoManager.getInstance().getSelectedItem();
						if(infoItem!=null){
							
							infoItem.setImage1(null);
							itemImage1.setVisibility(View.GONE);
							itemImage1.setImageBitmap(null);
							itemImage1.destroyDrawingCache();
						}			
				    }
				  })
				  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				    }
				  })
				  .show(); 
			
		}
	};
	
	
private OnClickListener addPhotoListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {

			Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = "img_" + timeStamp + ".jpeg";
			output=new File(dir, imageFileName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

			startActivityForResult(i, REQUEST_TAKE_PHOTO);
			
		}
	};

	
	
	
	private OnClickListener saveItemListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			try {
				String title=itemTitleView.getText().toString();
				String description = itemDescriptionView.getText().toString();

				
				Product item =MyInfoManager.getInstance().getSelectedItem();
				if(item==null){
					 item = new Product(title, description);
			
					 MyInfoManager.getInstance().createItem(item);
				}
				else{
					 item.setTitle(title);
					 item.setDescription(description);
					 if(item.getId() == NEW_ITEM_TAG){
						 MyInfoManager.getInstance().createItem(item);
					 }
					 else{
						 MyInfoManager.getInstance().updateItem(item);
					 }
				}
				
				
				ItemListFragment itemlistfragment = new ItemListFragment();
				FragmentManager fManager =ctx.getFragmentManager();
				FragmentTransaction ft = fManager.beginTransaction();
				ft.replace(R.id.content_frame, itemlistfragment);
				ft.addToBackStack(null);
				ft.commit();

				
			} catch (Throwable e) {
				e.printStackTrace();
			}	
			
		}
	};
	
	
	private OnClickListener deleteItemListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			try {
			

				Product item =MyInfoManager.getInstance().getSelectedItem();
				if(item!=null){
					 MyInfoManager.getInstance().deleteItem(item);
				}

				ItemListFragment itemlistfragment = new ItemListFragment();
				FragmentManager fManager =ctx.getFragmentManager();
				FragmentTransaction ft = fManager.beginTransaction();
				ft.replace(R.id.content_frame, itemlistfragment);
				ft.addToBackStack(null);
				ft.commit();
				
			} catch (Throwable e) {
				e.printStackTrace();
			}	
			
		}
	};
	


    /**
     * The activity returns with the photo.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

        	
			if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {


				Product infoItem = MyInfoManager.getInstance().getSelectedItem();

				if(infoItem == null){
					infoItem = new Product();
					infoItem.setId(NEW_ITEM_TAG);
					MyInfoManager.getInstance().setSelectedItem(infoItem);
				}
				
			   Bitmap bitmap =  getScaledImageFromFilePath(output.getAbsolutePath());
			   if(bitmap!=null){
				    if(infoItem!=null){
				        itemImage1.setImageBitmap(bitmap);
						itemImage1.setVisibility(View.VISIBLE);
						infoItem.setImage1(bitmap);
				    }

			   }
			} 

		} catch (Throwable e) {
			e.printStackTrace();
		}
    }
    

  
    private Bitmap getScaledImageFromFilePath(String imagePath) {
        // Get the dimensions of the View
    	Bitmap scaledBitmap = null;
        try {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

			Matrix matrix = new Matrix();
			matrix.postRotate(90);

			Bitmap rotatedBitmap =  Bitmap.createScaledBitmap(bitmap, PHOTO_W, PHOTO_H, false);
			scaledBitmap = Bitmap.createBitmap(rotatedBitmap , 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);

		} catch (Throwable e) {
			e.printStackTrace();
		}
        return scaledBitmap;
    }

}

