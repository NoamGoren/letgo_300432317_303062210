package course.android.letgo_300432317_303062210;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyInfoDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "MyInfoDB";

	// folder table
	//user table
	private static final String TABLE_FOLDER_NAME = "folders";
	private static final String FOLDER_COLUMN_ID = "id";
	private static final String FOLDER_COLUMN_NAME = "name";
	private static final String FOLDER_COLUMN_EMAIL = "email";
	private static final String FOLDER_COLUMN_PASSWORD = "password";
	private static final String FOLDER_COLUMN_CITY = "city";
	private static final String[] TABLE_FOLDER_COLUMNS = {FOLDER_COLUMN_ID, FOLDER_COLUMN_NAME,FOLDER_COLUMN_EMAIL
			,FOLDER_COLUMN_CITY,FOLDER_COLUMN_PASSWORD};

	// item table
	//product table
	private static final String TABLE_ITEMS_NAME = "items";
	private static final String ITEM_COLUMN_ID = "id";
	private static final String ITEM_COLUMN_NAME = "title";
	private static final String ITEM_COLUMN_DESCRIPTION = "description";
	private static final String ITEM_COLUMN_PRICE = "price";
	private static final String ITEM_COLUMN_LOCATION = "location";
	private static final String ITEM_COLUMN_CATEGORY = "category";
	private static final String ITEM_COLUMN__IMAGE1 = "image1";
	private static final String ITEM_COLUMN_FOLDERID = "folder_id";

	private static final String[] TABLE_ITEM_COLUMNS = {ITEM_COLUMN_ID, ITEM_COLUMN_NAME,
			ITEM_COLUMN_DESCRIPTION, ITEM_COLUMN__IMAGE1, ITEM_COLUMN_FOLDERID,ITEM_COLUMN_LOCATION,ITEM_COLUMN_PRICE};


	private SQLiteDatabase db = null;

	public MyInfoDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// SQL statement to create item table
			String CREATE_ITEM_TABLE = "create table if not exists " + TABLE_ITEMS_NAME +" ( "
					+ ITEM_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ ITEM_COLUMN_NAME +" TEXT, "
					+ ITEM_COLUMN_DESCRIPTION + " TEXT, "
					+ ITEM_COLUMN__IMAGE1 + " BLOB, "
					+ ITEM_COLUMN_PRICE+" INTEGER, "
					+ ITEM_COLUMN_CATEGORY + " TEXT, "
					+ ITEM_COLUMN_LOCATION + " TEXT, "
					+ ITEM_COLUMN_FOLDERID + " INTEGER)";
			db.execSQL(CREATE_ITEM_TABLE);

			if (!isTableExist(TABLE_FOLDER_NAME, db)) {
				// SQL statement to create item table
				String CREATE_FOLDER_TABLE = "create table if not exists "+ TABLE_FOLDER_NAME+" ( "
						+ FOLDER_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ FOLDER_COLUMN_CITY + " TEXT, "
						+ FOLDER_COLUMN_PASSWORD +" TEXT, "
						+ FOLDER_COLUMN_EMAIL + " TEXT, "
						+ FOLDER_COLUMN_NAME + " TEXT)";

				db.execSQL(CREATE_FOLDER_TABLE);
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			// drop item table if already exists
			//db.execSQL("DROP TABLE IF EXISTS items");
			//db.execSQL("DROP TABLE IF EXISTS folders");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		//onCreate(db);
	}

	public void createItem(User user,Product product) {

		try {
			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(ITEM_COLUMN_NAME, product.getTitle());
			values.put(ITEM_COLUMN_DESCRIPTION, product.getDescription());
			values.put(ITEM_COLUMN_PRICE, product.getPrice());
			values.put(ITEM_COLUMN_LOCATION, product.getLocation());
			values.put(ITEM_COLUMN_CATEGORY, product.getCategory());
			values.put(ITEM_COLUMN_FOLDERID, user.getId());
			
			//images
			Bitmap image1 = product.getImage1();
			if (image1 != null) {
				byte[] data = getBitmapAsByteArray(image1);
				if (data != null && data.length > 0) {
					values.put(ITEM_COLUMN__IMAGE1, data);
				}
			}

			// insert item
			db.insert(TABLE_ITEMS_NAME, null, values);
			
			
		} catch (Throwable t) {
			t.printStackTrace();
		}


	}
	
	private byte[] getBitmapAsByteArray(Bitmap bitmap) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    bitmap.compress(CompressFormat.PNG, 0, outputStream);
	    return outputStream.toByteArray();
	}

	public void createFolder(InfoFolder folder) {

		try {
			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(FOLDER_COLUMN_TITLE, folder.getTitle());
			// insert folder
			db.insert(TABLE_FOLDER_NAME, null, values);
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public InfoItem readItem(int id) {
		InfoItem item = null;
		Cursor cursor = null;
		try {
			/// get reference of the itemDB database

			// get  query
			 cursor = db
					.query(TABLE_ITEMS_NAME,
							TABLE_ITEM_COLUMNS, ITEM_COLUMN_ID + " = ?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);
		
				

			// if results !=null, parse the first one
			if(cursor!=null && cursor.getCount()>0){
			
				cursor.moveToFirst();

				item = new InfoItem();
				item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_COLUMN_ID))));
				item.setTitle(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				
				//images
				byte[] img1Byte = cursor.getBlob(3);
				if (img1Byte != null && img1Byte.length > 0) {
					Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
					if (image1 != null) {
						item.setImage1(image1);
					}
				}
				

				item.setFolderId(Integer.parseInt(cursor.getString(4)));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			if(cursor!=null){
				cursor.close();
			}
		}
		return item;
	}

	public InfoFolder readFolder(int id) {
		InfoFolder folder = null;
		Cursor cursor = null;
		try {
			// get reference of the folderDB database

			// get  query
			cursor = db
					.query(TABLE_FOLDER_NAME, // a. table
							TABLE_FOLDER_COLUMNS, FOLDER_COLUMN_ID + " = ?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);

			// if results !=null, parse the first one
			if (cursor != null)
				cursor.moveToFirst();

			folder = new InfoFolder();
			folder.setId(Integer.parseInt(cursor.getString(0)));
			folder.setTitle(cursor.getString(1));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			if(cursor!=null){
				cursor.close();
			}
		}
		return folder;
	}

	public List<InfoItem> getAllItems() {
		List<InfoItem> result = new ArrayList<InfoItem>();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_ITEMS_NAME, TABLE_ITEM_COLUMNS, null, null,
					null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				InfoItem item = cursorToItem(cursor);
				result.add(item);
				cursor.moveToNext();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			// make sure to close the cursor
			if(cursor!=null){
				cursor.close();
			}
		}

		return result;
	}
	
	private InfoItem cursorToItem(Cursor cursor) {
		InfoItem result = new InfoItem();
		try {
			result.setId(Integer.parseInt(cursor.getString(0)));
			result.setTitle(cursor.getString(1));
			result.setDescription(cursor.getString(2));
			
			//images
			byte[] img1Byte = cursor.getBlob(3);
			if (img1Byte != null && img1Byte.length > 0) {
				Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
				if (image1 != null) {
					result.setImage1(image1);
				}
			}

			result.setFolderId(Integer.parseInt(cursor.getString(4)));
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return result;
	}

	public List<InfoFolder> getAllFolders() {
		List<InfoFolder> result = new ArrayList<InfoFolder>();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_FOLDER_NAME, TABLE_FOLDER_COLUMNS, null, null,
					null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				InfoFolder folder = cursorToFolder(cursor);
				result.add(folder);
				cursor.moveToNext();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			// make sure to close the cursor
			if(cursor!=null){
				cursor.close();
			}
		}
		return result;
	}

	private InfoFolder cursorToFolder(Cursor cursor) {
		InfoFolder result = new InfoFolder();
		try {
			result.setId(Integer.parseInt(cursor.getString(0)));
			result.setTitle(cursor.getString(1));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}

	public int updateItem(InfoItem item) {
		int cnt = 0;
		try {

			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(ITEM_COLUMN_NAME, item.getTitle());
			values.put(ITEM_COLUMN_DESCRIPTION, item.getDescription());
			
			//images
			Bitmap image1 = item.getImage1();
			if (image1 != null) {
				byte[] data = getBitmapAsByteArray(image1);
				if (data != null && data.length > 0) {
					values.put(ITEM_COLUMN__IMAGE1, data);
				}
			}
			else{
				values.putNull(ITEM_COLUMN__IMAGE1);
			}

			// update
			cnt = db.update(TABLE_ITEMS_NAME, values, ITEM_COLUMN_ID + " = ?",
					new String[] { String.valueOf(item.getId()) });
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return cnt;
	}

	public int updateFolder(InfoFolder folder) {
		int i = 0;
		try {
			

			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(FOLDER_COLUMN_TITLE, folder.getTitle());

			// update
			i = db.update(TABLE_FOLDER_NAME, values, FOLDER_COLUMN_ID + " = ?",
					new String[] { String.valueOf(folder.getId()) });
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return i;
	}

	public void deleteItem(InfoItem item) {

		try {

			// delete item
			db.delete(TABLE_ITEMS_NAME, ITEM_COLUMN_ID + " = ?",
					new String[] { String.valueOf(item.getId()) });
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public void deleteFolder(InfoFolder folder) {
		boolean succeded = false;
		try {

			// delete folder
			int rowAffected = db.delete(TABLE_FOLDER_NAME, FOLDER_COLUMN_ID + " = ?",
					new String[] { String.valueOf(folder.getId()) });
			if(rowAffected>0) {
				succeded = true;
			}
			
		} catch (Throwable t) {
			succeded = false;
			t.printStackTrace();
		} finally {
			if(succeded){
				deleteFolderItems(folder);
			}
		}

	}

	private void deleteFolderItems(InfoFolder folder) {

		try {

			// delete items
			db.delete(TABLE_ITEMS_NAME, ITEM_COLUMN_FOLDERID + " = ?",
					new String[] { String.valueOf(folder.getId()) });
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}

	private boolean isTableExist(String name, SQLiteDatabase db) {

		Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ name + "'", null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		return false;
	}

	
	public List<InfoItem> getAllItemsOfFolder(InfoFolder folder) {
		List<InfoItem> result = new ArrayList<InfoItem>();
		Cursor cursor = null;
		try {
			int floderId = folder.getId();
			cursor = db.query(TABLE_ITEMS_NAME, TABLE_ITEM_COLUMNS, ITEM_COLUMN_FOLDERID +" = ?",
					new String[] { String.valueOf(floderId) }, null, null,
					null, null);

			if(cursor!=null && cursor.getCount()>0){
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					InfoItem item = cursorToItem(cursor);
					result.add(item);
					cursor.moveToNext();
				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			if(cursor!=null) {
				// make sure to close the cursor
				cursor.close();
			}
		}
		return result;
	}

	public void open() {
		try {
		 	db = getWritableDatabase();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void close() {
		try {
			db.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
