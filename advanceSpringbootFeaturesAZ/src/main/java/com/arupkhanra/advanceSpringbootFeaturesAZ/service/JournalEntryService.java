package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;

import java.util.List;

public interface JournalEntryService {

    JournalEntry createJournalEntry(JournalEntry journalEntry, String user);

    //void createJournalEntry(JournalEntry journalEntry);

    List<JournalEntry> getAllJournal();
    JournalEntry getJournalId(int journalId);

    int removeId(int deleteId, String userName);

    JournalEntry updateJournalEntry(String userName,int id, JournalEntry updatedEntry);

}
