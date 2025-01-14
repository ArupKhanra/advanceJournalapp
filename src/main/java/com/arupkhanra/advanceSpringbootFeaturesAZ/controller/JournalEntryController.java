package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.JournalEntryService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal/v1/")
@Slf4j
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(Authentication authentication) {
        log.info("Fetching all journal entries for user: {}", authentication.getName());
        User user = userRepository.findByUserName(authentication.getName());
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            log.info("Found {} journal entries for user: {}", all.size(), authentication.getName());
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        log.warn("No journal entries found for user: {}", authentication.getName());
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        log.info("Received request to create a new journal entry.");
        try {
            String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Authenticated user: {}", authenticatedUser);
            JournalEntry entry = journalEntryService.createJournalEntry(journalEntry, authenticatedUser);
            log.info("Successfully created journal entry with ID: {}", entry.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        } catch (Exception e) {
            log.error("Error occurred while creating journal entry: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable Long myId) {
        log.info("Fetching journal entry with ID: {}", myId);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                log.info("Found journal entry with ID: {}", myId);
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        log.warn("Journal entry with ID: {} not found for user: {}", myId, userName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable("myId") Long myId) {
        log.info("Received request to delete journal entry with ID: {}", myId);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if (removed) {
            log.info("Successfully deleted journal entry with ID: {}", myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.warn("Journal entry with ID: {} not found for deletion.", myId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable("myId") Long myId,
                                               @RequestBody JournalEntry newEntry) {
        log.info("Received request to update journal entry with ID: {}", myId);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                log.info("Successfully updated journal entry with ID: {}", myId);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        log.warn("Journal entry with ID: {} not found for update.", myId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
