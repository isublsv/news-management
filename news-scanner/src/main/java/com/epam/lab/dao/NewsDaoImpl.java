package com.epam.lab.dao;

import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.model.News;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public class NewsDaoImpl implements NewsDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public NewsDaoImpl(final EntityManager entityManagerValue) {
        entityManager = entityManagerValue;
    }

    @Transactional
    @Override
    public void addNews(final List<News> news) {
        news.forEach(newsValue -> {
            LocalDate date = LocalDate.now();
            newsValue.setCreationDate(date);
            newsValue.setModificationDate(date);
            try {
                entityManager.unwrap(Session.class).replicate(newsValue, ReplicationMode.IGNORE);
            } catch (ConstraintViolationException e) {
                throw new EntityDuplicatedException();
            }
        });
    }
}
