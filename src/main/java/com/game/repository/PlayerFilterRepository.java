package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.controller.dto.PlayerFilterRequest;
import com.game.entity.Player;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PlayerFilterRepository {

    @PersistenceContext
    EntityManager em;

    public List<Player> findAll(PlayerFilterRequest request, PlayerOrder order, int pageNumber, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> root = cq.from(Player.class);
        List<Predicate> predicates = predicatesGetRequest(request, cb, root);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get(order.getFieldName())));

        TypedQuery<Player> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    public Integer count(PlayerFilterRequest request) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Player> root = cq.from(Player.class);
        List<Predicate> predicates = predicatesGetRequest(request, cb, root);
        cq.select(cb.count(root));
        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    private List<Predicate> predicatesGetRequest(PlayerFilterRequest request, CriteriaBuilder cb, Root<Player> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getName() != null) {
            Predicate predicate = cb.like(root.get("name"), "%" + request.getName() + "%");
            predicates.add(predicate);
        }
        if (request.getTitle() != null) {
            Predicate predicate = cb.like(root.get("title"), "%" + request.getTitle() + "%");
            predicates.add(predicate);
        }
        if (request.getRace() != null) {
            Predicate predicate = cb.equal(root.get("race"), request.getRace());
            predicates.add(predicate);
        }
        if (request.getProfession() != null) {
            Predicate predicate = cb.equal(root.get("profession"), request.getProfession());
            predicates.add(predicate);
        }
        if (request.getBanned() != null) {
            Predicate predicate = cb.equal(root.get("banned"), request.getBanned());
            predicates.add(predicate);
        }
        // birthday
        if (request.getAfter() != null) {
            Date dateAfter = Date.from(Instant.ofEpochMilli(request.getAfter()));
            Predicate predicate = cb.greaterThanOrEqualTo(root.get("birthday"), dateAfter);
            predicates.add(predicate);
        }
        if (request.getBefore() != null) {
            Date dateBefore = Date.from(Instant.ofEpochMilli(request.getBefore()));
            Predicate predicate = cb.lessThanOrEqualTo(root.get("birthday"), dateBefore);
            predicates.add(predicate);
        }
        // experience
        if (request.getMinExperience() != null) {
            Predicate predicate = cb.greaterThanOrEqualTo(root.get("experience"), request.getMinExperience());
            predicates.add(predicate);
        }
        if (request.getMaxExperience() != null) {
            Predicate predicate = cb.lessThanOrEqualTo(root.get("experience"), request.getMaxExperience());
            predicates.add(predicate);
        }
        // level
        if (request.getMinLevel() != null) {
            Predicate predicate = cb.greaterThanOrEqualTo(root.get("level"), request.getMinLevel());
            predicates.add(predicate);
        }
        if (request.getMaxLevel() != null) {
            Predicate predicate = cb.lessThanOrEqualTo(root.get("level"), request.getMaxLevel());
            predicates.add(predicate);
        }

        return predicates;
    }
}
