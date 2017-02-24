package name.sportivka.feed.event;

import name.sportivka.feed.model.feed.Hub;

/**
 * Created by daniil on 24.02.17.
 */
public class TypePostsEvent {
    int type;
    Hub hub;

    public TypePostsEvent(int type) {
        this.type = type;
    }

    public TypePostsEvent(Hub hub, int type) {
        this.type = type;
        this.hub = hub;
    }

    public int getType() {
        return type;
    }

    public Hub getHub() {
        return hub;
    }
}
