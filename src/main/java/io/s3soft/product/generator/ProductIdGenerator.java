package io.s3soft.product.generator;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		String id="PRD2019";
		String uuid=UUID.randomUUID().toString().replace("-","").substring(1,5);
		id=id+uuid;
		return id.toUpperCase();
	}

}
