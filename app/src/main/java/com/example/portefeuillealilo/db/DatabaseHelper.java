package com.example.portefeuillealilo.db;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.portefeuillealilo.model.Portefeuille;
import com.example.portefeuillealilo.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_PPORTFEUILLE = "portefuille";
   // private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_SOLDE = "user_solde";
    private static final String COLUMN_USER_REVENU = "user_Revenu";
    private static final String COLUMN_USER_DEPENSES = "user_depenses";

    // PPORTFEUILLE  Table Columns names
    private static final String COLUMN_PORT_ID = "portfi_id";
    private static final String COLUMN_PORT_USER_ID = "user_id";
    private static final String COLUMN_PORT_TITLE = "title";
    private static final String COLUMN_PORT_TYPE = "type"; // depense or revenu
    private static final String COLUMN_PORT_DATE = "date";
    private static final String COLUMN_SOLDE = "solde";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"+ COLUMN_USER_SOLDE + " TEXT," +COLUMN_USER_DEPENSES + " TEXT," +COLUMN_USER_REVENU + " TEXT," +COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create table sql query
    private String CREATE_PORT_TABLE = "CREATE TABLE " + TABLE_PPORTFEUILLE + "("
            + COLUMN_PORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PORT_TITLE + " TEXT,"
            + COLUMN_PORT_TYPE + " TEXT,"+ COLUMN_SOLDE + " TEXT," +COLUMN_PORT_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +COLUMN_PORT_USER_ID + " INTEGER" + ")";


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_PORT_TABLE  = "DROP TABLE IF EXISTS " + TABLE_PPORTFEUILLE;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PORT_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PORT_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_SOLDE, user.getSolde());
        values.put(COLUMN_USER_REVENU, user.getRevenu());
        values.put(COLUMN_USER_DEPENSES, user.getDepenses());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    /**
     * This method is to create user record
     *
     * @param port
     */
    public boolean addPortefi(Portefeuille port) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PORT_TITLE, port.getTitle());
        values.put(COLUMN_SOLDE, port.getSolde());
        values.put(COLUMN_PORT_TYPE, port.getType());
        values.put(COLUMN_PORT_USER_ID, port.getUser_id());
        // Inserting Row
        db.insert(TABLE_PPORTFEUILLE, null, values);
        db.close();
        return true;
    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_REVENU,
                COLUMN_USER_DEPENSES,
                COLUMN_USER_SOLDE
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setSolde(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SOLDE)));
                user.setdepenses(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DEPENSES)));
                user.setRevenu(cursor.getString(cursor.getColumnIndex(COLUMN_USER_REVENU)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public List<Portefeuille> getAllPortf(int user_ids) {
        // array of columns to fetch
        String user_id = String.valueOf(user_ids);
        String[] columns = {
                COLUMN_PORT_TITLE,
                COLUMN_PORT_TYPE,
                COLUMN_PORT_DATE,
                COLUMN_SOLDE,
        };
        // sorting orders
        String sortOrder =
                COLUMN_PORT_DATE + " ASC";
        List<Portefeuille> userList = new ArrayList<Portefeuille>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = {user_id};
        String selection = COLUMN_PORT_USER_ID + " = ?";
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor2 = db.query(TABLE_PPORTFEUILLE, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {
                Portefeuille port = new Portefeuille();
              //  port.setPortfi_id(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PORT_ID))));
                port.setTitle(cursor2.getString(cursor2.getColumnIndex(COLUMN_PORT_TITLE)));
                port.setType(cursor2.getString(cursor2.getColumnIndex(COLUMN_PORT_TYPE)));
                port.setSolde(cursor2.getString(cursor2.getColumnIndex(COLUMN_SOLDE)));
                port.setDate(cursor2.getString(cursor2.getColumnIndex(COLUMN_PORT_DATE)));
                // Adding user record to list
                userList.add(port);
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db.close();

        // return user list
        return userList;
    }




    @SuppressLint("Range")
    public User getUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from user where user_id="+id+"", null );
        User user = new User();
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
             //   User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setSolde(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SOLDE)));
                user.setdepenses(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DEPENSES)));
                user.setRevenu(cursor.getString(cursor.getColumnIndex(COLUMN_USER_REVENU)));
                // Adding user record to list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return user;
    }


    @SuppressLint("Range")
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from user where user_email= ? ", new String[]{email} );
        User user = new User();
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //   User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setSolde(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SOLDE)));
                user.setdepenses(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DEPENSES)));
                user.setRevenu(cursor.getString(cursor.getColumnIndex(COLUMN_USER_REVENU)));
                // Adding user record to list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return user;
    }


    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to update user record
     *
     * @param
     */
    public void updateBalance(String balance, String dependence, String revenu, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_SOLDE, balance);
        values.put(COLUMN_USER_DEPENSES, dependence);
        values.put(COLUMN_USER_REVENU, revenu);
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }



}
