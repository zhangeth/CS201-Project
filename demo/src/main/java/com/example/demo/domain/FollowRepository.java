package com.example.demo.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
 
 
@Repository
public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
    //get followed users list from user
	List <Follow> findByUserID(Long userID);
}
