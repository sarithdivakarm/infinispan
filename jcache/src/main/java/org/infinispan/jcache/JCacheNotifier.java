package org.infinispan.jcache;

import org.infinispan.jcache.logging.Log;
import org.infinispan.util.logging.LogFactory;

import javax.cache.Cache;
import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListener;
import javax.cache.event.CacheEntryListenerRegistration;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JCache notifications dispatcher.
 *
 * TODO: Deal with asynchronous listeners...
 *
 * @author Galder Zamarreño
 * @since 5.3
 */
public class JCacheNotifier<K, V> {

   private static final Log log =
         LogFactory.getLog(JCacheNotifier.class, Log.class);

   private static final boolean isTrace = log.isTraceEnabled();

   // Traversals are a not more common than mutations when it comes to
   // keeping track of registered listeners, so use copy-on-write lists.

   private final List<CacheEntryListenerRegistration<? super K, ? super V>> createdListeners =
         new CopyOnWriteArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();

   private final List<CacheEntryListenerRegistration<? super K, ? super V>> updatedListeners =
         new CopyOnWriteArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();

   private final List<CacheEntryListenerRegistration<? super K, ? super V>> removedListeners =
         new CopyOnWriteArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();

   private final List<CacheEntryListenerRegistration<? super K, ? super V>> expiredListeners =
         new CopyOnWriteArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();

   public void addListener(CacheEntryListenerRegistration<? super K, ? super V> reg) {
      addListener(reg, false);
   }

   public boolean addListenerIfAbsent(CacheEntryListenerRegistration<? super K, ? super V> reg) {
      return addListener(reg, true);
   }

   public boolean removeListener(CacheEntryListener<?, ?> listener) {
      boolean removed = false;
      if (listener instanceof CacheEntryCreatedListener)
         removed = removeListener(listener, createdListeners);

      if (listener instanceof CacheEntryUpdatedListener)
         removed = removeListener(listener, updatedListeners);

      if (listener instanceof CacheEntryRemovedListener)
         removed = removeListener(listener, removedListeners);

      if (listener instanceof CacheEntryExpiredListener)
         removed = removeListener(listener, expiredListeners);

      return removed;
   }

   @SuppressWarnings("unchecked")
   public void notifyEntryCreated(Cache<K, V> cache, K key, V value) {
      if (!createdListeners.isEmpty()) {
         List<CacheEntryEvent<? extends K, ? extends V>> events =
               createEvent(cache, key, value);
         for (CacheEntryListenerRegistration<? super K, ? super V> reg : createdListeners) {
            ((CacheEntryCreatedListener<K, V>) reg.getCacheEntryListener())
                  .onCreated(getEntryIterable(events, reg));
         }
      }
   }

   @SuppressWarnings("unchecked")
   public void notifyEntryUpdated(Cache<K, V> cache, K key, V value) {
      if (!updatedListeners.isEmpty()) {
         List<CacheEntryEvent<? extends K, ? extends V>> events =
               createEvent(cache, key, value);
         for (CacheEntryListenerRegistration<? super K, ? super V> reg : updatedListeners) {
            CacheEntryUpdatedListener<K, V> listener =
                  (CacheEntryUpdatedListener<K, V>) reg.getCacheEntryListener();
            listener.onUpdated(getEntryIterable(events, reg));
         }
      }
   }

   @SuppressWarnings("unchecked")
   public void notifyEntryRemoved(Cache<K, V> cache, K key, V value) {
      if (!removedListeners.isEmpty()) {
         List<CacheEntryEvent<? extends K, ? extends V>> events =
               createEvent(cache, key, value);
         for (CacheEntryListenerRegistration<? super K, ? super V> reg : removedListeners) {
            ((CacheEntryRemovedListener<K, V>) reg.getCacheEntryListener())
                  .onRemoved(getEntryIterable(events, reg));
         }
      }
   }

   @SuppressWarnings("unchecked")
   public void notifyEntryExpired(Cache<K, V> cache, K key, V value) {
      if (!expiredListeners.isEmpty()) {
         List<CacheEntryEvent<? extends K, ? extends V>> events =
               createEvent(cache, key, value);
         for (CacheEntryListenerRegistration<? super K, ? super V> reg : expiredListeners) {
            ((CacheEntryExpiredListener<K, V>) reg.getCacheEntryListener())
                  .onExpired(getEntryIterable(events, reg));
         }
      }
   }

   private Iterable<CacheEntryEvent<? extends K, ? extends V>> getEntryIterable(
         List<CacheEntryEvent<? extends K, ? extends V>> events,
         CacheEntryListenerRegistration<? super K, ? super V> reg) {
      CacheEntryEventFilter<? super K, ? super V> filter = reg.getCacheEntryFilter();
      return filter == null  ? events
            : new JCacheEventFilteringIterable<K, V>(events, filter);
   }

   private boolean addListener(CacheEntryListenerRegistration<? super K, ? super V> reg,
         boolean addIfAbsent) {
      boolean added = false;
      CacheEntryListener<? super K, ? super V> listener = reg.getCacheEntryListener();
      if (listener instanceof CacheEntryCreatedListener) {
         added = addListener(addIfAbsent, reg, listener, createdListeners);
      }

      if (listener instanceof CacheEntryUpdatedListener) {
         added = addListener(addIfAbsent, reg, listener, updatedListeners);
      }

      if (listener instanceof CacheEntryRemovedListener) {
         added = addListener(addIfAbsent, reg, listener, removedListeners);
      }

      if (listener instanceof CacheEntryExpiredListener) {
         added = addListener(addIfAbsent, reg, listener, expiredListeners);
      }

      return added;
   }

   private boolean addListener(boolean addIfAbsent,
         CacheEntryListenerRegistration<? super K, ? super V> reg,
         CacheEntryListener<? super K, ? super V> listener,
         List<CacheEntryListenerRegistration<? super K, ? super V>> listeners) {
      return !containsListener(addIfAbsent, listener, listeners)
            && listeners.add(reg);

   }

   private boolean containsListener(boolean addIfAbsent,
         CacheEntryListener<? super K, ? super V> listener,
         List<CacheEntryListenerRegistration<? super K, ? super V>> listeners) {
      // If add only if no listener present, check the listeners collection
      if (addIfAbsent) {
         for (CacheEntryListenerRegistration<? super K, ? super V> reg : listeners) {
            if (reg.getCacheEntryListener().equals(listener))
               return true;
         }
      }

      return false;
   }

   private boolean removeListener(CacheEntryListener<?, ?> listener,
         List<CacheEntryListenerRegistration<? super K, ? super V>> listeners) {
      for (CacheEntryListenerRegistration<? super K, ? super V> reg : listeners) {
         if (reg.getCacheEntryListener().equals(listener))
            return listeners.remove(reg);
      }

      return false;
   }

   private List<CacheEntryEvent<? extends K, ? extends V>> createEvent(
         Cache<K, V> cache, K key, V value) {
      List<CacheEntryEvent<? extends K, ? extends V>> events =
            Collections.<CacheEntryEvent<? extends K, ? extends V>>singletonList(
                  new RICacheEntryEvent<K, V>(cache, key, value));
      if (isTrace) log.tracef("Received event: %s", events);
      return events;
   }

}
