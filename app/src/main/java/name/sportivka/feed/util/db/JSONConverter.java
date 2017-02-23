package name.sportivka.feed.util.db;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniil on 23.02.17.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class JSONConverter extends TypeConverter<String, JSONObject> {

    @Override
    public String getDBValue(JSONObject model) {
        return model == null ? null : model.toString();
    }

    @Override
    public JSONObject getModelValue(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            // you should consider logging or throwing exception.
        }
        return jsonObject;
    }
}
