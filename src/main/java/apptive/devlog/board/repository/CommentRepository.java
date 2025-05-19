package apptive.devlog.board.repository;

import apptive.devlog.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIsDeletedFalse();
}
