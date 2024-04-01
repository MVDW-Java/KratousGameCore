package a.a.a.d.c;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandHandler1 {
   private Map az = new HashMap();

   public void a(GamePlayer gPlayer, PlayerInteractEvent e) {
      if (!gPlayer.getPlayer().isSneaking() && this.az.containsKey(gPlayer)) {
         GoldWandType type = (GoldWandType)this.az.get(gPlayer);
         if (gPlayer.dZ() < type.F()) {
            gPlayer.getPlayer().sendMessage(Core.a("Oops, You don't have enough gold for a " + type.getName() + "!"));
            return;
         }

         boolean clickBlock = e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK);
         switch(type) {
         case aq:
            if (!clickBlock) {
               return;
            }

            Location location = e.getClickedBlock().getRelative(BlockFace.UP).getLocation();
            if (location.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR) || !location.getBlock().getType().equals(Material.AIR) || location.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.STONE_BUTTON) || location.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.GOLD_BLOCK)) {
               gPlayer.getPlayer().sendMessage(Core.a("Invalid position! Try placing it on the ground."));
               return;
            }

            if (Core.d().cX().y(gPlayer)) {
               return;
            }

            Core.d().cX().a(gPlayer, type, e.getClickedBlock().getRelative(BlockFace.UP));
            break;
         case ar:
            if (!clickBlock) {
               return;
            }

            if (Core.d().cX().y(gPlayer)) {
               return;
            }

            Core.d().cX().a(gPlayer, type, e.getClickedBlock());
            break;
         case au:
            if (!clickBlock) {
               return;
            }

            Block block = e.getClickedBlock();
            if (block.getType().equals(Material.AIR) || block.getType().equals(Material.STAINED_GLASS) || block.getType().equals(Material.COBBLESTONE_STAIRS) || block.getType().equals(Material.TRIPWIRE) || block.getType().equals(Material.STRING) || block.getType().equals(Material.STEP) || block.getType().equals(Material.SMOOTH_STAIRS) || !block.getRelative(BlockFace.UP).getType().equals(Material.AIR) || block.getType().equals(Material.STONE_BUTTON) || block.getType().equals(Material.GOLD_BLOCK) || block.getType().equals(Material.STONE_PLATE)) {
               gPlayer.getPlayer().sendMessage(Core.a("Invalid position! Try placing it on the ground."));
               return;
            }

            block.getRelative(BlockFace.UP).setType(Material.STONE_PLATE);
            break;
         default:
            return;
         }

         e.setCancelled(true);
         gPlayer.dO();
         gPlayer.s(-type.F());
      } else {
         Inventory inventory = Bukkit.createInventory(gPlayer.getPlayer(), 9, "Choose Wand mode");
         GoldWandType[] var4 = GoldWandType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            GoldWandType type = var4[var6];
            ItemStack stack = new ItemStack(type.getMaterial());
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(type.getName());
            meta.setLore(type.getLore());
            stack.setItemMeta(meta);
            inventory.setItem(type.G(), stack);
         }

         gPlayer.getPlayer().openInventory(inventory);
      }

   }

   public void a(GamePlayer gPlayer, PlayerInteractEntityEvent e) {
      GoldWandType type = (GoldWandType)this.az.get(gPlayer);
      if (type != null && !gPlayer.getPlayer().isSneaking()) {
         if (gPlayer.dZ() < type.F()) {
            gPlayer.getPlayer().sendMessage(Core.a("Oops, You don't have enough gold for a " + type.getName() + "!"));
         } else {
            Entity entity = e.getRightClicked();
            label58:
            switch(type) {
            case as:
               if (gPlayer.getPlayer().isInsideVehicle()) {
                  gPlayer.getPlayer().sendMessage(Core.a("You are already riding someone!"));
                  return;
               }

               if (gPlayer.getPlayer().getPassenger() != null && gPlayer.getPlayer().getPassenger().equals(entity)) {
                  return;
               }

               for(int count = 1; entity.getPassenger() != null && count < 10; ++count) {
                  entity = entity.getPassenger();
               }

               if (entity.getPassenger() != null) {
                  gPlayer.getPlayer().sendMessage(Core.a("Oops! Could not put you in this stack"));
                  return;
               }

               if (entity instanceof Player) {
                  GamePlayer animal = Core.c().r(entity.getName());
                  if (animal.dH() || animal.dI()) {
                     gPlayer.getPlayer().sendMessage(Core.a("You cannot ride this player!"));
                     return;
                  }

                  if (!animal.getPlayer().setPassenger(gPlayer.getPlayer())) {
                     return;
                  }

                  gPlayer.getPlayer().sendMessage(Core.a("You are now riding on " + ChatColor.GOLD + animal.getPlayer().getName()));
               }
               break;
            case at:
               entity.setVelocity(gPlayer.getPlayer().getEyeLocation().getDirection().setY(1).multiply(2));
               Iterator var6 = Core.c().getPlayers().iterator();

               while(true) {
                  if (!var6.hasNext()) {
                     break label58;
                  }

                  GamePlayer other = (GamePlayer)var6.next();
                  other.a(EnumParticle.LAVA, entity.getLocation().add(0.0D, 1.0D, 0.0D), 1.0F, 10);
               }
            default:
               return;
            }

            e.setCancelled(true);
            gPlayer.dO();
            gPlayer.s(-type.F());
         }
      }
   }

   public void a(GamePlayer gPlayer, InventoryClickEvent e) {
      e.setCancelled(true);
      if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
         if (e.getInventory().getTitle().equalsIgnoreCase("Choose Wand mode")) {
            GoldWandType wandType = this.a(e.getCurrentItem().getType());
            if (wandType != null) {
               this.az.put(gPlayer, wandType);
               gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + wandType.getName() + ChatColor.DARK_AQUA + " is now active"));
               gPlayer.getPlayer().sendMessage(Core.a("Sneak + click to change the selected item!"));
               gPlayer.getPlayer().closeInventory();
               gPlayer.dO();
            }
         }
      }
   }

   private GoldWandType a(Material material) {
      GoldWandType[] var2 = GoldWandType.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         GoldWandType type = var2[var4];
         if (type.getMaterial().equals(material)) {
            return type;
         }
      }

      return null;
   }

   public ItemStack b(GamePlayer gPlayer) {
      ItemStack stack = new ItemStack(Material.BLAZE_ROD);
      ItemMeta meta = stack.getItemMeta();
      meta.setDisplayName(ChatColor.GOLD + "Wand" + ChatColor.GRAY + " (Right click to use)");
      if (!this.az.containsKey(gPlayer)) {
         meta.setDisplayName(ChatColor.GOLD + "Wand" + ChatColor.GRAY + " (Right click to use)");
      } else {
         GoldWandType type = (GoldWandType)this.az.get(gPlayer);
         meta.setDisplayName(ChatColor.GOLD + "Wand - " + type.getName() + " " + (type.F() > gPlayer.dZ() ? ChatColor.RED.toString() : ChatColor.GREEN.toString()) + type.F() + " Gold" + ChatColor.GRAY + " (Right click to use)");
      }

      meta.setLore(new ArrayList());
      stack.setItemMeta(meta);
      stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      return stack;
   }
}
