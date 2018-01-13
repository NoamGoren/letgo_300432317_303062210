package course.android.letgo_300432317_303062210.DB;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.Classes.User;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.widget.Toast;


import course.android.letgo_300432317_303062210.utils.NetworkConnector;
import course.android.letgo_300432317_303062210.utils.NetworkResListener;
import course.android.letgo_300432317_303062210.utils.ResStatus;

import org.json.JSONObject;


public class MyInfoManager implements NetworkResListener {

	//Showing all the functions that you can run on the database


	private static MyInfoManager instance = null;
	private Context context = null;
	private MyInfoDatabase db = null;
	private User selectedFolder = null;
	private Product selectedItem = null;
	private Bitmap takenProductPicture = null;
	private String myUserId =null ;

	public String getMyUserId() {
		return myUserId;
	}


	public static MyInfoManager getInstance() {
			if (instance == null) {
				instance = new MyInfoManager();
			}
			return instance;
		}


		public static void releaseInstance() {
			if (instance != null) {
				instance.clean();
				instance = null;
			}
		}

		private void clean() {

		}

	@Override
	public void onPreUpdate() {
		Toast.makeText(context,"Sync stated...",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProductUpdate(byte[] res, ResStatus status) {
		Toast.makeText(context,"Sync finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProductUpdate(JSONObject res, ResStatus status) {
		if(res!=null){
			Toast.makeText(context,"Sync finished...status " + res.toString(),Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(context, "Sync finished...status " + status.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onUserUpdate(JSONObject res, ResStatus status) {
		if(res!=null){
			Toast.makeText(context,"Sync finished...status " + res.toString(),Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(context, "Sync finished...status " + status.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onProductUpdate(Bitmap res, ResStatus status) {
	}

	public Bitmap getTakenProductPicture() {
		return takenProductPicture;
	}

	public void setTakenProductPicture(Bitmap takenProductPicture) {
		this.takenProductPicture = takenProductPicture;
	}


	public Context getContext() {
			return context;

		}
		//Open the Database
		public void openDataBase(Context context) {
			this.context = context;
			if (context != null) {
				db = new MyInfoDatabase(context);
				db.open();
			}
		}
		//Close the database
		public void closeDataBase() {
			if(db!=null){
				db.close();
			}
		}
		//Create Product and insert to the database
		public void createItem(Product item) {
			boolean result=false;
			if (db != null) {
				if(db.createItem(item)){
					NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_PRODUCT_REQ, item, instance);
				}


			}
		}
		//create User
		public void createFolder(User folder) {
			if (db != null) {
				if (db.createFolder(folder)){
					NetworkConnector.getInstance().sendRequestToServerFolder(NetworkConnector.INSERT_USER_REQ, folder, instance);
				}

			}
		}



		//Read specific Product
		public Product readItem(String id) {
			Product result = null;
			if (db != null) {
				result = db.readItem(id);
			}
			return result;
		}
		//Read specific User
		public User readFolder(String id) {
			User result = null;
			if (db != null) {
				result = db.readFolder(id);
			}
			return result;
		}
		//Get All Products
		public List<Product> getAllItems() {
			List<Product> result = new ArrayList<Product>();
			if (db != null) {
				result = db.getAllItems();
			}
			return result;
		}
		//Get all users
		public List<User> getAllFolders() {
			List<User> result = new ArrayList<User>();
			if (db != null) {
				result = db.getAllFolders();
			}
			return result;
			}


		//Update specific product
		public boolean updateItem(Product item) {
			boolean result=false;
			if (db != null && item != null) {
				result= db.updateItem(item);
				if(result){
					NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_PRODUCT_REQ,item, instance);
				}
			}
			return result;
		}
		//TODO ask about updateItems and users
	public void updateItems(JSONObject res) {
		if(db==null){
			return;
		}
		List<Product> products = Product.parseJson(res);
		for(Product i:products){
			if(!db.createItemWithoutImg(i)) {
				db.updateItemWithoutImage(i);
			}
		}
	}
//TODO ask about itemId
	public void updateProductImage(String id, Bitmap bm){
		if(bm!=null) {
			String itemId = id;
			if(db!=null){
				db.updateProductImage(id,bm);
			}
		}
	}


	//Update specific
		public boolean updateFolder(User user) {
			boolean result=false;
			if (db != null && user != null) {
				result= db.updateFolder(user);
				if(result){
					NetworkConnector.getInstance().sendRequestToServerFolder(NetworkConnector.INSERT_USER_REQ,user, instance);
				}
			}
			return result;
		}
	public void updateUsers(JSONObject res) {
		if(db==null){
			return;
		}
		List<User> users = User.parseJson(res);
		for(User i:users){
			if(!db.createFolder(i)) {
				db.updateFolder(i);
			}
		}
	}
		//Delete specifc product
		public boolean deleteItem(Product item) {
			boolean result =false;
			if (db != null) {
				result=db.deleteItem(item);
				if(result){
					NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_PRODUCT_REQ, item, instance);
				}
			}
			return  result;
		}
		//Delete User
		public boolean deleteFolder(User user) {
			boolean result =false;
			if (db != null) {
				result=db.deleteFolder(user);
				if(result){
					NetworkConnector.getInstance().sendRequestToServerFolder(NetworkConnector.DELETE_USER_REQ,user, instance);
				}
			}
			return  result;
		}

		//Get list of users
		public List<Product> getFolderItems(User folder) {
			List<Product> result = new ArrayList<Product>();
			if (db != null && folder != null) {
				result = db.getAllItemsOfFolder(folder);
			}
			return result;
		}

		public List<Product> getFavoriteItems(User user){
      List<Product> result = new ArrayList<Product>();
      if (db != null && user != null) {
        result = db.getAllFavoriteItems(user);
      }
      return result;

    }
		//getters and setters for SelectedFolder and SelectedItem
		public User getSelectedFolder() {
			return selectedFolder;
		}

		public void setSelectedFolder(User selectedFolder) {
			this.selectedFolder = selectedFolder;
		}

		public Product getSelectedItem() {
			return selectedItem;
		}

		public void setSelectedItem(Product selectedItem) {
			this.selectedItem = selectedItem;
		}

		//delete Selected Folder
		public void deleteSelectedFolder(){
			deleteFolder(selectedFolder);
		}

}
