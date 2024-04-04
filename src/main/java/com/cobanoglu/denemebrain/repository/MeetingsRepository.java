package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingsRepository extends JpaRepository<Meetings, Long> {

}
