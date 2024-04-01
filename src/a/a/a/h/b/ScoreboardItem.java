package a.a.a.h.b;

import org.bukkit.ChatColor;

public class ScoreboardItem {
   private String fy = "";
   private String name;
   private int fz;
   private ChatColor prefixColor;
   private PersonalScoreboardType fA;
   private boolean enabled;
   private int fB;

   public ScoreboardItem(String name, ChatColor prefix, PersonalScoreboardType type, int score) {
      this.name = name;
      this.fz = score;
      this.prefixColor = prefix;
      this.fA = type;
      this.enabled = true;
      this.fB = 0;
   }

   public String getName() {
      return this.name;
   }

   public int getScore() {
      return this.fz;
   }

   public PersonalScoreboardType dm() {
      return this.fA;
   }

   public String dn() {
      return this.fy;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean value) {
      this.enabled = value;
   }

   public String ScoreboardString() {
      if ((prefixColor + name).length() <= 16)  return this.prefixColor + this.name;

      String tempName = this.name + "     " + this.name;
      int maxLength = this.fA.equals(PersonalScoreboardType.fn) ? 20 : 16;
      maxLength -= this.prefixColor.toString().length();
      ++this.fB;
      if (this.name.substring(0, 10).equals(tempName.substring(this.fB, this.fB + 10)) || this.fB + maxLength > tempName.length() - 1) {
         this.fB = 0;
      }

      fy = this.prefixColor + tempName.substring(this.fB, this.fB + maxLength);
      return fy;
   }

   public String l(String val) {
      this.name = val;
      this.fy = this.prefixColor + val;
      if (this.fy.length() > 16) {
         this.fy = this.fy.substring(0, 15);
      } else {
         this.fB = 0;
      }

      return this.fy;
   }
}
