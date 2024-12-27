package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.JournalEntryRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public JournalEntry createJournalEntry(JournalEntry journalEntry, String userName) {
        log.info("Attempting to save journal entry with ID: {}", journalEntry.getId());

        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + userName);
        }

        journalEntry.setDate(LocalDateTime.now());
        journalEntry.setUser(user);

        JournalEntry saved = journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(saved);

        userService.createUser(user);

        log.info("Successfully saved journal entry with ID: {}", saved.getId());
        return saved;
    }
//    @Override
//    public void createJournalEntry(JournalEntry journalEntry) {
//
//        journalEntryRepository.save(journalEntry);
//
//    }

    @Override
    public List<JournalEntry> getAllJournal() {
        log.info("Fetching all journal entries from the database.");
        List<JournalEntry> entries = journalEntryRepository.findAll();
        log.info("Retrieved {} journal entries from the database.", entries.size());
        return entries;
    }

    @Override
    public JournalEntry getJournalId(int journalId) {
        log.info("Fetching journal entry with ID: {}", journalId);
        Optional<JournalEntry> entry = journalEntryRepository.findById(journalId);
        return entry.orElseThrow(() -> {
            String errorMessage = String.format("Journal entry with ID %d not found", journalId);
            log.error(errorMessage);
            return new RuntimeException(errorMessage);
        });
    }


    @Override
    public int removeId(int deleteId, String userName) {
        User user = userRepository.findByUserName(userName);


        if (user != null && journalEntryRepository.existsById(deleteId)) {
            // Remove the journal entry from the user's list
            user.getJournalEntries().removeIf(r -> r.getId()== deleteId);

            // Update the user
            userService.updateUser(user);

            // Delete the journal entry
            journalEntryRepository.deleteById(deleteId);

            log.info("Successfully deleted journal entry with ID: {}", deleteId);
            return deleteId;
        } else {
            log.warn("Attempted to delete journal entry with ID: {} but it was not found.", deleteId);
            return -1;
        }
    }

    @Override
    public JournalEntry updateJournalEntry(String userName, int id, JournalEntry updatedEntry) {
        log.info("Updating journal entry with ID: {}", id);

        JournalEntry oldEntry = journalEntryRepository.findById(id).orElse(null);
        if (oldEntry != null) {
            updatedEntry.setContent(updatedEntry.getContent() != null &&
                    !updatedEntry.getContent().isEmpty() ?
                    updatedEntry.getContent() : oldEntry.getContent());

            updatedEntry.setTitle(updatedEntry.getTitle() != null &&
                    !updatedEntry.getTitle().isEmpty() ?
                    updatedEntry.getTitle() : oldEntry.getTitle());

            updatedEntry.setDate(LocalDateTime.now());
            updatedEntry.setUser(oldEntry.getUser());
        }
        JournalEntry newJournalEntrySave = journalEntryRepository.save(updatedEntry);
        log.info("Successfully updated journal entry with ID: {}", id);
        return newJournalEntrySave;
    }

}

