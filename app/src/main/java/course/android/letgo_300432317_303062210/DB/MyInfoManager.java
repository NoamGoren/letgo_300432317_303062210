package course.android.letgo_300432317_303062210.DB;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.Classes.User;

public class MyInfoManager {

	//Showing all the functions that you can run on the database


	private static MyInfoManager instance = null;
	private Context context = null;
	private MyInfoDatabase db = null;
	private User selectedFolder = null;
	private Product selectedItem = null;

	

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
			if (db != null) {
					db.createItem(getSelectedFolder(), item);
			}
		}
		//create User
		public void createFolder(User folder) {
			if (db != null) {
				db.createFolder(folder);
			}
		}

		//Read specific Product
		public Product readItem(int id) {
			Product result = null;
			if (db != null) {
				result = db.readItem(id);
			}
			return result;
		}
		//Read specific User
		public User readFolder(int id) {
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
		public void updateItem(Product item) {
			if (db != null && item != null) {
				db.updateItem(item);
			}
		}
		//Update specific
		public void updateFolder(User folder) {
			if (db != null && folder != null) {
				db.updateFolder(folder);
			}
		}
		//Delete specifc product
		public void deleteItem(Product item) {
			if (db != null) {
				db.deleteItem(item);
			}
		}
		//Delete User
		public void deleteFolder(User folder) {
			if (db != null) {
				db.deleteFolder(folder);
			}
		}

		//Get list of users
		public List<Product> getFolderItems(User folder) {
			List<Product> result = new ArrayList<Product>();
			if (db != null && folder != null) {
				result = db.getAllItemsOfFolder(folder);
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
