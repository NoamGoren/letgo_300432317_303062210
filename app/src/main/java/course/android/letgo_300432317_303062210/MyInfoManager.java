package course.android.letgo_300432317_303062210;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyInfoManager {

	private static MyInfoManager instance = null;
	private Context context = null;
	private MyInfoDatabase db = null;
	private InfoFolder selectedFolder = null;
	private InfoItem selectedItem = null;
	

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
		
		public void createItem(InfoItem item) {
			if (db != null) {
					db.createItem(getSelectedFolder(), item);
			}
		}
		
		public void createFolder(InfoFolder folder) {
			if (db != null) {
				db.createFolder(folder);
			}
		}
		
		public InfoItem readItem(int id) {
			InfoItem result = null;
			if (db != null) {
				result = db.readItem(id);
			}
			return result;
		}
		
		public InfoFolder readFolder(int id) {
			InfoFolder result = null;
			if (db != null) {
				result = db.readFolder(id);
			}
			return result;
		}
		
		public List<InfoItem> getAllItems() {
			List<InfoItem> result = new ArrayList<InfoItem>();
			if (db != null) {
				result = db.getAllItems();
			}
			return result;
		}
		
		public List<InfoFolder> getAllFolders() {
			List<InfoFolder> result = new ArrayList<InfoFolder>();
			if (db != null) {
				result = db.getAllFolders();
			}
			return result;
		}
		
		public void updateItem(InfoItem item) {
			if (db != null && item != null) {
				db.updateItem(item);
			}
		}
		
		public void updateFolder(InfoFolder folder) {
			if (db != null && folder != null) {
				db.updateFolder(folder);
			}
		}
		
		public void deleteItem(InfoItem item) {
			if (db != null) {
				db.deleteItem(item);
			}
		}
		
		public void deleteFolder(InfoFolder folder) {
			if (db != null) {
				db.deleteFolder(folder);
			}
		}
		
		public List<InfoItem> getFolderItems(InfoFolder folder) {
			List<InfoItem> result = new ArrayList<InfoItem>();
			if (db != null && folder != null) {
				result = db.getAllItemsOfFolder(folder);
			}
			return result;
		}

		public InfoFolder getSelectedFolder() {
			return selectedFolder;
		}

		public void setSelectedFolder(InfoFolder selectedFolder) {
			this.selectedFolder = selectedFolder;
		}

		public InfoItem getSelectedItem() {
			return selectedItem;
		}

		public void setSelectedItem(InfoItem selectedItem) {
			this.selectedItem = selectedItem;
		}


		public void deleteSelectedFolder(){
			deleteFolder(selectedFolder);
		}

}
