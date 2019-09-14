package ru.shopitem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shopitem.repository.ConfigSetting;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.*;

import static ru.shopitem.ShopItemsTestData.*;
import static ru.shopitem.repository.ConfigSetting.PERSISTENCE_UNIT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractTestCase {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setUpEntityManagerFactor() throws Exception {
        PERSISTENCE_UNIT = "test-ogm";
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        entityManager = entityManagerFactory.createEntityManager();
        TransactionManager transactionManager =
                com.arjuna.ats.jta.TransactionManager.transactionManager();

        if (transactionManager.getStatus() == 6) {
            transactionManager.begin();
        }

        entityManager.createNativeQuery(TestQueries.DROP_ALL).executeUpdate();
        entityManager.persist(PHONE);
        entityManager.persist(TELEVISION);

        entityManager.close();

        transactionManager.commit();
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
        PHONE.setId(null);
        TELEVISION.setId(null);
    }
}
