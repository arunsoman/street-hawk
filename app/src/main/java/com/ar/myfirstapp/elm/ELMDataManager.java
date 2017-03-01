package com.ar.myfirstapp.elm;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Arun Soman on 2/28/2017.
 */

class ELMDataManager {
    private HashMap<String, ArrayList<Tupple>> store = new HashMap<>();

    class Tupple {
        long ts;
        String data;

        Tupple(String resp) {
            ts = System.currentTimeMillis();
            data = resp;
        }
    }

    public void addResponse(String resp) {
        Tupple tupple = new Tupple(resp);
        String key = resp.substring(0, 2);
        ArrayList<Tupple> tupples;
        if (!store.containsKey(key)) {
            tupples = new ArrayList<>();
            store.put(key, tupples);
        } else {
            tupples = store.get(key);
        }
        tupples.add(tupple);
    }
}
