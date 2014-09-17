package com.kodcu;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.integration.CacheWriter;
import javax.cache.spi.CachingProvider;
import java.util.HashMap;

/**
 * Created by usta on 15.09.2014.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        MutableConfiguration config = new MutableConfiguration();
        config.setTypes(String.class, Integer.class);

        config.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        Cache<String, Integer> kodcu = cacheManager.createCache("kodcu", config);


        kodcu.put("İstanbul", 34);
        kodcu.put("Antalya", 07);
        kodcu.putIfAbsent("İstanbul", 99);
        kodcu.putAll(new HashMap<String, Integer>() {
            {
                put("Ankara", 07);
                put("Kayseri", 38);
            }
        });


        kodcu.replace("Ankara", 06);

        Integer kayseri = kodcu.get("Kayseri");

        boolean silindiMi = kodcu.remove("Ankara");

        Integer antalya = kodcu.getAndRemove("Antalya");

        System.out.println("*** Cache print started ***");
        for (Cache.Entry<String, Integer> entry : kodcu) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.format("Key: %s - Value: %d %n", key, value);
        }

        System.out.println("*** Cache print end ***");

        System.out.println("Wait one minute to see expiry out");
        Thread.sleep(1000 * 60); // Dikkat

        System.out.println("*** Cache print started (after one minute) ***");
        for (Cache.Entry<String, Integer> entry : kodcu) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.format("Key: %s - Value: %d %n", key, value);
        }
        System.out.println("*** Cache print end ***");



    }
}
