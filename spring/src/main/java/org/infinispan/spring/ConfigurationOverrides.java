package org.infinispan.spring;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.distribution.ch.ConsistentHash;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.eviction.EvictionThreadPolicy;
import org.infinispan.interceptors.base.CommandInterceptor;
import org.infinispan.remoting.ReplicationQueue;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.lookup.TransactionManagerLookup;
import org.infinispan.commons.util.Util;
import org.infinispan.util.concurrent.IsolationLevel;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

import java.util.List;

/**
 * <p>
 * Helper class to override select values in an Infinispan
 * {@link org.infinispan.configuration.cache.Configuration <code>Configuration</code>}.
 * </p>
 * 
 * @author <a href="mailto:olaf DOT bergner AT gmx DOT de">Olaf Bergner</a>
 * 
 */
public final class ConfigurationOverrides {

   private final Log logger = LogFactory.getLog(getClass());

   private Long eagerDeadlockSpinDuration;

   private Boolean useEagerDeadlockDetection;

   private Boolean useLockStriping;

   private Boolean unsafeUnreliableReturnValues;

   private Long rehashRpcTimeout;

   private Boolean writeSkewCheck;

   private Integer concurrencyLevel;

   private Integer replQueueMaxElements;

   private Long replQueueInterval;

   private String replQueueClass;

   private Boolean exposeJmxStatistics;

   private Boolean invocationBatchingEnabled;

   private Boolean fetchInMemoryState;

   private Boolean alwaysProvideInMemoryState;

   private Long lockAcquisitionTimeout;

   private Long syncReplTimeout;

   private String cacheModeString;

   private Long expirationWakeUpInterval;

   private EvictionStrategy evictionStrategy;

   private String evictionStrategyClass;

   private EvictionThreadPolicy evictionThreadPolicy;

   private String evictionThreadPolicyClass;

   private Integer evictionMaxEntries;

   private Long expirationLifespan;

   private Long expirationMaxIdle;

   private String transactionManagerLookupClass;

   private TransactionManagerLookup transactionManagerLookup;

   private Boolean syncCommitPhase;

   private Boolean syncRollbackPhase;

   private Boolean useEagerLocking;

   private Boolean useReplQueue;

   private IsolationLevel isolationLevel;

   private Long stateRetrievalTimeout;

   private Integer stateRetrievalMaxNonProgressingLogWrites;

   private Integer stateRetrievalChunkSize;

   private Long stateRetrievalInitialRetryWaitTime;

   private String isolationLevelClass;

   private Boolean useLazyDeserialization;

   private Boolean l1CacheEnabled;

   private Long l1Lifespan;

   private Boolean l1OnRehash;

   private String consistentHashClass;

   private Integer numOwners;

   private Boolean rehashEnabled;

   private Boolean useAsyncMarshalling;

   private Boolean indexingEnabled;

   private Boolean indexLocalOnly;

   private List<? extends CommandInterceptor> customInterceptors;

   /**
    * @param eagerDeadlockSpinDuration
    *           the eagerDeadlockSpinDuration to set
    */
   public void setDeadlockDetectionSpinDuration(final Long eagerDeadlockSpinDuration) {
      this.eagerDeadlockSpinDuration = eagerDeadlockSpinDuration;
   }

   /**
    * @param useEagerDeadlockDetection
    *           the useEagerDeadlockDetection to set
    */
   public void setEnableDeadlockDetection(final Boolean useEagerDeadlockDetection) {
      this.useEagerDeadlockDetection = useEagerDeadlockDetection;
   }

   /**
    * @param useLockStriping
    *           the useLockStriping to set
    */
   public void setUseLockStriping(final Boolean useLockStriping) {
      this.useLockStriping = useLockStriping;
   }

   /**
    * @param unsafeUnreliableReturnValues
    *           the unsafeUnreliableReturnValues to set
    */
   public void setUnsafeUnreliableReturnValues(final Boolean unsafeUnreliableReturnValues) {
      this.unsafeUnreliableReturnValues = unsafeUnreliableReturnValues;
   }

   /**
    * @param rehashRpcTimeout
    *           the rehashRpcTimeout to set
    */
   public void setRehashRpcTimeout(final Long rehashRpcTimeout) {
      this.rehashRpcTimeout = rehashRpcTimeout;
   }

   /**
    * @param writeSkewCheck
    *           the writeSkewCheck to set
    */
   public void setWriteSkewCheck(final Boolean writeSkewCheck) {
      this.writeSkewCheck = writeSkewCheck;
   }

   /**
    * @param concurrencyLevel
    *           the concurrencyLevel to set
    */
   public void setConcurrencyLevel(final Integer concurrencyLevel) {
      this.concurrencyLevel = concurrencyLevel;
   }

   /**
    * @param replQueueMaxElements
    *           the replQueueMaxElements to set
    */
   public void setReplQueueMaxElements(final Integer replQueueMaxElements) {
      this.replQueueMaxElements = replQueueMaxElements;
   }

   /**
    * @param replQueueInterval
    *           the replQueueInterval to set
    */
   public void setReplQueueInterval(final Long replQueueInterval) {
      this.replQueueInterval = replQueueInterval;
   }

   /**
    * @param replQueueClass
    *           the replQueueClass to set
    */
   public void setReplQueueClass(final String replQueueClass) {
      this.replQueueClass = replQueueClass;
   }

   /**
    * @param exposeJmxStatistics
    *           the exposeJmxStatistics to set
    */
   public void setExposeJmxStatistics(final Boolean exposeJmxStatistics) {
      this.exposeJmxStatistics = exposeJmxStatistics;
   }

   /**
    * @param invocationBatchingEnabled
    *           the invocationBatchingEnabled to set
    */
   public void setInvocationBatchingEnabled(final Boolean invocationBatchingEnabled) {
      this.invocationBatchingEnabled = invocationBatchingEnabled;
   }

   /**
    * @param fetchInMemoryState
    *           the fetchInMemoryState to set
    */
   public void setFetchInMemoryState(final Boolean fetchInMemoryState) {
      this.fetchInMemoryState = fetchInMemoryState;
   }

   /**
    * @param alwaysProvideInMemoryState
    *           the alwaysProvideInMemoryState to set
    */
   public void setAlwaysProvideInMemoryState(final Boolean alwaysProvideInMemoryState) {
      this.alwaysProvideInMemoryState = alwaysProvideInMemoryState;
   }

   /**
    * @param lockAcquisitionTimeout
    *           the lockAcquisitionTimeout to set
    */
   public void setLockAcquisitionTimeout(final Long lockAcquisitionTimeout) {
      this.lockAcquisitionTimeout = lockAcquisitionTimeout;
   }

   /**
    * @param syncReplTimeout
    *           the syncReplTimeout to set
    */
   public void setSyncReplTimeout(final Long syncReplTimeout) {
      this.syncReplTimeout = syncReplTimeout;
   }

   /**
    * @param cacheModeString
    *           the cacheModeString to set
    */
   public void setCacheModeString(final String cacheModeString) {
      this.cacheModeString = cacheModeString;
   }

   /**
    * @param expirationWakeUpInterval
    *           the expiration expirationWakeUpInterval to set
    */
   public void setExpirationWakeUpInterval(final Long expirationWakeUpInterval) {
      this.expirationWakeUpInterval = expirationWakeUpInterval;
   }

   /**
    * @param evictionStrategy
    *           the evictionStrategy to set
    */
   public void setEvictionStrategy(final EvictionStrategy evictionStrategy) {
      this.evictionStrategy = evictionStrategy;
   }

   /**
    * @param evictionStrategyClass
    *           the evictionStrategyClass to set
    */
   public void setEvictionStrategyClass(final String evictionStrategyClass) {
      this.evictionStrategyClass = evictionStrategyClass;
   }

   /**
    * @param evictionThreadPolicy
    *           the evictionThreadPolicy to set
    */
   public void setEvictionThreadPolicy(final EvictionThreadPolicy evictionThreadPolicy) {
      this.evictionThreadPolicy = evictionThreadPolicy;
   }

   /**
    * @param evictionThreadPolicyClass
    *           the evictionThreadPolicyClass to set
    */
   public void setEvictionThreadPolicyClass(final String evictionThreadPolicyClass) {
      this.evictionThreadPolicyClass = evictionThreadPolicyClass;
   }

   /**
    * @param evictionMaxEntries
    *           the evictionMaxEntries to set
    */
   public void setEvictionMaxEntries(final Integer evictionMaxEntries) {
      this.evictionMaxEntries = evictionMaxEntries;
   }

   /**
    * @param expirationLifespan
    *           the expirationLifespan to set
    */
   public void setExpirationLifespan(final Long expirationLifespan) {
      this.expirationLifespan = expirationLifespan;
   }

   /**
    * @param expirationMaxIdle
    *           the expirationMaxIdle to set
    */
   public void setExpirationMaxIdle(final Long expirationMaxIdle) {
      this.expirationMaxIdle = expirationMaxIdle;
   }

   /**
    * @param transactionManagerLookupClass
    *           the transactionManagerLookupClass to set
    */
   public void setTransactionManagerLookupClass(final String transactionManagerLookupClass) {
      this.transactionManagerLookupClass = transactionManagerLookupClass;
   }

   /**
    * @param transactionManagerLookup
    *           the transactionManagerLookup to set
    */
   public void setTransactionManagerLookup(final TransactionManagerLookup transactionManagerLookup) {
      this.transactionManagerLookup = transactionManagerLookup;
   }

   /**
    * @param syncCommitPhase
    *           the syncCommitPhase to set
    */
   public void setSyncCommitPhase(final Boolean syncCommitPhase) {
      this.syncCommitPhase = syncCommitPhase;
   }

   /**
    * @param syncRollbackPhase
    *           the syncRollbackPhase to set
    */
   public void setSyncRollbackPhase(final Boolean syncRollbackPhase) {
      this.syncRollbackPhase = syncRollbackPhase;
   }

   /**
    * @param useEagerLocking
    *           the useEagerLocking to set
    */
   public void setUseEagerLocking(final Boolean useEagerLocking) {
      this.useEagerLocking = useEagerLocking;
   }

   /**
    * @param eagerLockSingleNode
    *           the eagerLockSingleNode to set
    */
   @Deprecated
   public void setEagerLockSingleNode(final Boolean eagerLockSingleNode) {
   }

   /**
    * @param useReplQueue
    *           the useReplQueue to set
    */
   public void setUseReplQueue(final Boolean useReplQueue) {
      this.useReplQueue = useReplQueue;
   }

   /**
    * @param isolationLevel
    *           the isolationLevel to set
    */
   public void setIsolationLevel(final IsolationLevel isolationLevel) {
      this.isolationLevel = isolationLevel;
   }

   /**
    * @param stateRetrievalTimeout
    *           the stateRetrievalTimeout to set
    */
   public void setStateRetrievalTimeout(final Long stateRetrievalTimeout) {
      this.stateRetrievalTimeout = stateRetrievalTimeout;
   }

   /**
    * @param stateRetrievalMaxNonProgressingLogWrites
    *           the stateRetrievalMaxNonProgressingLogWrites to set
    */
   public void setStateRetrievalMaxNonProgressingLogWrites(
            final Integer stateRetrievalMaxNonProgressingLogWrites) {
      this.stateRetrievalMaxNonProgressingLogWrites = stateRetrievalMaxNonProgressingLogWrites;
   }

   /**
    * @param stateRetrievalChunkSize
    *           the stateRetrievalChunkSize to set
    */
   public void setStateRetrievalChunkSize(
         final Integer stateRetrievalChunkSize) {
      this.stateRetrievalChunkSize = stateRetrievalChunkSize;
   }

   /**
    * @param stateRetrievalInitialRetryWaitTime
    *           the stateRetrievalInitialRetryWaitTime to set
    */
   public void setStateRetrievalInitialRetryWaitTime(final Long stateRetrievalInitialRetryWaitTime) {
      this.stateRetrievalInitialRetryWaitTime = stateRetrievalInitialRetryWaitTime;
   }

   /**
    * @param isolationLevelClass
    *           the isolationLevelClass to set
    */
   public void setIsolationLevelClass(final String isolationLevelClass) {
      this.isolationLevelClass = isolationLevelClass;
   }

   /**
    * @param useLazyDeserialization
    *           the useLazyDeserialization to set
    */
   public void setUseLazyDeserialization(final Boolean useLazyDeserialization) {
      this.useLazyDeserialization = useLazyDeserialization;
   }

   /**
    * @param l1CacheEnabled
    *           the l1CacheEnabled to set
    */
   public void setL1CacheEnabled(final Boolean l1CacheEnabled) {
      this.l1CacheEnabled = l1CacheEnabled;
   }

   /**
    * @param l1Lifespan
    *           the l1Lifespan to set
    */
   public void setL1Lifespan(final Long l1Lifespan) {
      this.l1Lifespan = l1Lifespan;
   }

   /**
    * @param l1OnRehash
    *           the l1OnRehash to set
    */
   public void setL1OnRehash(final Boolean l1OnRehash) {
      this.l1OnRehash = l1OnRehash;
   }

   /**
    * @param consistentHashClass
    *           the consistentHashClass to set
    */
   public void setConsistentHashClass(final String consistentHashClass) {
      this.consistentHashClass = consistentHashClass;
   }

   /**
    * @param numOwners
    *           the numOwners to set
    */
   public void setNumOwners(final Integer numOwners) {
      this.numOwners = numOwners;
   }

   /**
    * @param rehashEnabled
    *           the rehashEnabled to set
    */
   public void setRehashEnabled(final Boolean rehashEnabled) {
      this.rehashEnabled = rehashEnabled;
   }

   /**
    * @param useAsyncMarshalling
    *           the useAsyncMarshalling to set
    */
   public void setUseAsyncMarshalling(final Boolean useAsyncMarshalling) {
      this.useAsyncMarshalling = useAsyncMarshalling;
   }

   /**
    * @param indexingEnabled
    *           the indexingEnabled to set
    */
   public void setIndexingEnabled(final Boolean indexingEnabled) {
      this.indexingEnabled = indexingEnabled;
   }

   /**
    * @param indexLocalOnly
    *           the indexLocalOnly to set
    */
   public void setIndexLocalOnly(final Boolean indexLocalOnly) {
      this.indexLocalOnly = indexLocalOnly;
   }

   /**
    * @param customInterceptors
    *           the customInterceptors to set
    */
   public void setCustomInterceptors(final List<? extends CommandInterceptor> customInterceptors) {
      this.customInterceptors = customInterceptors;
   }

   public void applyOverridesTo(final ConfigurationBuilder configurationToOverride) {
      this.logger.debug("Applying configuration overrides to Configuration [" + configurationToOverride + "] ...");

      if (this.eagerDeadlockSpinDuration != null) {
         this.logger.debug("Overriding property [eagerDeadlockSpinDuration] with value [" + this.eagerDeadlockSpinDuration + "]");
         configurationToOverride.deadlockDetection().spinDuration(this.eagerDeadlockSpinDuration);
      }
      if (this.useEagerDeadlockDetection != null) {
         this.logger.debug("Overriding property [useEagerDeadlockDetection] with value [" + this.useEagerDeadlockDetection + "]");
         configurationToOverride.deadlockDetection().enabled(this.useEagerDeadlockDetection);
      }
      if (this.useLockStriping != null) {
         this.logger.debug("Overriding property [useLockStriping] with value [" + this.useLockStriping + "]");
         configurationToOverride.locking().useLockStriping(this.useLockStriping);
      }
      if (this.unsafeUnreliableReturnValues != null) {
         this.logger.debug("Overriding property [unsafeUnreliableReturnValues] with value [" + this.unsafeUnreliableReturnValues + "]");
         configurationToOverride.unsafe().unreliableReturnValues(this.unsafeUnreliableReturnValues);
      }
      if (this.rehashRpcTimeout != null) {
         this.logger.debug("Overriding property [rehashRpcTimeout] with value [" + this.rehashRpcTimeout + "]");
         configurationToOverride.clustering().stateTransfer().timeout(this.rehashRpcTimeout);
      }
      if (this.writeSkewCheck != null) {
         this.logger.debug("Overriding property [writeSkewCheck] with value [" + this.writeSkewCheck + "]");
         configurationToOverride.locking().writeSkewCheck(this.writeSkewCheck);
      }
      if (this.concurrencyLevel != null) {
         this.logger.debug("Overriding property [concurrencyLevel] with value [" + this.concurrencyLevel + "]");
         configurationToOverride.locking().concurrencyLevel(this.concurrencyLevel);
      }
      if (this.replQueueMaxElements != null) {
         this.logger.debug("Overriding property [replQueueMaxElements] with value [" + this.replQueueMaxElements + "]");
         configurationToOverride.clustering().async().replQueueMaxElements(this.replQueueMaxElements);
      }
      if (this.replQueueInterval != null) {
         this.logger.debug("Overriding property [replQueueInterval] with value [" + this.replQueueInterval + "]");
         configurationToOverride.clustering().async().replQueueInterval(this.replQueueInterval);
      }
      if (this.replQueueClass != null) {
         this.logger.debug("Overriding property [replQueueClass] with value [" + this.replQueueClass + "]");
         configurationToOverride.clustering().async().replQueue(Util.<ReplicationQueue>getInstance(
               this.replQueueClass, Thread.currentThread().getContextClassLoader()));
      }
      if (this.exposeJmxStatistics != null) {
         this.logger.debug("Overriding property [exposeJmxStatistics] with value [" + this.exposeJmxStatistics + "]");
         configurationToOverride.jmxStatistics().enabled(this.exposeJmxStatistics);
      }
      if (this.invocationBatchingEnabled != null) {
         this.logger.debug("Overriding property [invocationBatchingEnabled] with value [" + this.invocationBatchingEnabled + "]");
         configurationToOverride.invocationBatching().enable(this.invocationBatchingEnabled);
      }
      if (this.fetchInMemoryState != null) {
         this.logger.debug("Overriding property [fetchInMemoryState] with value [" + this.fetchInMemoryState + "]");
         configurationToOverride.clustering().stateTransfer().fetchInMemoryState(this.fetchInMemoryState);
      }
      if (this.alwaysProvideInMemoryState != null) {
         this.logger.debug("Overriding property [alwaysProvideInMemoryState] with value [" + this.alwaysProvideInMemoryState + "]");
         configurationToOverride.clustering().stateTransfer().fetchInMemoryState(this.alwaysProvideInMemoryState);
      }
      if (this.lockAcquisitionTimeout != null) {
         this.logger.debug("Overriding property [lockAcquisitionTimeout] with value [" + this.lockAcquisitionTimeout + "]");
         configurationToOverride.locking().lockAcquisitionTimeout(this.lockAcquisitionTimeout);
      }
      if (this.syncReplTimeout != null) {
         this.logger.debug("Overriding property [syncReplTimeout] with value [" + this.syncReplTimeout + "]");
         configurationToOverride.clustering().sync().replTimeout(this.syncReplTimeout);
      }
      if (this.cacheModeString != null) {
         this.logger.debug("Overriding property [cacheModeString] with value [" + this.cacheModeString + "]");
         configurationToOverride.clustering().cacheMode(CacheMode.valueOf(this.cacheModeString));
      }
      if (this.expirationWakeUpInterval != null) {
         this.logger.debug("Overriding property [expirationWakeUpInterval] with value [" + this.expirationWakeUpInterval + "]");
         configurationToOverride.expiration().wakeUpInterval(expirationWakeUpInterval);
      }
      if (this.evictionStrategy != null) {
         this.logger.debug("Overriding property [evictionStrategy] with value [" + this.evictionStrategy + "]");
         configurationToOverride.eviction().strategy(this.evictionStrategy);
      }
      if (this.evictionStrategyClass != null) {
         this.logger.debug("Overriding property [evictionStrategyClass] with value [" + this.evictionStrategyClass + "]");
         //FIXME
         //configurationToOverride.setEvictionStrategy(this.evictionStrategyClass);
      }
      if (this.evictionThreadPolicy != null) {
         this.logger.debug("Overriding property [evictionThreadPolicy] with value [" + this.evictionThreadPolicy + "]");
         configurationToOverride.eviction().threadPolicy(this.evictionThreadPolicy);
      }
      if (this.evictionThreadPolicyClass != null) {
         this.logger.debug("Overriding property [evictionThreadPolicyClass] with value [" + this.evictionThreadPolicyClass + "]");
         //FIXME
         //configurationToOverride.eviction()//setEvictionThreadPolicy(this.evictionThreadPolicyClass);
      }
      if (this.evictionMaxEntries != null) {
         this.logger.debug("Overriding property [evictionMaxEntries] with value [" + this.evictionMaxEntries + "]");
         configurationToOverride.eviction().maxEntries(this.evictionMaxEntries);
      }
      if (this.expirationLifespan != null) {
         this.logger.debug("Overriding property [expirationLifespan] with value [" + this.expirationLifespan + "]");
         configurationToOverride.expiration().lifespan(this.expirationLifespan);
      }
      if (this.expirationMaxIdle != null) {
         this.logger.debug("Overriding property [expirationMaxIdle] with value [" + this.expirationMaxIdle + "]");
         configurationToOverride.expiration().maxIdle(this.expirationMaxIdle);
      }
      if (this.transactionManagerLookupClass != null) {
         this.logger.debug("Overriding property [transactionManagerLookupClass] with value [" + this.transactionManagerLookupClass + "]");
         configurationToOverride.transaction().transactionManagerLookup(Util.<TransactionManagerLookup>getInstance(
               this.transactionManagerLookupClass, Thread.currentThread().getContextClassLoader()));
      }
      if (this.transactionManagerLookup != null) {
         this.logger.debug("Overriding property [transactionManagerLookup] with value [" + this.transactionManagerLookup + "]");
         configurationToOverride.transaction().transactionManagerLookup(this.transactionManagerLookup);
      }
      if (this.syncCommitPhase != null) {
         this.logger.debug("Overriding property [syncCommitPhase] with value [" + this.syncCommitPhase + "]");
         configurationToOverride.transaction().syncCommitPhase(this.syncCommitPhase);
      }
      if (this.syncRollbackPhase != null) {
         this.logger.debug("Overriding property [syncRollbackPhase] with value [" + this.syncRollbackPhase + "]");
         configurationToOverride.transaction().syncRollbackPhase(this.syncRollbackPhase);
      }
      if (this.useEagerLocking != null) {
         this.logger.debug("Overriding property [useEagerLocking] with value [" + this.useEagerLocking + "]");
         configurationToOverride.transaction().lockingMode(useEagerLocking ? LockingMode.PESSIMISTIC : LockingMode.OPTIMISTIC);
      }
      if (this.useReplQueue != null) {
         this.logger.debug("Overriding property [useReplQueue] with value [" + this.useReplQueue + "]");
         configurationToOverride.clustering().async().useReplQueue(this.useReplQueue);
      }
      if (this.isolationLevel != null) {
         this.logger.debug("Overriding property [isolationLevel] with value [" + this.isolationLevel + "]");
         configurationToOverride.locking().isolationLevel(this.isolationLevel);
      }
      if (this.stateRetrievalTimeout != null) {
         this.logger.debug("Overriding property [stateRetrievalTimeout] with value [" + this.stateRetrievalTimeout + "]");
         configurationToOverride.clustering().stateTransfer().timeout(this.stateRetrievalTimeout);
      }
      if (this.stateRetrievalMaxNonProgressingLogWrites != null) {
         this.logger.debug("Overriding property [stateRetrievalMaxNonProgressingLogWrites] with value [" + this.stateRetrievalMaxNonProgressingLogWrites + "]");
//         configurationToOverride
//               .setStateRetrievalMaxNonProgressingLogWrites(this.stateRetrievalMaxNonProgressingLogWrites);
      }
      if (this.stateRetrievalChunkSize != null) {
         this.logger.debug("Overriding property [stateRetrievalChunkSize] with value [" + this.stateRetrievalChunkSize + "]");
         configurationToOverride.clustering().stateTransfer().chunkSize(this.stateRetrievalChunkSize);
      }
      if (this.stateRetrievalInitialRetryWaitTime != null) {
         this.logger.debug("Overriding property [stateRetrievalInitialRetryWaitTime] with value [" + this.stateRetrievalInitialRetryWaitTime + "]");
         //FIXME
      }
      if (this.isolationLevelClass != null) {
         this.logger.debug("Overriding property [isolationLevelClass] with value [" + this.isolationLevelClass + "]");
//         configurationToOverride.setIsolationLevel(this.isolationLevelClass);
      }
      if (this.useLazyDeserialization != null) {
         this.logger.debug("Overriding property [useLazyDeserialization] with value [" + this.useLazyDeserialization + "]");
         //FIXME
      }
      if (this.l1CacheEnabled != null) {
         this.logger.debug("Overriding property [l1CacheEnabled] with value [" + this.l1CacheEnabled + "]");
         configurationToOverride.clustering().l1().enabled(this.l1CacheEnabled);
      }
      if (this.l1Lifespan != null) {
         this.logger.debug("Overriding property [l1Lifespan] with value [" + this.l1Lifespan + "]");
         configurationToOverride.clustering().l1().lifespan(this.l1Lifespan);
      }
      if (this.l1OnRehash != null) {
         this.logger.debug("Overriding property [l1OnRehash] with value [" + this.l1OnRehash + "]");
         configurationToOverride.clustering().l1().l1OnRehash(this.l1OnRehash);
      }
      if (this.consistentHashClass != null) {
         this.logger.debug("Overriding property [consistentHashClass] with value [" + this.consistentHashClass + "]");
         configurationToOverride.clustering().hash().consistentHash(Util.<ConsistentHash>getInstance(
               this.consistentHashClass, Thread.currentThread().getContextClassLoader()));
      }
      if (this.numOwners != null) {
         this.logger.debug("Overriding property [numOwners] with value [" + this.numOwners + "]");
         configurationToOverride.clustering().hash().numOwners(this.numOwners);
      }
      if (this.rehashEnabled != null) {
         this.logger.debug("Overriding property [rehashEnabled] with value [" + this.rehashEnabled + "]");
         configurationToOverride.clustering().stateTransfer().fetchInMemoryState(this.rehashEnabled);
      }
      if (this.useAsyncMarshalling != null) {
         this.logger.debug("Overriding property [useAsyncMarshalling] with value [" + this.useAsyncMarshalling + "]");
         configurationToOverride.clustering().async().asyncMarshalling(this.useAsyncMarshalling);
      }
      if (this.indexingEnabled != null) {
         this.logger.debug("Overriding property [indexingEnabled] with value [" + this.indexingEnabled + "]");
         configurationToOverride.indexing().enabled(this.indexingEnabled);
      }
      if (this.indexLocalOnly != null) {
         this.logger.debug("Overriding property [indexLocalOnly] with value [" + this.indexLocalOnly + "]");
         configurationToOverride.indexing().indexLocalOnly(this.indexLocalOnly);
      }
      if (this.customInterceptors != null) {
         this.logger.debug("Looping over [customInterceptors] to add each one to local collection.");
         for (CommandInterceptor interceptor : customInterceptors) {
            this.logger.debug("Adding custom interceptor  [" + interceptor.getClass().getName() + "]");
            configurationToOverride.customInterceptors().addInterceptor().interceptor(interceptor);
         }

      }

      this.logger.debug("Finished applying configuration overrides to Configuration [" + configurationToOverride + "]");
   }
}
