package com.plugins.API;

import com.plugins.Collections.Bank;
import com.plugins.Collections.query.ItemQuery;
import net.runelite.api.widgets.Widget;

public class BankUtil {

    public static ItemQuery nameContainsNoCase(String name) {
        return Bank.search().filter(widget -> widget.getName().toLowerCase().contains(name.toLowerCase()));
    }
    public static int getItemAmount(int itemId) {
        return getItemAmount(itemId, false);
    }

    public static int getItemAmount(int itemId, boolean stacked) {
        return stacked ?
                Bank.search().withId(itemId).first().map(Widget::getItemQuantity).orElse(0) :
                Bank.search().withId(itemId).result().size();
    }

    public static int getItemAmount(String itemName) {
        return nameContainsNoCase(itemName).result().size();
    }


    public static boolean hasItem(int id) {
        return hasItem(id, 1, false);
    }

    public static boolean hasItem(int id, int amount) {
        return getItemAmount(id, false) >= amount;
    }

    public static boolean hasItem(int id, int amount, boolean stacked) {
        return getItemAmount(id, stacked) >= amount;
    }

    public static boolean hasAny(int ...ids) {
        for (int id : ids) {
            if (getItemAmount(id) > 0) {
                return true;
            }
        }
        return false;
    }
}
