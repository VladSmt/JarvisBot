package com.tgbot.jarvis.jarvisbot.bot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad
 */

@Repository
public interface TestRepository extends CrudRepository<TestData, Long> {
}
