package name.sportivka.feed.model;

import com.raizlabs.android.dbflow.annotation.Database;

import static name.sportivka.feed.model.MyDatabase.NAME;
import static name.sportivka.feed.model.MyDatabase.VERSION;

/**
 * Created by daniil on 23.02.17.
 */

@Database(name = NAME, version = VERSION)
public class MyDatabase {
    public static final String NAME = "MyDB";
    public static final int VERSION = 1;
}