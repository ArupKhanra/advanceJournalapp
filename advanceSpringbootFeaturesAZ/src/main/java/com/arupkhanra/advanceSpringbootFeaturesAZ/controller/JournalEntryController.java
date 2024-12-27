package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.JournalEntryService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal/v1/")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    //localhost:8081/journal/v1/create
    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        JournalEntry entry = journalEntryService.createJournalEntry(journalEntry,userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }
    /*
        localhost:8081/journal/v1/{username}
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username) {
        // Fetch the user by username
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if user not found
        }
        List<JournalEntry> all = user.getJournalEntries();

        if (all == null || all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for empty list
        }
        return new ResponseEntity<>(all, HttpStatus.OK); // 200 OK with the list
    }

    //http://localhost:8081/journal/v1/getId?journalId=1
    @GetMapping("/getId")
    public JournalEntry getJournalId(@RequestParam("journalId") int journalId){
        return journalEntryService.getJournalId(journalId);
    }

    //localhost:8081/journal/v1deleteId/arup_khanra/1

    @DeleteMapping("/deleteId/{userName}/{deletedId}")
    public ResponseEntity<String> deleteByJournalEntryId(@PathVariable String userName,
                                                         @PathVariable int deletedId) {
        int removedId = journalEntryService.removeId(deletedId, userName);
        if (removedId != -1) {
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Resource with ID %d deleted successfully.", removedId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Attempted to delete journal entry with ID %d but it was not found.", deletedId));
    }

    //http://localhost:8081/journal/v1/update/3
    //http://localhost:8081/journal/v1/update/

    @PutMapping("/update/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(
            @PathVariable("userName") String userName,
            @PathVariable("myId") int myId,
            @RequestBody JournalEntry updatedEntry) {

        try {
            JournalEntry updated = journalEntryService.updateJournalEntry(userName, myId, updatedEntry);
            return ResponseEntity.ok(updated); // 200 OK with updated resource
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }
}
