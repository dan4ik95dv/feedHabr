package name.sportivka.feed.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class PostWebView extends WebView {
    private OnPageScrollSizeChangeListener onPageScrollSizeChangeListener;

    public PostWebView(Context context) {
        super(context);
    }

    public PostWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PostWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected int computeVerticalScrollExtent() {
        if (this.onPageScrollSizeChangeListener != null) {
            this.onPageScrollSizeChangeListener.onPageScrollSizeChange();
        }
        return super.computeVerticalScrollExtent();
    }

    public void setOnPageScrollSizeChangeListener(OnPageScrollSizeChangeListener onPageScrollSizeChangeListener) {
        this.onPageScrollSizeChangeListener = onPageScrollSizeChangeListener;
    }

    public interface OnPageScrollSizeChangeListener {
        void onPageScrollSizeChange();
    }
}