package com.arupkhanra.advanceSpringbootFeaturesAZ.scheduler;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.EmailService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.SentimenAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SentimenAnalysisService sentimenAnalysisService;

    @Transactional
   @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filterEntries = journalEntries.stream()
                    .filter(x ->x.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getContent())
                    .collect(Collectors.toList());
            String entry = String.join("", filterEntries);

           String sentiment = sentimenAnalysisService.getSentiment(entry);
           emailService.sendMail(user.getEmail(),"Sentiment for last 7days",sentiment);
        }
    }
}
