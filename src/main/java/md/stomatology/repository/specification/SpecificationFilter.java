package md.stomatology.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import md.stomatology.model.Customer;

public class SpecificationFilter<T> implements Specification<T> {

	private String key;
	private String operation;
	private Object value;

	public SpecificationFilter(String key, String operation, Object value) {
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		if (operation.equalsIgnoreCase(">")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.<String> get(key), value.toString());
		} else if (operation.equalsIgnoreCase("<")) {
			return criteriaBuilder.lessThanOrEqualTo(root.<String> get(key), value.toString());
		} else if (operation.equalsIgnoreCase("like")) {
			if (root.get(key).getJavaType() == String.class) {
				return criteriaBuilder.like(root.<String> get(key), "%" + value + "%");
			} else {
				return criteriaBuilder.equal(root.get(key), value);
			}
		} else if (operation.equalsIgnoreCase("=")) {
			return criteriaBuilder.equal(root.get(key), value);
		}
		return null;
	}

}
