package tk.srubio.adoptix.web.search;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import tk.srubio.adoptix.model.Pet;

public class PetFilter {
	
	public static Specification<Pet> lessOrEqual(final String field, final Integer value) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.lessThanOrEqualTo(root.get(field), value);
			}
		};
	}
	
	public static Specification<Pet> greaterOrEqual(final String field, final Integer value) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.greaterThanOrEqualTo(root.get(field), value);
			}
		};
	}
	
	public static Specification<Pet> equalToLocationId(final String field, final Byte value) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(field).get("id"), value);
			}
		};
	}
	
	public static Specification<Pet> equal(final String field, final Object value) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(field), value);
			}
		};
	}
	
	public static Specification<Pet> isTrue(final String field) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isTrue(root.get(field));
			}
		};
	}
	
	public static Specification<Pet> getSpecification(final List<Specification<Pet>> sp) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();
				for (Specification<Pet> specification : sp) {
					p.getExpressions().add(specification.toPredicate(root, query, cb));
				}
				return p;
			}
		};
	}
}
