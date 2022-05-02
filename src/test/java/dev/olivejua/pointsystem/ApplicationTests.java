package dev.olivejua.pointsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
public class ApplicationTests {

    @Autowired
    protected EntityManager entityManager;

    protected void clearContext() {
        entityManager.clear();
        entityManager.flush();
    }
}
