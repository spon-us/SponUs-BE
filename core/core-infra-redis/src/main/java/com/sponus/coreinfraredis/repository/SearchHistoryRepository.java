package com.sponus.coreinfraredis.repository;

import org.springframework.data.repository.CrudRepository;

import com.sponus.coreinfraredis.entity.SearchHistory;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Long> {
}
