//package com.arupkhanra.advanceSpringbootFeaturesAZ.cache;
//
//import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.ConfigJournalAppEntity;
//import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.ConfigJournalAppEntityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class AppCache {
//
//    public enum keys{
//        weather_api;
//    }
//
//    @Autowired
//    public ConfigJournalAppEntityRepository configJournalAppEntityRepository;
//
//    public Map<String,String> appCache;
//
//    @PostConstruct
//    public void init() {
//        appCache = new HashMap<>();
//        try {
//            List<ConfigJournalAppEntity> all = configJournalAppEntityRepository.findAll();
//            for (ConfigJournalAppEntity configJournalAppEntity : all) {
//                appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to initialize app cache", e);
//        }
//    }
//}
