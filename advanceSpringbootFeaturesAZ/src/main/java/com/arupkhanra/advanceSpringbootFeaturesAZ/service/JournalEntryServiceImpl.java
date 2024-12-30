package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.exceptions.UserNotFoundException;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.JournalEntryRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<JournalEntry> entries = journalEntryRepository.findAll();
        log.info("Retrieved {} journal entries from the database.", entries.size());
        return entries;
    }

    @Override
    @Transactional
    public JournalEntry createJournalEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                throw new RuntimeException("User not found with username: " + userName);
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            log.info("Successfully saved journal entry with ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            throw new RuntimeException("an error occurred while the entry ", e);
        }
    }

    @Override
    public Optional<JournalEntry> findById(Long id) {
        return journalEntryRepository.findById(id);
    }

    @Override
    public List<JournalEntry> findByUserName(String userName) {

        return null;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id, String userName) {

        boolean removed = false;
        try {
            User user = userRepository.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("an error occurred while deleting th entry  " + e);
        }
        return removed;
    }

    @Override
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }


}




