package org.ru.shad0vsky.zpixel.faction_dep.gui.utilities;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Utility;
import org.ru.shad0vsky.zpixel.ZPixelReboot;

import java.util.*;

@RequiredArgsConstructor
public class FactionGUIBuilder {

    private final @NonNull String               viewName;
    private final @NonNull String               viewTag;
    private final          Map<int[], SGButton> buttons = new HashMap<>();

    public FactionGUIBuilder addButton(@NonNull SGButton button, int[] slots) {
        buttons.put(slots, button);
        return this;
    }

    public SGMenu build() {
        SGMenu menu = ZPixelReboot.getInstance().getSpiGUI().create(viewName, calculateRows(), viewTag);

        for ( int[] slots : buttons.keySet() ) {
            SGButton button = buttons.get(slots);
            for ( int slot : slots ) {
                menu.setButton(slot, button);
            }
        }

        return menu;
    }

    @Utility
    private int calculateRows() {
        return (buttons.keySet().stream().flatMapToInt(Arrays::stream).max().orElse(0) / 9 + 1) * 9;
    }
}
