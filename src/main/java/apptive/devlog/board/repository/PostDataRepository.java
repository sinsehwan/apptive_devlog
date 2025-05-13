package apptive.devlog.board.repository;

import apptive.devlog.board.entity.PostData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDataRepository extends JpaRepository<PostData, Long> {
    List<PostData> findByIsDeletedFalse();
}
