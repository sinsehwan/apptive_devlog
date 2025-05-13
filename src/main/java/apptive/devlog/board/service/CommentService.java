package apptive.devlog.board.service;

import apptive.devlog.board.entity.Comment;
import apptive.devlog.board.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 생성
    public Comment create(Comment comment){
        comment.setCreatedAt(LocalDate.now());
        comment.setUpdatedAt(LocalDate.now());
        comment.setIsDeleted(false);
        comment.setLikeCount(0L);
        return commentRepository.save(comment);
    }

    // 수정
    public Comment update(Long id, Comment updated){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
        comment.setUpdatedAt(LocalDate.now());
        comment.setIsDeleted(updated.getIsDeleted());
        comment.setLikeCount(updated.getLikeCount());
        return commentRepository.save(comment);
    }

    // 삭제 - soft delete
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
        comment.setIsDeleted(true);
        commentRepository.save(comment);
    }

    // 단일 조회. 삭제 요소도 조회 가능
    public Comment get(Long id){
        return commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
    }


    // 작성된 모든 댓글 조회
    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    // 유효한 댓글만 조회
    public List<Comment> getNotDeleted(){
        return commentRepository.findByIsDeletedFalse();
    }
}
