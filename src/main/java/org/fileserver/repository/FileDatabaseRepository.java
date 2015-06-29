package org.fileserver.repository;

import org.fileserver.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDatabaseRepository extends JpaRepository<File, String> {

    @Modifying
    @Query("UPDATE File f SET f.deleted = 1 WHERE f.id=:id")
    void markFileAsDeleted(@Param("id") String id);

    @Query("FROM File f WHERE f.deleted = 0")
    List<File> findAllNotDeleted();

    @Query("FROM File f WHERE f.deleted = 1")
    List<File> findAllDeleted();
}
