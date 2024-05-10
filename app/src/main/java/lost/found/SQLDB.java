package lost.found;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Items.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Item_table";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TYPE = "item_type";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";


     SQLDB(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addItem(String type, String name, String phone, String desc, String date, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_DESCRIPTION, desc);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_LOCATION, location);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
        {
            Toast.makeText(context, "FAILED TO ADD", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "ADDED ITEM!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    Cursor readData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db!=null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void RemoveTask(String row_id)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1)
        {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
        }
    }

}
