package pl.anytlogout;

import org.bukkit.plugin.java.JavaPlugin;

public class AnyTLogout extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager()
                .registerEvents(new CombatListener(this), this);

        getLogger().info("AnyTLogout wlaczony!");
    }
}
