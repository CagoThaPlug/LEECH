package com.plugins.Collections;

import com.plugins.Collections.query.TileItemQuery;

import java.util.ArrayList;
import java.util.List;

public class TileItems {
    public static List<ETileItem> tileItems = new ArrayList<>();

    public static TileItemQuery search() {
        return new TileItemQuery(tileItems);
    }
}
