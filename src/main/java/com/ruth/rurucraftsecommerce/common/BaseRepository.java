package com.ruth.rurucraftsecommerce.common;

import com.ruth.rurucraftsecommerce.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean

public interface BaseRepository<T> extends JpaRepository<T,Integer> {
    List<T> findAllByDeletedAtIsNull();

}
