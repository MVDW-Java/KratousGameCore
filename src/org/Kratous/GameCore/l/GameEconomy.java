package org.Kratous.GameCore.l;

import org.Kratous.GameCore.Core;
import java.util.List;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class GameEconomy extends AbstractEconomy {
   private Core gq;

   public GameEconomy(Core plugin) {
      this.gq = plugin;
   }

   public boolean isEnabled() {
      return true;
   }

   public String getName() {
      return "GameEconomy";
   }

   public int fractionalDigits() {
      return 0;
   }

   public String format(double v) {
      return "" + v;
   }

   public String currencyNamePlural() {
      return "gold";
   }

   public String currencyNameSingular() {
      return "gold";
   }

   public double getBalance(String playerName) {
      return (double)Core.c().r(playerName).dZ();
   }

   public boolean has(String playerName, double v) {
      return (double)Core.c().r(playerName).dZ() >= v;
   }

   public EconomyResponse withdrawPlayer(String playerName, double v) {
      Double d = new Double(v);
      int gold = d.intValue();
      Core.c().r(playerName).s(-gold);
      return new EconomyResponse(v, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
   }

   public EconomyResponse depositPlayer(String playerName, double v) {
      Double d = new Double(v);
      int gold = d.intValue();
      Core.c().r(playerName).s(gold);
      return new EconomyResponse(v, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
   }

   public boolean hasAccount(String playerName, String world) {
      return this.hasAccount(playerName);
   }

   public double getBalance(String playerName, String world) {
      return this.getBalance(playerName);
   }

   public boolean has(String playerName, String world, double v) {
      return this.has(playerName, v);
   }

   public EconomyResponse withdrawPlayer(String playerName, String world, double v) {
      return this.withdrawPlayer(playerName, v);
   }

   public EconomyResponse depositPlayer(String playerName, String world, double v) {
      return this.depositPlayer(playerName, v);
   }

   public boolean createPlayerAccount(String playerName) {
      return true;
   }

   public boolean createPlayerAccount(String playerName, String world) {
      return true;
   }

   public boolean hasAccount(String playerName) {
      return true;
   }

   public boolean hasBankSupport() {
      return false;
   }

   public EconomyResponse createBank(String playerName, String world) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse deleteBank(String playerName) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse bankBalance(String playerName) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse bankHas(String playerName, double v) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse bankWithdraw(String playerName, double v) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse bankDeposit(String playerName, double v) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse isBankOwner(String playerName, String world) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public EconomyResponse isBankMember(String playerName, String world) {
      return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "MGMC does not support bank accounts!");
   }

   public List getBanks() {
      return null;
   }
}
