package club.mineplex.clans.cache;

import club.mineplex.clans.enums.ModRole;
import club.mineplex.clans.utils.UtilHTTP;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ModRolesCache extends ModCache {

    static HashMap<UUID, ArrayList<ModRole>> modRoles = new HashMap<>();

    @Override
    public HashMap<UUID, ArrayList<ModRole>> get() {
        return modRoles;
    }

    @Override
    public void updateCache() {
        modRoles.clear();

        String scrape;
        try {
            scrape = UtilHTTP.mineplexScrape("https://api.mineplex.club/clansmod/roles");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        HashMap<String, ArrayList<String>> uuids = gson.fromJson(scrape, HashMap.class);
        for (String teamName : uuids.keySet()) {
            ArrayList<String> uuidStrings = uuids.get(teamName);
            ModRole role = ModRole.of(teamName);
            if (role == null) continue;

            for (String uuidString : uuidStrings) {
                UUID uuid = UUID.fromString(uuidString);
                ArrayList<ModRole> currentRoles = modRoles.getOrDefault(uuid, new ArrayList<>());
                currentRoles.add(role);
                modRoles.put(uuid, currentRoles);
            }
        }

    }

}
