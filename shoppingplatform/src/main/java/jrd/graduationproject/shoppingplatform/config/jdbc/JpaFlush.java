package jrd.graduationproject.shoppingplatform.config.jdbc;

import javax.persistence.EntityManager;

public class JpaFlush {

	private EntityManager entityManager;

	public void flush(Class<?> entityClass, Object primaryKey) {

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
