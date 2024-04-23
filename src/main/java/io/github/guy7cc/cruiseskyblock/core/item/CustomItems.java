package io.github.guy7cc.cruiseskyblock.core.item;

import io.github.guy7cc.cruiseskyblock.core.system.Registry;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class CustomItems {
    public static final Registry<CustomItem> REGISTRY = new Registry<>("item");

    public static final CustomItem BACK_BUTTON = REGISTRY.register(new GuiItem(1000, "back_button"));
    public static final CustomItem NEXT_BUTTON = REGISTRY.register(new GuiItem(1001, "next_button"));
    public static final CustomItem GOLDEN_EYE = REGISTRY.register(new GuiItem(1002, "golden_eye"));
    public static final CustomItem EXAMPLE_ITEM = REGISTRY.register(new ExampleItem(10000));

    @Nullable
    public static CustomItem byId(int id) {
        return REGISTRY.byId(id);
    }

    @Nullable
    public static CustomItem byItemStack(ItemStack itemStack) {
        if (itemStack == null) return null;
        int id = CustomItem.getId(itemStack);
        if (id < 0) return null;
        return CustomItems.byId(id);
    }

    public static boolean is(ItemStack itemStack, CustomItem type) {
        CustomItem actualType = CustomItems.byItemStack(itemStack);
        return actualType == type;
    }
}
