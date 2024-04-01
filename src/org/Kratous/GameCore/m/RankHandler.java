package org.Kratous.GameCore.m;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RankHandler {
   private Map ix = new HashMap();

   public void fb() {
      this.ix.clear();
   }

   public Rank z(int XP) {
      Rank myRank = (Rank)this.ix.get(0);

      Rank rank;
      for(Iterator var3 = this.ix.values().iterator(); var3.hasNext(); myRank = rank) {
         rank = (Rank)var3.next();
         if (rank.fa() > XP) {
            break;
         }
      }

      return myRank;
   }

   public Rank A(int XP) {
      Rank myRank = this.z(XP);
      return this.ix.containsKey(myRank.eZ() + 1) ? (Rank)this.ix.get(myRank.eZ() + 1) : myRank;
   }
}
