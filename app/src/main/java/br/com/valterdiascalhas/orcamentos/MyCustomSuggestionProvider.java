package br.com.valterdiascalhas.orcamentos;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by George on 21/08/2016.
 * JUST TOO COMPLICATED
 */
public class MyCustomSuggestionProvider extends ContentProvider {
    DBAdapter dbAdapter;
    SQLiteDatabase sqLiteDatabase;

    private static final String AUTHORITY = "br.com.valterdiascalhas.orcamento.MyCustomSuggestionProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cliente");
    private static final int SUGGESTIONS_CLIENTE = 1;
    private static final int SEARCH_CLIENTE = 2;
    private static final int GET_CLIENTE = 3;

    UriMatcher mUriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGESTIONS_CLIENTE);
        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/countries" of searchable.xml
        uriMatcher.addURI(AUTHORITY, "cliente", SEARCH_CLIENTE);

        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Country details for CountryActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CountryDB.java
        uriMatcher.addURI(AUTHORITY, "cliente/#", GET_CLIENTE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbAdapter = new DBAdapter(getContext());
        sqLiteDatabase = dbAdapter.mydbHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        switch (mUriMatcher.match(uri)) {
            case SUGGESTIONS_CLIENTE:
                c = dbAdapter.getClientes(selectionArgs);
                break;
            case SEARCH_CLIENTE:
                c = dbAdapter.getClientes(selectionArgs);
                break;
            case GET_CLIENTE:
                String id = uri.getLastPathSegment();
                c = dbAdapter.getCliente(id);
        }
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

}