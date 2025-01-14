package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {

    List<JournalEntry> getAllJournal();
    JournalEntry createJournalEntry(JournalEntry journalEntry, String user);
    Optional<JournalEntry> findById(Long id);
    List<JournalEntry> findByUserName(String userName);

    boolean deleteById(Long myId, String userName);

    void saveEntry(JournalEntry journalEntry);
}
