package co.zip.candidate.userapi.dao.impl;

import co.zip.candidate.userapi.dao.UserDao;
import co.zip.candidate.userapi.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * DAO implementation for User persistant operations
 */
@Component
public class UserDaoImpl implements UserDao {

    /**
     * LIST_USERS query
     */
    public static final String LIST_USERS = "select u from User u";

    /**
     * GET_USER query
     */
    public static final String GET_USER = "select u from User u where u.id=:id";

    /**
     * GET_USER_BY_EMAIL query
     */
    public static final String GET_USER_BY_EMAIL = "select u from User u where u.email=:email";

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Create User
     * @param user user
     * @return user with userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    /**
     * List users
     * @return list of users
     */
    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery(LIST_USERS);
        return query.getResultList();
    }

    /**
     * getUser by userId
     * @param id id
     * @return user
     */
    @Override
    public User getUser(String id) {
        Query query = entityManager.createQuery(GET_USER);
        query.setParameter(ID, UUID.fromString(id));
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * getUserByEmail
     * @param email email
     * @return user
     */
    @Override
    public User getUserByEmail(String email) {
        Query query = entityManager.createQuery(GET_USER_BY_EMAIL);
        query.setParameter(EMAIL, email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
