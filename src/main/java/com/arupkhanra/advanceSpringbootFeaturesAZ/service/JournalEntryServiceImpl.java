package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.exceptions.UserNotFoundException;
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
    public List<JournalEntry> getAllJournal() {
        log.info("Fetching all journal entries from the database.");
        try {
            List<JournalEntry> entries = journalEntryRepository.findAll();
            log.info("Retrieved {} journal entries from the database.", entries.size());
            return entries;
        } catch (Exception e) {
            log.error("Error fetching journal entries: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching journal entries", e);
        }
    }

    @Override
    @Transactional
    public JournalEntry createJournalEntry(JournalEntry journalEntry, String userName) {
        log.info("Creating journal entry for user: {}", userName);
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                log.warn("User not found with username: {}", userName);
                throw new UserNotFoundException("User not found with username: " + userName);
            }

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

            log.info("Successfully saved journal entry with ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("Error creating journal entry for user {}: {}", userName, e.getMessage(), e);
            throw new RuntimeException("Error creating journal entry", e);
        }
    }

    @Override
    public Optional<JournalEntry> findById(Long id) {
        log.info("Finding journal entry with ID: {}", id);
        try {
            Optional<JournalEntry> entry = journalEntryRepository.findById(id);
            if (entry.isPresent()) {
                log.info("Journal entry found with ID: {}", id);
            } else {
                log.warn("Journal entry not found with ID: {}", id);
            }
            return entry;
        } catch (Exception e) {
            log.error("Error finding journal entry with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error finding journal entry", e);
        }
    }

    @Override
    public List<JournalEntry> findByUserName(String userName) {
        log.info("Finding journal entries for user: {}", userName);
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                log.warn("User not found with username: {}", userName);
                throw new UserNotFoundException("User not found with username: " + userName);
            }
            List<JournalEntry> entries = user.getJournalEntries();
            log.info("Retrieved {} journal entries for user: {}", entries.size(), userName);
            return entries;
        } catch (Exception e) {
            log.error("Error fetching journal entries for user {}: {}", userName, e.getMessage(), e);
            throw new RuntimeException("Error fetching journal entries", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id, String userName) {
        log.info("Deleting journal entry with ID {} for user: {}", id, userName);
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                log.warn("User not found with username: {}", userName);
                throw new UserNotFoundException("User not found with username: " + userName);
            }

            boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                log.info("Successfully deleted journal entry with ID: {}", id);
            } else {
                log.warn("Journal entry with ID {} not found for user: {}", id, userName);
            }
            return removed;
        } catch (Exception e) {
            log.error("Error deleting journal entry with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting journal entry", e);
        }
    }

    @Override
    public void saveEntry(JournalEntry journalEntry) {
        log.info("Saving journal entry: {}", journalEntry);
        try {
            journalEntryRepository.save(journalEntry);
            log.info("Successfully saved journal entry with ID: {}", journalEntry.getId());
        } catch (Exception e) {
            log.error("Error saving journal entry: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving journal entry", e);
        }
    }
}
