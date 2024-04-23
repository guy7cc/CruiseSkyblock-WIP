package io.github.guy7cc.cruiseskyblock.core.item;

import net.md_5.bungee.api.chat.TranslatableComponent;

public enum CustomItemInteractionResult {
    UNAVAILABLE(false, null),
    IN_COOLDOWN(false, null),
    FAIL_NO_TARGET(true, new TranslatableComponent("csb.chat.noTarget")),
    FAIL_LACK_MP(true, new TranslatableComponent("csb.chat.lackMp")),
    SUCCESS(false, null);

    public final boolean shouldShowMessage;
    public final TranslatableComponent message;

    CustomItemInteractionResult(boolean shouldShowMessage, TranslatableComponent message) {
        this.shouldShowMessage = shouldShowMessage;
        this.message = message;
    }
}
