package ru.gudkov.test_task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gudkov.test_task.entity.Paste;

import java.time.LocalDateTime;

public interface PasteRepository extends JpaRepository<Paste, Long> {
    Paste findByHashCode(Long hashCode);
    @Query("SELECT p FROM Paste p " +
            "WHERE p.expirationTime >= :time " +
            "AND p.access = 'PUBLIC' " +
            "ORDER BY p.expirationTime DESC")
    Page<Paste> findAllByExpirationTimeAndAccess(@Param("time") LocalDateTime time, PageRequest pageable);
}
