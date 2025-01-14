package com.arupkhanra.advanceSpringbootFeaturesAZ.repository;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long> {


}
