package lk.ijse.dep8.polling.dao.custom.impl;

import lk.ijse.dep8.polling.dao.custom.PollDAO;
import lk.ijse.dep8.polling.entity.Poll;
import lk.ijse.dep8.polling.service.util.JPAUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PollDAOImplTest {

    private PollDAO pollDAO;
    private EntityManager em;
    static List<Poll> polls = new ArrayList<>();

    public static List<Poll> getDummyPolls() {
        if (polls.isEmpty()) {
            polls.add(new Poll("ABC", 5, 7, "Admin"));
            polls.add(new Poll("Test", 5, 7, "Dulanga"));
            polls.add(new Poll("Something", 5, 7, "Admin"));
            polls.add(new Poll("KFC", 5, 7, "Sasitha"));
            polls.add(new Poll("IJSE", 5, 7, "Admin"));
        }
        return polls;
    }

    @BeforeEach
    void setUp() {
        em = JPAUtil.getEntityManageFactory().createEntityManager();
        pollDAO = new PollDAOImpl(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        em.getTransaction().commit();
        em.close();
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("getDummyPolls")
    void save(/* Given */ Poll poll) {
        /* When */
        Poll actualPoll = pollDAO.save(poll);
        /* Then */
        assertEquals(poll, actualPoll);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("getDummyPolls")
    void findById(/* Given */ Poll poll) {
        /* When */
        Optional<Poll> wrapper = pollDAO.findById(poll.getId());
        /* Then */
        assertTrue(wrapper.isPresent());
    }

    @Order(3)
    @Test
    void findAll() {
        /* Given */
        List<Poll> givenPolls = getDummyPolls();
        /* When */
        List<Poll> actualPolls = pollDAO.findAll();
        /* Then */
        assertEquals(givenPolls, actualPolls);
    }

    @Order(4)
    @Test
    void count() {
        /* Given */
        long givenCount = getDummyPolls().size();
        /* When */
        long actualCount = pollDAO.count();
        /* Then */
        assertEquals(givenCount, actualCount);
    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("getDummyPolls")
    void existsById(/* Given */ Poll poll) {
        /* When */
        boolean result = pollDAO.existById(poll.getId());
        /* Then */
        assertTrue(result);
    }

    @Order(6)
    @ParameterizedTest
    @MethodSource("getDummyPolls")
    void deleteById(/* Given */ Poll poll) {
        /* When */
        pollDAO.deleteById(poll.getId());
        /* Then */
        assertThrows(AssertionError.class, ()-> existsById(poll));
    }

}