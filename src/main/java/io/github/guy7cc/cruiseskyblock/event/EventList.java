package io.github.guy7cc.cruiseskyblock.event;

import java.util.List;

public class EventList<T> {
    private List<T> list;

    public EventList(List<T> innerList) {
        list = innerList;
    }

    public boolean add(T listener) {
        return list.add(listener);
    }

    public boolean remove(T listener) {
        return list.remove(listener);
    }
}
