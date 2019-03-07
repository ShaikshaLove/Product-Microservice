package io.s3soft.product.generator;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CatIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
       String id="CAT2019";
       String randomId=UUID.randomUUID().toString().replaceAll("-", "").substring(1,7).toUpperCase();
	   return (id+randomId).toUpperCase();
	}

}
