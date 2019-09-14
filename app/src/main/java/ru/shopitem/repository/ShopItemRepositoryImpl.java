package ru.shopitem.repository;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import ru.shopitem.model.NativeQueries;
import ru.shopitem.model.ShopItem;
import ru.shopitem.util.TransactionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static ru.shopitem.repository.ConfigSetting.PERSISTENCE_UNIT;

@Repository
public class ShopItemRepositoryImpl implements ShopItemRepository{

    private final EntityManagerFactory managerFactory;

    public ShopItemRepositoryImpl() {
        managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @Override
    public ShopItem save(ShopItem item) {
        try {
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            if (item.isNew()) {
                entityManager.persist(item);
            } else {
                entityManager.merge(item);
            }

            entityManager.getTransaction().commit();
            entityManager.close();
            return item;
        } catch (Exception e) {
            throw new TransactionException("Cann't save entity, transaction exception");
        }
    }

    @Override
    public ShopItem get(String id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        ShopItem result = entityManager.find(ShopItem.class, id);
        entityManager.close();

        return result;

    }

    @Override
    public List<ShopItem> getByName(String name) {
        EntityManager entityManager = managerFactory.createEntityManager();
        List<ShopItem> result = entityManager.createNamedQuery(ShopItem.GET_BY_NAME, ShopItem.class)
                .setParameter("name", name).getResultList();
        entityManager.close();

        return result;
    }

    @Override
    public List<ShopItem> getByOption(String key, String value) {
        EntityManager entityManager = managerFactory.createEntityManager();
        List<ShopItem> result = entityManager
                .createNativeQuery(NativeQueries.queryByOption(key, value), ShopItem.class)
                .getResultList();
        entityManager.close();
        return result;
    }
}
