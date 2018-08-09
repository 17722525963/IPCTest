package com.zsrun.ipctest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 2018/8/9 14:15
 * <p>
 * 测试学习ContentProvider 通过此类模拟返回 Activity界面需要查询的数据
 *
 * @author zsr
 * @version 1.0
 */

public class TestDictionaryProvider extends ContentProvider {

    private static UriMatcher uriMatcher;//类似于过滤器，过滤匹配不同的Uri

    private static String AUTHORITY = "com.zsrun.test.DiationaryContentProvider";

    private static final int SINGLE_CODE = 1;
    private static final int PREFIX_CODES = 2;

    private String DATABASE_FILENAME = "test.db";
    private String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/testDictionary/";
    private SQLiteDatabase database;

    static {
        /**
         * 添加访问ContentProvider的Uri
         */
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "single", SINGLE_CODE);
        uriMatcher.addURI(AUTHORITY, "prefix/*", PREFIX_CODES);
    }

    /**
     * 该方法在Activity 的onCreate方法之前被调用
     */
    @Override
    public boolean onCreate() {
//        database = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_FILENAME, null);//TODO 假定有这个数据库····
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_CODE:
                //查找指定的的单词，匹配single
                cursor = database.query("t_words", projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case PREFIX_CODES:
                String word = uri.getPathSegments().get(1);//获取携带的参数
                cursor = database
                        .rawQuery("", new String[]{});//TODO 此处根据获取到的参数查询数据
                break;
            default:
                throw new IllegalArgumentException("<" + uri + ">格式不正确.");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
