package a.a.a.l;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class GamePerms extends Permission {
   private Permission gr;

   public GamePerms(Permission provider) {
      this.gr = provider;
   }

   public GamePerms() {
      this.gr = null;
   }

   public String getName() {
      return "GamePerms";
   }

   public boolean isEnabled() {
      return false;
   }

   public boolean hasSuperPermsCompat() {
      return false;
   }

   public boolean has(Player player, String permission) {
      return this.gr != null ? this.gr.has(player, permission) : player.hasPermission(permission);
   }

   public boolean playerHas(String s, String s1, String s2) {
      return false;
   }

   public boolean playerAdd(String s, String s1, String s2) {
      return false;
   }

   public boolean playerRemove(String s, String s1, String s2) {
      return false;
   }

   public boolean groupHas(String s, String s1, String s2) {
      return false;
   }

   public boolean groupAdd(String s, String s1, String s2) {
      return false;
   }

   public boolean groupRemove(String s, String s1, String s2) {
      return false;
   }

   public boolean playerInGroup(String s, String s1, String s2) {
      return false;
   }

   public boolean playerInGroup(Player player, String group) {
      return this.gr == null ? false : this.gr.playerInGroup(player, group);
   }

   public boolean playerAddGroup(String s, String s1, String s2) {
      return false;
   }

   public boolean playerRemoveGroup(String s, String s1, String s2) {
      return false;
   }

   public String[] getPlayerGroups(String s, String s1) {
      return new String[0];
   }

   public String getPrimaryGroup(String s, String s1) {
      return null;
   }

   public String[] getGroups() {
      return new String[0];
   }

   public boolean hasGroupSupport() {
      return false;
   }
}
