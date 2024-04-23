package io.github.guy7cc.cruiseskyblock.core.gui.sidebar;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;

import java.util.function.Consumer;

public abstract class SidebarComponent implements Tickable {
    public final String tag;
    private String text = "";
    protected Consumer<String> refreshTextCallback;

    public SidebarComponent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (!this.text.equals(text) && refreshTextCallback != null) refreshTextCallback.accept(text);
        this.text = text;
    }

    protected void registerSidebar(Sidebar sidebar, int index) {
        refreshTextCallback = t -> sidebar.refreshText(index, t);
    }
}
