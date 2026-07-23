package pl.anytlogout;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class SpawnWall {

    public static void create(Plugin plugin) {

        Bukkit.getScheduler().runTask(plugin, () -> {

            World world = Bukkit.getWorld("world");

            if (world == null) {
                plugin.getLogger().warning("Nie znaleziono świata world!");
                return;
            }

            int x1 = 100;
            int z1 = 100;

            int x2 = 200;
            int z2 = 100;

            int x3 = 200;
            int z3 = 200;

            int x4 = 100;
            int z4 = 200;


            Material glass = Material.RED_STAINED_GLASS;


            int maxHeight = world.getMaxHeight();


            // ściana 1
            for (int x = x1; x <= x2; x++) {
                for (int y = world.getHighestBlockYAt(x, z1); y < maxHeight; y++) {
                    world.getBlockAt(x, y, z1).setType(glass);
                }
            }


            // ściana 2
            for (int z = z2; z <= z3; z++) {
                for (int y = world.getHighestBlockYAt(x2, z); y < maxHeight; y++) {
                    world.getBlockAt(x2, y, z).setType(glass);
                }
            }


            // ściana 3
            for (int x = x4; x <= x3; x++) {
                for (int y = world.getHighestBlockYAt(x, z3); y < maxHeight; y++) {
                    world.getBlockAt(x, y, z3).setType(glass);
                }
            }


            // ściana 4
            for (int z = z1; z <= z4; z++) {
                for (int y = world.getHighestBlockYAt(x1, z); y < maxHeight; y++) {
                    world.getBlockAt(x1, y, z).setType(glass);
                }
            }


            plugin.getLogger().info("Ściana spawna gotowa!");

        });
    }
}
