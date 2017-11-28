package course.android.letgo_300432317_303062210;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyInfoManager {

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

		public void openDataBase(Context context) {
			this.context = context;
			if (context != null) {
				db = new MyInfoDatabase(context);
				db.open();
			}
		}
		public void closeDataBase() {
			if(db!=null){
				db.close();
			}
		}
		
		public void createItem(Product item) {
			if (db != null) {
					db.createItem(getSelectedFolder(), item);
			}
		}
		
		public void createFolder(User folder) {
			if (db != null) {
				db.createFolder(folder);
			}
		}
		
		public Product readItem(int id) {
			Product result = null;
			if (db != null) {
				result = db.readItem(id);
			}
			return result;
		}
		
		public User readFolder(int id) {
			User result = null;
			if (db != null) {
				result = db.readFolder(id);
			}
			return result;
		}
		
		public List<Product> getAllItems() {
			List<Product> result = new ArrayList<Product>();
			if (db != null) {
				result = db.getAllItems();
			}
			return result;
		}
		
		public List<User> getAllFolders() {
			List<User> result = new ArrayList<User>();
			if (db != null) {
				result = db.getAllFolders();
			}
			return result;
		}
		
		public void updateItem(Product item) {
			if (db != null && item != null) {
				db.updateItem(item);
			}
		}
		
		public void updateFolder(User folder) {
			if (db != null && folder != null) {
				db.updateFolder(folder);
			}
		}
		
		public void deleteItem(Product item) {
			if (db != null) {
				db.deleteItem(item);
			}
		}
		
		public void deleteFolder(User folder) {
			if (db != null) {
				db.deleteFolder(folder);
			}
		}
		
		public List<Product> getFolderItems(User folder) {
			List<Product> result = new ArrayList<Product>();
			if (db != null && folder != null) {
				result = db.getAllItemsOfFolder(folder);
			}
			return result;
		}

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


		public void deleteSelectedFolder(){
			deleteFolder(selectedFolder);
		}

}
