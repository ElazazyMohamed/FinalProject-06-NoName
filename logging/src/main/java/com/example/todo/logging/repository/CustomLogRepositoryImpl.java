package com.example.todo.logging.repository;

import com.example.todo.logging.model.Log;
import com.example.todo.logging.model.LogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CustomLogRepository interface
 */
public class CustomLogRepositoryImpl implements CustomLogRepository {

    private final MongoTemplate mongoTemplate;

    public CustomLogRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Log> findLogsWithFilter(LogFilter filter, Pageable pageable) {
        final Query query = new Query();
        final List<Criteria> criteriaList = new ArrayList<>();

        // Add criteria based on filter parameters
        if (filter.getLevels() != null && !filter.getLevels().isEmpty()) {
            criteriaList.add(Criteria.where("level").in(filter.getLevels()));
        }

        if (filter.getTypes() != null && !filter.getTypes().isEmpty()) {
            criteriaList.add(Criteria.where("type").in(filter.getTypes()));
        }

        if (filter.getMessageContains() != null && !filter.getMessageContains().isEmpty()) {
            criteriaList.add(Criteria.where("message").regex(filter.getMessageContains(), "i"));
        }

        if (filter.getUserId() != null && !filter.getUserId().isEmpty()) {
            criteriaList.add(Criteria.where("userId").is(filter.getUserId()));
        }

        if (filter.getSource() != null && !filter.getSource().isEmpty()) {
            criteriaList.add(Criteria.where("source").regex(filter.getSource(), "i"));
        }

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            criteriaList.add(Criteria.where("timestamp").gte(filter.getStartDate()).lte(filter.getEndDate()));
        } else if (filter.getStartDate() != null) {
            criteriaList.add(Criteria.where("timestamp").gte(filter.getStartDate()));
        } else if (filter.getEndDate() != null) {
            criteriaList.add(Criteria.where("timestamp").lte(filter.getEndDate()));
        }

        // Combine all criteria with AND
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        // Count total matches
        long total = mongoTemplate.count(query, Log.class);

        // Apply pagination
        query.with(pageable);

        // Execute query
        List<Log> logs = mongoTemplate.find(query, Log.class);

        // Return paginated result
        return new PageImpl<>(logs, pageable, total);
    }

    @Override
    public long deleteLogsOlderThan(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        Query query = new Query(Criteria.where("timestamp").lt(cutoffDate));
        return mongoTemplate.remove(query, Log.class).getDeletedCount();
    }

    @Override
    public Object[] countLogsBySource() {
        GroupOperation groupBySource = Aggregation.group("source").count().as("count");
        Aggregation aggregation = Aggregation.newAggregation(groupBySource);
        AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "logs", Object.class);
        return results.getMappedResults().toArray();
    }

    @Override
    public Object[] countLogsByType() {
        GroupOperation groupByType = Aggregation.group("type").count().as("count");
        Aggregation aggregation = Aggregation.newAggregation(groupByType);
        AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "logs", Object.class);
        return results.getMappedResults().toArray();
    }

    @Override
    public Object[] countLogsByLevel() {
        GroupOperation groupByLevel = Aggregation.group("level").count().as("count");
        Aggregation aggregation = Aggregation.newAggregation(groupByLevel);
        AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "logs", Object.class);
        return results.getMappedResults().toArray();
    }
}