package dev.olivejua.pointsystem;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
