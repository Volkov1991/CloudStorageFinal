package netology.cloudstoragefinal.repository;

import netology.cloudstoragefinal.model.FileEntity;
import netology.cloudstoragefinal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByFileNameAndUser(String fileName, UserEntity user);

    Optional<List<FileEntity>> findAllByUser(UserEntity user);
}
