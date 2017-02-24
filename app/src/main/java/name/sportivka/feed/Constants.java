package name.sportivka.feed;

/**
 * Created by daniil on 23.02.17.
 */

public class Constants {
    public static final String EXCLUDE_WITHOUT_FLOW = "preview_html,first_img,text_cut,polls";
    public static final String EXCLUDE_WITH_FLOW = "preview_html,first_img,text_cut,polls,flow";
    public static final int FIRST_PAGE = 1;
    public static final String INCLUDE = "text_html";
    public static final int PER_PAGE = 30;

    public static final String API_URL = "https://api.habrahabr.ru/v1/";
    public static final String API_URL_DEV = "https://api.habrahabr.ru/v1/";
    public static final String API_AUTH_URL = "https://habrahabr.ru/auth/o/";
    public static final String USER_AGENT = "habr-android";
    public static final int CONNECT_TIMEOUT = 5;
    public static final int READ_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
    public static final int DELAY = 2000;

    public static final String[] FILTER_TYPE = new String[]{"daily",
            "weekly",
            "monthly",
            "alltime"};

    public static final String TYPE_ACTION = "type_action";
    public static final String CATEGORY = "category";
    public static final String HUB_CATEGORY = "hub_category";
    public static final String HUB = "hub";
    public static final String CATEGORY_TITLE = "category_title";

    public static final String[] TYPES = new String[]{
            "best", "interesting", "all"
    };

    public enum SearchType {
        DATE("date"),
        RATING("rating"),
        RELEVANCE("relevance");

        String name;

        SearchType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
