package io.github.guy7cc.cruiseskyblock.core.gui.sidebar;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.ProfiledTimer;

import java.util.List;


public class ProfiledTimerComponentFactory {
    public static List<SidebarComponent> all(String profile) {
        return List.of(title(profile), avrTime(profile), maxTime(profile), minTime(profile));
    }

    public static SidebarComponent title(String profile) {
        return new SimpleSidebarComponent("Tracking: " + profile, "ProfiledTimer");
    }

    public static SidebarComponent maxTime(String profile) {
        return new SidebarComponent("ProfiledTimer") {
            @Override
            public void tick(int globalTick) {
                if (globalTick % 20 == 0) {
                    ProfiledTimer.Tracker tracker = CruiseSkyblock.timer.getTracker(profile);
                    if (tracker != null) {
                        long nanoTime = tracker.getMax();
                        setText("Max: " + nanoTime / 1000 + "µs");
                    }
                }
            }
        };
    }

    public static SidebarComponent minTime(String profile) {
        return new SidebarComponent("ProfiledTimer") {
            @Override
            public void tick(int globalTick) {
                if (globalTick % 20 == 0) {
                    ProfiledTimer.Tracker tracker = CruiseSkyblock.timer.getTracker(profile);
                    if (tracker != null) {
                        long nanoTime = tracker.getMin();
                        setText("Min: " + nanoTime / 1000 + "µs");
                    }
                }
            }
        };
    }

    public static SidebarComponent avrTime(String profile) {
        return new SidebarComponent("ProfiledTimer") {
            @Override
            public void tick(int globalTick) {
                if (globalTick % 20 == 0) {
                    ProfiledTimer.Tracker tracker = CruiseSkyblock.timer.getTracker(profile);
                    if (tracker != null) {
                        double nanoTime = tracker.getAvr();
                        setText("Avr: " + (long) (nanoTime / 1000) + "µs");
                    }
                }
            }
        };
    }
}
