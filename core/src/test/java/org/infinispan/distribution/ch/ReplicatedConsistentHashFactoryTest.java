package org.infinispan.distribution.ch;

import org.infinispan.commons.hash.MurmurHash3;
import org.infinispan.distribution.TestAddress;
import org.infinispan.remoting.transport.Address;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test even distribution after membership change
 *
 * @author Radim Vansa &lt;rvansa@redhat.com&gt;
 */
@Test(groups = "unit", testName = "distribution.ch.ReplicatedConsistentHashFactoryTest")
public class ReplicatedConsistentHashFactoryTest {

   public void test1() {
      int[] testSegments = { 1, 2, 4, 8, 16, 31, 32, 33, 67, 128};

      ReplicatedConsistentHashFactory factory = new ReplicatedConsistentHashFactory();
      Address A = new TestAddress(0, "A");
      Address B = new TestAddress(1, "B");
      Address C = new TestAddress(2, "C");
      Address D = new TestAddress(3, "D");
      List<Address> a = Arrays.asList(A);
      List<Address> ab = Arrays.asList(A, B);
      List<Address> abc = Arrays.asList(A, B, C);
      List<Address> abcd = Arrays.asList(A, B, C, D);
      List<Address> bcd = Arrays.asList(B, C, D);
      List<Address> c = Arrays.asList(C);

      for (int segments : testSegments) {
         ReplicatedConsistentHash ch = factory.create(new MurmurHash3(), 0, segments, a);
         checkDistribution(ch);

         ch = factory.updateMembers(ch, ab);
         checkDistribution(ch);

         ch = factory.updateMembers(ch, abc);
         checkDistribution(ch);

         ch = factory.updateMembers(ch, abcd);
         checkDistribution(ch);

         ch = factory.updateMembers(ch, bcd);
         checkDistribution(ch);

         ch = factory.updateMembers(ch, c);
         checkDistribution(ch);
      }
   }

   private void checkDistribution(ReplicatedConsistentHash ch) {
      int minSegments = Integer.MAX_VALUE, maxSegments = Integer.MIN_VALUE;
      OwnershipStatistics stats = new OwnershipStatistics(ch, ch.getMembers());
      for (Address member : ch.getMembers()) {
         int primary = stats.getPrimaryOwned(member);
         minSegments = Math.min(minSegments, primary);
         maxSegments = Math.max(maxSegments, primary);
         assertEquals(stats.getOwned(member), ch.getNumSegments());
      }
      assertTrue(maxSegments - minSegments <= 1);
   }
}
