package org.infinispan.container.versioning;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.context.Flag;
import org.infinispan.distribution.DistributionTestHelper;
import org.infinispan.distribution.MagicKey;
import org.infinispan.test.fwk.CleanupAfterMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.RollbackException;
import javax.transaction.Transaction;

@Test(testName = "container.versioning.DistWriteSkewTest", groups = "functional")
@CleanupAfterMethod
public class DistWriteSkewTest extends AbstractClusteredWriteSkewTest {

   @Override
   protected CacheMode getCacheMode() {
      return CacheMode.DIST_SYNC;
   }

   @Override
   protected int clusterSize() {
      return 4;
   }

   public void testWriteSkew() throws Exception {
      Cache<Object, Object> cache0 = cache(0);
      Cache<Object, Object> cache1 = cache(1);
      Cache<Object, Object> cache2 = cache(2);
      Cache<Object, Object> cache3 = cache(3);

      MagicKey hello = new MagicKey("hello", cache(2));

      // Auto-commit is true
      cache1.put(hello, "world 1");

      tm(1).begin();
      assert "world 1".equals(cache1.get(hello));
      Transaction t = tm(1).suspend();

      // Induce a write skew
      cache3.put(hello, "world 3");

      assert cache0.get(hello).equals("world 3");
      assert cache1.get(hello).equals("world 3");
      assert cache2.get(hello).equals("world 3");
      assert cache3.get(hello).equals("world 3");

      tm(1).resume(t);
      cache1.put(hello, "world 2");

      try {
         tm(1).commit();
         assert false : "Transaction should roll back";
      } catch (RollbackException re) {
         // expected
      }

      assert "world 3".equals(cache0.get(hello));
      assert "world 3".equals(cache1.get(hello));
      assert "world 3".equals(cache2.get(hello));
      assert "world 3".equals(cache3.get(hello));
   }

   public void testWriteSkewOnNonOwner() throws Exception {
      Cache<Object, Object> cache0 = cache(0);
      Cache<Object, Object> cache1 = cache(1);
      Cache<Object, Object> cache2 = cache(2);
      Cache<Object, Object> cache3 = cache(3);

      MagicKey hello = new MagicKey("hello", cache(0)); // Owned by cache0 and cache1

      int owners[] = {0, 0};
      int nonOwners[] = {0, 0};
      int j=0, k = 0;
      for (int i=0; i<4; i++) {
         if (DistributionTestHelper.isOwner(cache(i), hello))
            owners[j++] = i;
         else
            nonOwners[k++] = i;
      }

      // Auto-commit is true
      cache(owners[1]).put(hello, "world 1");

      tm(nonOwners[0]).begin();
      assert "world 1".equals(cache(nonOwners[0]).get(hello));
      Transaction t = tm(nonOwners[0]).suspend();

      // Induce a write skew
      cache(nonOwners[1]).put(hello, "world 3");

      assert cache0.get(hello).equals("world 3");
      assert cache1.get(hello).equals("world 3");
      assert cache2.get(hello).equals("world 3");
      assert cache3.get(hello).equals("world 3");

      tm(nonOwners[0]).resume(t);
      cache(nonOwners[0]).put(hello, "world 2");

      try {
         tm(nonOwners[0]).commit();
         assert false : "Transaction should roll back";
      } catch (RollbackException re) {
         // expected
      }

      assert "world 3".equals(cache0.get(hello));
      assert "world 3".equals(cache1.get(hello));
      assert "world 3".equals(cache2.get(hello));
      assert "world 3".equals(cache3.get(hello));
   }

   public void testWriteSkewMultiEntries() throws Exception {
      Cache<Object, Object> cache0 = cache(0);
      Cache<Object, Object> cache1 = cache(1);
      Cache<Object, Object> cache2 = cache(2);
      Cache<Object, Object> cache3 = cache(3);

      MagicKey hello = new MagicKey("hello", cache(2));
      MagicKey hello2 = new MagicKey("hello2", cache(3));
      MagicKey hello3 = new MagicKey("hello3", cache(0));

      tm(1).begin();
      cache1.put(hello, "world 1");
      cache1.put(hello2, "world 1");
      cache1.put(hello3, "world 1");
      tm(1).commit();

      tm(1).begin();
      cache1.put(hello2, "world 2");
      cache1.put(hello3, "world 2");
      assert "world 1".equals(cache1.get(hello));
      assert "world 2".equals(cache1.get(hello2));
      assert "world 2".equals(cache1.get(hello3));
      Transaction t = tm(1).suspend();

      // Induce a write skew
      // Auto-commit is true
      cache3.put(hello, "world 3");

      for (Cache<Object, Object> c : caches()) {
         assert "world 3".equals(c.get(hello));
         assert "world 1".equals(c.get(hello2));
         assert "world 1".equals(c.get(hello3));
      }

      tm(1).resume(t);
      cache1.put(hello, "world 2");

      try {
         tm(1).commit();
         assert false : "Transaction should roll back";
      } catch (RollbackException re) {
         // expected
      }

      for (Cache<Object, Object> c : caches()) {
         assert "world 3".equals(c.get(hello));
         assert "world 1".equals(c.get(hello2));
         assert "world 1".equals(c.get(hello3));
      }
   }

   public void testNullEntries() throws Exception {
      Cache<Object, Object> cache0 = cache(0);
      Cache<Object, Object> cache1 = cache(1);
      Cache<Object, Object> cache2 = cache(2);
      Cache<Object, Object> cache3 = cache(3);

      MagicKey hello = new MagicKey("hello", cache(2));

      // Auto-commit is true
      cache0.put(hello, "world");

      tm(0).begin();
      assert "world".equals(cache0.get(hello));
      Transaction t = tm(0).suspend();

      cache1.remove(hello);

      assert null == cache0.get(hello);
      assert null == cache1.get(hello);
      assert null == cache2.get(hello);
      assert null == cache3.get(hello);

      tm(0).resume(t);
      cache0.put(hello, "world2");

      try {
         tm(0).commit();
         assert false : "This transaction should roll back";
      } catch (RollbackException expected) {
         // expected
      }

      assert null == cache0.get(hello);
      assert null == cache1.get(hello);
      assert null == cache2.get(hello);
      assert null == cache3.get(hello);
   }

   public void testResendPrepare() throws Exception {
      Cache<Object, Object> cache0 = cache(0);
      Cache<Object, Object> cache1 = cache(1);
      Cache<Object, Object> cache2 = cache(2);
      Cache<Object, Object> cache3 = cache(3);

      MagicKey hello = new MagicKey("hello", cache(2));

      // Auto-commit is true
      cache0.put(hello, "world");

      // create a write skew
      tm(2).begin();
      assert "world".equals(cache2.get(hello));
      Transaction t = tm(2).suspend();

      // Implicit tx.  Prepare should be retried.
      cache(0).put(hello, "world 2");

      assert cache0.get(hello).equals("world 2");
      assert cache1.get(hello).equals("world 2");
      assert cache2.get(hello).equals("world 2");
      assert cache3.get(hello).equals("world 2");

      tm(2).resume(t);
      cache2.put(hello, "world 3");

      try {
         tm(2).commit();
         assert false : "This transaction should roll back";
      } catch (RollbackException expected) {
         // expected
      }

      assert cache0.get(hello).equals("world 2");
      assert cache1.get(hello).equals("world 2");
      assert cache2.get(hello).equals("world 2");
      assert cache3.get(hello).equals("world 2");
   }

   public void testLocalOnlyPut() {
      localOnlyPut(this.<Integer, String>cache(0), 1, "v1");
      localOnlyPut(this.<Integer, String>cache(1), 2, "v2");
      localOnlyPut(this.<Integer, String>cache(2), 3, "v3");
      localOnlyPut(this.<Integer, String>cache(3), 4, "v4");
   }

   public void testSameNodeKeyCreation() throws Exception {
      tm(0).begin();
      Assert.assertEquals(cache(0).get("NewKey"), null);
      cache(0).put("NewKey", "v1");
      Transaction tx0 = tm(0).suspend();

      //other transaction do the same thing
      tm(0).begin();
      Assert.assertEquals(cache(0).get("NewKey"), null);
      cache(0).put("NewKey", "v2");
      tm(0).commit();

      tm(0).resume(tx0);
      try {
         tm(0).commit();
         Assert.fail("The transaction should rollback");
      } catch (RollbackException expected) {
         //expected
      }

      Assert.assertEquals(cache(0).get("NewKey"), "v2");
      Assert.assertEquals(cache(1).get("NewKey"), "v2");
   }

   public void testDifferentNodeKeyCreation() throws Exception {
      tm(0).begin();
      Assert.assertEquals(cache(0).get("NewKey"), null);
      cache(0).put("NewKey", "v1");
      Transaction tx0 = tm(0).suspend();

      //other transaction, in other node,  do the same thing
      tm(1).begin();
      Assert.assertEquals(cache(1).get("NewKey"), null);
      cache(1).put("NewKey", "v2");
      tm(1).commit();

      tm(0).resume(tx0);
      try {
         tm(0).commit();
         Assert.fail("The transaction should rollback");
      } catch (RollbackException expected) {
         //expected
      }

      Assert.assertEquals(cache(0).get("NewKey"), "v2");
      Assert.assertEquals(cache(1).get("NewKey"), "v2");
   }

   private void localOnlyPut(Cache<Integer, String> cache, Integer k, String v) {
      cache.getAdvancedCache().withFlags(Flag.CACHE_MODE_LOCAL).put(k, v);
   }

}
