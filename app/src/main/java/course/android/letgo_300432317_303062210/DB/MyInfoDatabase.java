package course.android.letgo_300432317_303062210.DB;

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

import course.android.letgo_300432317_303062210.Classes.Product;
import course.android.letgo_300432317_303062210.Classes.User;

public class MyInfoDatabase extends SQLiteOpenHelper {

	//All the functions about how our database is implement

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "letgo_db";

	// folder table
	//user table
	private static final String TABLE_USERS_NAME = "users";
	private static final String FOLDER_COLUMN_NAME = "name";

	private static final String[] TABLE_USER_COLUMNS = { FOLDER_COLUMN_NAME};

	// item table
	//product table
	private static final String TABLE_PRODUCTS_NAME = "products";
	private static final String ITEM_COLUMN_ID = "id";
	private static final String ITEM_COLUMN_NAME = "title";
	private static final String ITEM_COLUMN_DESCRIPTION = "description";
	private static final String ITEM_COLUMN_PRICE = "price";
	private static final String ITEM_COLUMN_LOCATION = "location";
	private static final String ITEM_COLUMN_CATEGORY = "category";
	private static final String ITEM_COLUMN__IMAGE1 = "image";
	private static final String ITEM_COLUMN_FOLDERNAME = "userId";

	private static final String[] TABLE_ITEM_COLUMNS = {ITEM_COLUMN_ID, ITEM_COLUMN_NAME,
			ITEM_COLUMN_DESCRIPTION,ITEM_COLUMN_PRICE, ITEM_COLUMN_LOCATION, ITEM_COLUMN_CATEGORY, ITEM_COLUMN__IMAGE1, ITEM_COLUMN_FOLDERNAME};


	private SQLiteDatabase db;
	private Context ctx;

	public MyInfoDatabase(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.ctx = ctx;
	}




	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// SQL statement to create item table
			String CREATE_ITEM_TABLE = "create table if not exists " + TABLE_PRODUCTS_NAME +" ( "
					+ ITEM_COLUMN_ID +" TEXT PRIMARY KEY, "
					+ ITEM_COLUMN_NAME +" TEXT, "
					+ ITEM_COLUMN_DESCRIPTION + " TEXT, "
					+ ITEM_COLUMN_PRICE+" TEXT, "
					+ ITEM_COLUMN_LOCATION + " TEXT, "
					+ ITEM_COLUMN_CATEGORY + " TEXT, "
					+ ITEM_COLUMN__IMAGE1 + " BLOB, "
					+ ITEM_COLUMN_FOLDERNAME + " TEXT"
					+ ")";
			db.execSQL(CREATE_ITEM_TABLE);

			if (!isTableExist(TABLE_USERS_NAME, db)) {
				// SQL statement to create item table
				String CREATE_FOLDER_TABLE = "create table if not exists "+ TABLE_USERS_NAME+" ( "
						+ FOLDER_COLUMN_NAME +" TEXT PRIMARY KEY)";


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

	public boolean createItem(Product product) {
		long result = 0;
		try {
			result = -1;
			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(ITEM_COLUMN_ID, product.getId());
			values.put(ITEM_COLUMN_NAME, product.getTitle());
			values.put(ITEM_COLUMN_DESCRIPTION, product.getDescription());
			values.put(ITEM_COLUMN_PRICE, product.getPrice());
			values.put(ITEM_COLUMN_LOCATION, product.getLocation());
			values.put(ITEM_COLUMN_CATEGORY, product.getCategory());
			values.put(ITEM_COLUMN_FOLDERNAME, product.getUserId());

			//images
			Bitmap image1 = product.getImage1();
			if (image1 != null) {
				byte[] data = getBitmapAsByteArray(image1);
				if (data != null && data.length > 0) {
					values.put(ITEM_COLUMN__IMAGE1, data);
				}
			}

			// insert item
			result=db.insert(TABLE_PRODUCTS_NAME, null, values);


		} catch (Throwable t) {
			t.printStackTrace();
		}

		if(result>0){
			return true;
		}
		return false;
	}


  public boolean createItemWithoutImg(Product product) {
    long result = 0;
    try {
      result = -1;
      // make values to be inserted
      ContentValues values = new ContentValues();
      values.put(ITEM_COLUMN_ID, product.getId());
      values.put(ITEM_COLUMN_NAME, product.getTitle());
      values.put(ITEM_COLUMN_DESCRIPTION, product.getDescription());
      values.put(ITEM_COLUMN_PRICE, product.getPrice());
      values.put(ITEM_COLUMN_LOCATION, product.getLocation());
      values.put(ITEM_COLUMN_CATEGORY, product.getCategory());
      values.put(ITEM_COLUMN_FOLDERNAME, product.getUserId());


      // insert item
      result=db.insert(TABLE_PRODUCTS_NAME, null, values);


    } catch (Throwable t) {
      t.printStackTrace();
    }

    if(result>0){
      return true;
    }
    return false;
  }

	private byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	public boolean createFolder(User user) {
		long result=0;
		try {
			result=-1;
			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(FOLDER_COLUMN_NAME, user.getName());

			// insert folder
			result=db.insert(TABLE_USERS_NAME, null, values);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if(result>0){
			return true;
		}
		return false;

	}

	public Product readItem(String id) {
		Product item = null;
		Cursor cursor = null;
		try {
			/// get reference of the itemDB database

			// get  query
			cursor = db
					.query(TABLE_PRODUCTS_NAME,
							TABLE_ITEM_COLUMNS, ITEM_COLUMN_ID + " = ?",
							new String[] {  id }, null, null,
							null, null);



			// if results !=null, parse the first one
			if(cursor!=null && cursor.getCount()>0){

				cursor.moveToFirst();

				item = new Product();
				item.setId(cursor.getString(0));
				item.setTitle(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				item.setPrice(cursor.getString(3));
				item.setLocation(cursor.getString(4));
				item.setCategory(cursor.getString(5));




				//images
				byte[] img1Byte = cursor.getBlob(6);
				if (img1Byte != null && img1Byte.length > 0) {
					Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
					if (image1 != null) {
						item.setImage1(image1);
					}
				}


				item.setUserId(cursor.getString(7));
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

	public User readFolder(String name) {
		User folder = null;
		Cursor cursor = null;
		try {
			// get reference of the folderDB database

			// get  query
			cursor = db
					.query(TABLE_USERS_NAME, // a. table
							TABLE_USER_COLUMNS, FOLDER_COLUMN_NAME + " = ?",
							new String[] { name }, null, null,
							null, null);

			// if results !=null, parse the first one
			if (cursor != null)
				cursor.moveToFirst();

			folder = new User(cursor.getString(0));
			//folder.setName(cursor.getString(0));


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

	public List<Product> getAllItems() {
		List<Product> result = new ArrayList<Product>();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_PRODUCTS_NAME, TABLE_ITEM_COLUMNS, null, null,
					null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Product item = cursorToItem(cursor);
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

	private Product cursorToItem(Cursor cursor) {
		Product result = new Product();
		try {
			result.setId(cursor.getString(0));
			result.setTitle(cursor.getString(1));
			result.setDescription(cursor.getString(2));
			result.setPrice(cursor.getString(3));
			result.setLocation(cursor.getString(4));
			result.setCategory(cursor.getString(5));

			//images
			byte[] img1Byte = cursor.getBlob(6);
			if (img1Byte != null && img1Byte.length > 0) {
				Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
				if (image1 != null) {
					result.setImage1(image1);
				}
			}

			//there is a problem with the next line
			result.setUserId(cursor.getString(7));
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return result;
	}

	public List<User> getAllFolders() {
		List<User> result = new ArrayList<User>();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_USERS_NAME, TABLE_USER_COLUMNS, null, null,
					null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				User folder = cursorToFolder(cursor);
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

	private User cursorToFolder(Cursor cursor) {
		User result = null;
		try {
			//result.setName(cursor.getString(0));
      result=new User(cursor.getString(0));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}

	public boolean updateItem(Product item) {
		long result=-1;

		try {

			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(ITEM_COLUMN_ID, item.getId());
			values.put(ITEM_COLUMN_NAME, item.getTitle());
			values.put(ITEM_COLUMN_DESCRIPTION, item.getDescription());
			values.put(ITEM_COLUMN_PRICE,item.getPrice());
			values.put(ITEM_COLUMN_LOCATION,item.getLocation());
			values.put(ITEM_COLUMN_CATEGORY,item.getCategory());
			values.put(ITEM_COLUMN_FOLDERNAME,item.getUserId());

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
			result = db.update(TABLE_PRODUCTS_NAME, values, ITEM_COLUMN_ID + " = ?",
					new String[] { item.getId() });
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if(result>0){
			return true;
		}
		return false;


	}


  public boolean updateItemWithoutImage(Product item) {
    long result=-1;

    try {

      // make values to be inserted
      ContentValues values = new ContentValues();
      values.put(ITEM_COLUMN_ID, item.getId());
      values.put(ITEM_COLUMN_NAME, item.getTitle());
      values.put(ITEM_COLUMN_DESCRIPTION, item.getDescription());
      values.put(ITEM_COLUMN_PRICE,item.getPrice());
      values.put(ITEM_COLUMN_LOCATION,item.getLocation());
      values.put(ITEM_COLUMN_CATEGORY,item.getCategory());
      values.put(ITEM_COLUMN_FOLDERNAME,item.getUserId());


      // update
      result = db.update(TABLE_PRODUCTS_NAME, values, ITEM_COLUMN_ID + " = ?",
        new String[] { item.getId() });
    } catch (Throwable t) {
      t.printStackTrace();
    }
    if(result>0){
      return true;
    }
    return false;


  }

	public int updateProductImage(String id, Bitmap bm) {

		int cnt = 0;
		try {

			// make values to be inserted
			ContentValues values = new ContentValues();

			//images
			if (bm != null) {
				byte[] data = Product.getImgAsByteArray(bm);
				if (data != null && data.length > 0) {
					values.put(ITEM_COLUMN__IMAGE1, data);
				}
			}
			else{
				values.putNull(ITEM_COLUMN__IMAGE1);
			}

			// update
			cnt = db.update(TABLE_PRODUCTS_NAME, values, ITEM_COLUMN_ID + " = ?",
					new String[] { id });
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return  cnt;
	}

	public boolean updateFolder(User folder) {
		long result=-1;
		try {


			// make values to be inserted
			ContentValues values = new ContentValues();
			values.put(FOLDER_COLUMN_NAME, folder.getName());

			// update
			result = db.update(TABLE_USERS_NAME, values, FOLDER_COLUMN_NAME + " = ?",
					new String[] { folder.getName() });
		} catch (Throwable t) {
			t.printStackTrace();
		}

		if(result>0){
			return true;
		}
		return false;

	}

	public boolean deleteItem(Product product) {

		String[] arguments = {product.getId()};

		int result = db.delete(TABLE_PRODUCTS_NAME, ITEM_COLUMN_ID+" = ?",arguments);

		if(result>0){
			return true;
		}
		return false;


	}

	public boolean deleteFolder(User folder) {

		int result=-1;
		try {


			// delete folder
			String[] arguments = {folder.getName()};

			 result = db.delete(TABLE_USERS_NAME, FOLDER_COLUMN_NAME+" = ?",arguments);

		} catch (Throwable t) {

			t.printStackTrace();
		} finally {
			if(result==1){
				deleteFolderItems(folder);
				return true;
			}
			return false;
		}

	}

	private void deleteFolderItems(User folder) {

		try {

			// delete items

			db.delete(TABLE_PRODUCTS_NAME, ITEM_COLUMN_FOLDERNAME + " = ?",
					new String[] { folder.getName() });
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


	public List<Product> getAllItemsOfFolder(User folder) {
      List<Product> result = new ArrayList<Product>();
    Cursor cursor = null;
    try {

      String folderName = folder.getName();
      cursor = db.query(TABLE_PRODUCTS_NAME, TABLE_ITEM_COLUMNS, ITEM_COLUMN_FOLDERNAME +" = ?",
        new String[] { folderName }, null, null,
        null, null);

      if(cursor!=null && cursor.getCount()>0){
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          Product item = cursorToItem(cursor);
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


  public List<Product> getAllFavoriteItems(User user) {
    List<Product> result = new ArrayList<Product>();
    Cursor cursor = null;
    try {

      String folderName = user.getName();
      cursor = db.query(TABLE_PRODUCTS_NAME, TABLE_ITEM_COLUMNS, ITEM_COLUMN_FOLDERNAME +" = ?",
        new String[] { folderName }, null, null,
        null, null);

      if(cursor!=null && cursor.getCount()>0){
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          Product item = cursorToItem(cursor);
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
			if(db!=null){
			db.close();}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}

