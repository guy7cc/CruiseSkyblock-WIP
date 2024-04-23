package io.github.guy7cc.cruiseskyblock.core.item;

import io.github.guy7cc.cruiseskyblock.core.system.RegistryObject;
import io.github.guy7cc.cruiseskyblock.core.role.Role;
import io.github.guy7cc.cruiseskyblock.core.role.RoleGroup;
import io.github.guy7cc.cruiseskyblock.util.TranslationUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class CustomItem extends RegistryObject {
    public static final String TAG_NAME = "CSBCustomItemData";

    public final int loreLineNum;
    public final int maxUseCount;
    public final int cooldownTick;
    public final CustomItemUsage usage;
    @Nullable
    public final List<Role> roles;
    @Nullable
    public final RoleGroup roleGroup;

    private CustomItem(int id, String name, int loreLineNum, int maxUseCount, int cooldownTick, CustomItemUsage usage, List<Role> roles, RoleGroup roleGroup) {
        super(id, name);
        this.loreLineNum = loreLineNum;
        this.maxUseCount = maxUseCount;
        this.cooldownTick = cooldownTick;
        this.usage = usage;
        this.roles = roles;
        this.roleGroup = roleGroup;
    }

    protected CustomItem(int id, String name, int loreLineNum, int maxUseCount, int cooldownTick, CustomItemUsage usage) {
        this(id, name, loreLineNum, maxUseCount, cooldownTick, usage, null, null);
    }

    protected CustomItem(int id, String name, int loreLineNum, int maxUseCount, int cooldownTick, CustomItemUsage usage, List<Role> roles) {
        this(id, name, loreLineNum, maxUseCount, cooldownTick, usage, roles, null);
    }

    protected CustomItem(int id, String name, int loreLineNum, int maxUseCount, int cooldownTick, CustomItemUsage usage, RoleGroup roleGroup) {
        this(id, name, loreLineNum, maxUseCount, cooldownTick, usage, null, roleGroup);
    }

    public static int getId(ItemStack itemStack) {
        net.minecraft.world.item.ItemStack rawItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (!rawItemStack.hasTag()) return -1;
        CompoundTag tag = rawItemStack.getTag();
        if (tag.contains(TAG_NAME)) {
            CompoundTag customItemTag = tag.getCompound(TAG_NAME);
            if (customItemTag.contains("Id")) return customItemTag.getInt("Id");
            else return -1;
        }
        return -1;
    }

    public final ItemStack give() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        net.minecraft.world.item.ItemStack rawItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tag = createTag(maxUseCount);
        rawItemStack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(rawItemStack);
        return itemStack;
    }

    public CompoundTag createTag(int useCount) {
        CompoundTag customItemTag = new CompoundTag();
        customItemTag.putInt("Id", id);
        customItemTag.putUUID("UUID", UUID.randomUUID());
        if (maxUseCount > 0) customItemTag.putInt("Use", useCount);

        CompoundTag displayTag = new CompoundTag();

        TranslatableComponent nameComponent = new TranslatableComponent(getTranslationKey());
        nameComponent.setItalic(false);
        if (maxUseCount > 0) {
            TextComponent useCountComponent = new TextComponent(" " + useCount + "/" + maxUseCount);
            ChatColor color;
            if (useCount > 3 && useCount > maxUseCount * 3 / 10) color = ChatColor.AQUA;
            else if (useCount > 1 && useCount > maxUseCount / 10) color = ChatColor.GREEN;
            else color = ChatColor.RED;
            useCountComponent.setColor(color);
            nameComponent.addExtra(useCountComponent);
        }

        ListTag loreListTag = new ListTag();

        for (int i = 0; i < loreLineNum; i++) {
            loreListTag.add(toStringTag(new TranslatableComponent(getTranslationKey() + ".lore" + i)));
        }
        loreListTag.add(toStringTag(new TextComponent(" ")));
        if (usage != CustomItemUsage.NONE) {
            loreListTag.add(toStringTag(new TranslatableComponent(TranslationUtil.guiKey("itemUsage"), new TranslatableComponent(TranslationUtil.guiKey("itemUsage." + usage.getKey())))));
        }
        TranslatableComponent roleComponent = new TranslatableComponent(TranslationUtil.guiKey("role"));
        roleComponent.addExtra(new TextComponent(": "));
        if (roles == null && roleGroup == null) {
            roleComponent.addExtra(new TranslatableComponent(TranslationUtil.guiKey("role.all")));
        } else if (roles != null) {
            StringBuilder sb = new StringBuilder();
            for (Role role : roles) sb.append(role.icon + " ");
            roleComponent.addExtra(new TextComponent(sb.toString()));
        } else {
            roleComponent.addExtra(new TranslatableComponent(TranslationUtil.guiKey("roleGroup." + roleGroup.toString().toLowerCase(Locale.ROOT))));
        }
        loreListTag.add(toStringTag(roleComponent));

        displayTag.putString("Name", ComponentSerializer.toString(nameComponent));
        displayTag.put("Lore", loreListTag);

        CompoundTag tag = new CompoundTag();
        tag.putInt("CustomModelData", id);
        tag.put(TAG_NAME, customItemTag);
        tag.put("display", displayTag);
        return tag;
    }

    private static StringTag toStringTag(BaseComponent component) {
        TextComponent result = new TextComponent("");
        result.setItalic(false);
        result.setColor(ChatColor.GRAY);
        result.addExtra(component);
        return StringTag.valueOf(ComponentSerializer.toString(result));
    }

    public boolean hasUseCount() {
        return maxUseCount > 0;
    }

    public void decreaseUseCount(Player player, int index) {
        if (maxUseCount <= 0) return;
        ItemStack itemStack = player.getInventory().getItem(index);
        if (getId(itemStack) != id) return;
        net.minecraft.world.item.ItemStack rawItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag customItemTag = rawItemStack.getTag().getCompound(TAG_NAME);
        int useCount = customItemTag.getInt("Use");
        if (useCount > 1) {
            rawItemStack.setTag(createTag(useCount - 1));
            itemStack = CraftItemStack.asBukkitCopy(rawItemStack);
            player.getInventory().setItem(index, itemStack);
        } else player.getInventory().setItem(index, null);
    }

    public void decreaseUseCount(Player player, EquipmentSlot slot) {
        int index = -1;
        switch (slot) {
            case HAND -> index = player.getInventory().getHeldItemSlot();
            case OFF_HAND -> index = 45;
            case FEET -> index = 8;
            case LEGS -> index = 7;
            case CHEST -> index = 6;
            case HEAD -> index = 5;
        }
        if (index >= 0) decreaseUseCount(player, index);
    }

    public boolean hasCooldownTick() {
        return cooldownTick > 0;
    }

    public CustomItemInteractionResult use(ItemStack self, Player player, EquipmentSlot slot, Action action) {
        return CustomItemInteractionResult.UNAVAILABLE;
    }

    public CustomItemInteractionResult useOnEntity(ItemStack self, Player player, EquipmentSlot slot, Entity entity) {
        return CustomItemInteractionResult.UNAVAILABLE;
    }

    public void tickInEquipmentSlot(ItemStack self, Player player, EquipmentSlot slot) {

    }

    public void tickInQuickBar(ItemStack self, Player player, int index) {

    }
}
