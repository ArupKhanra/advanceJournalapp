//package com.arupkhanra.advanceSpringbootFeaturesAZ.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "config_journal_app")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class ConfigJournalAppEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "key", nullable = false, unique = true)
//    private String key;
//
//    @Column(name = "value", nullable = false)
//    private String value;
//
//    @Override
//    public String toString() {
//        return "ConfigJournalAppEntity{id=" + id + ", key='" + key + "', value='" + value + "'}";
//    }
//}