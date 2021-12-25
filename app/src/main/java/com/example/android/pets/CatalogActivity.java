package com.example.android.pets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.data.PetContract;
import com.example.android.data.PetContract.PetEntry;
import com.example.android.data.PetsDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /** Database helper that will provide us access to the database */
    private PetsDbHelper mDbHelper;

    private CursorAdapter cursorAdapter;
    private static final int PET_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PetsDbHelper(this);

        //displayDatabaseInfo();

        ListView listView = (ListView)findViewById(R.id.list);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        cursorAdapter = new PetCursorAdapter(this,null);
        // Attach the adapter to the ListView.
        listView.setAdapter(cursorAdapter);
        // Kick off the Loader
        getSupportLoaderManager().initLoader(PET_LOADER , null,this);

        // Setup the item click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Create New Intent to go to Editor Activity
                Intent intent = new Intent(CatalogActivity.this,EditorActivity.class);

                // Form the Content Uri that represents the specific pet that was clicked on
                //by appending the id(that was passed as input to this method) onto the
                //{@link PetEntry#ContentUri};
                // For example the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on
                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI,id);

                //Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the Editor Activity to Display the data of the current Pet
                startActivity(intent);

            }
        });

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();

    } */

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    // To access our database, we instantiate our subclass of SQLiteOpenHelper
    // and pass the context, which is the current activity.

    /*private void displayDatabaseInfo() {


        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        } ;
        //String selection = PetEntry.COLUMN_PET_GENDER + " =?";
        //String[] selectionArgs = {String.valueOf(PetEntry.GENDER_UNKNOWN)};
        Cursor cursor = db.query(
                PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        Cursor cursor = getContentResolver().query(
                PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        ListView listView = (ListView)findViewById(R.id.list);
        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
         cursorAdapter = new PetCursorAdapter(this,cursor);
        // Attach the adapter to the ListView.
        listView.setAdapter(cursorAdapter); */



        //TextView displayView = (TextView) findViewById(R.id.text_view_pet);

       /* try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            /*displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(PetEntry._ID + " - " +
                    PetEntry.COLUMN_PET_NAME + " - " +
                    PetEntry.COLUMN_PET_BREED + " - " +
                    PetEntry.COLUMN_PET_GENDER + " - " +
                    PetEntry.COLUMN_PET_WEIGHT +"\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight));



        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }*/

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */

    private void insertDemoData(){
        // Gets the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,"Toto");
        values.put(PetEntry.COLUMN_PET_BREED,"Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER,PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT,7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        //long newRowId = db.insert(PetEntry.TABLE_NAME,null,values);

        Uri newRowUri = getContentResolver().insert(PetEntry.CONTENT_URI,values);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDemoData();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deletePets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePets() {
        // If all Pets are Already deleted and no Pets are there to get Deleted then show Message Pets Are Empty
        if(cursorAdapter.isEmpty())
        {
            Toast.makeText(this,"Pets are empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI,null,null);
            if(rowsDeleted>0)
                Toast.makeText(this, "All Pets Deleted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Pet Deletion Failed",Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
        } ;

        return new CursorLoader(this, PetEntry.CONTENT_URI, projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        cursorAdapter.swapCursor(null);

    }
}