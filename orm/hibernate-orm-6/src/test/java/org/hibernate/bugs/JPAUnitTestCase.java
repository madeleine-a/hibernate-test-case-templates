package org.hibernate.bugs;

import entities.InstantEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	@Test
	public void testUpdate(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Instant dateTime = Instant.now().minus(1, ChronoUnit.HOURS);
		InstantEntity e = new InstantEntity();
		e.setDateValue(dateTime);
		entityManager.persist(e);
		entityManager.flush();
		entityManager.clear();

		Query query = entityManager.createNamedQuery("InstantEntity.updateDateValue2");
		query.setParameter("date", Instant.now());
		query.executeUpdate();

		entityManager.getTransaction().rollback();
		entityManager.close();

	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void testTimestampUTC() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Instant dateTime = Instant.now().minus(70, ChronoUnit.MINUTES);
		InstantEntity e = new InstantEntity();
		e.setDateValue(dateTime);
		entityManager.persist(e);
		entityManager.flush();
		entityManager.clear();

		Query query = entityManager.createNamedQuery("InstantEntity.findBetween", InstantEntity.class);
		query.setParameter("from", Instant.now().minus(2, ChronoUnit.HOURS));
		query.setParameter("to", Instant.now().minus(1, ChronoUnit.HOURS));
		List<?> resultList = query.getResultList();
		// Should find entity with a dateValue within the time above
		Assertions.assertThat(resultList.size()).isEqualTo(1);
		entityManager.getTransaction().rollback();
		entityManager.close();
	}
}
