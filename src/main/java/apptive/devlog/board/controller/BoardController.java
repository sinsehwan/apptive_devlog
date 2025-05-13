package apptive.devlog.board.controller;

import apptive.devlog.board.entity.Comment;
import apptive.devlog.board.entity.Post;
import apptive.devlog.board.service.CommentService;
import apptive.devlog.board.service.PostService;
import apptive.devlog.board.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;

    // 게시물 생성
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        Post createdPost = postService.create(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost); // 201
    }

    // 게시물 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        Post post = postService.get(id);
        return ResponseEntity.ok(post); // 200
    }

    // 유효한 게시물 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getNotDeleted();
        return ResponseEntity.ok(posts); // 200
    }

    // 게시물 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost){
        Post post = postService.update(id, updatedPost);
        return ResponseEntity.ok(post); // 200
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.noContent().build(); // 200
    }

    // 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment){
        // 게시물에 댓글 연결
        comment.setPost(postService.get(postId));
        Comment createdComment = commentService.create(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment); // 201
    }

    // 게시물 댓글 목록 조회

    //getNotDeleted 를 써서 stream으로 뽑아내는 것과
    // getNotDeletedForPost 등 애초에 Repository랑 Service계층에서 함수를 매우 많이 만들어서
    // controller에서는 함수만 쓰는 방식
    // 후자가 최적화에는 유리해 보이는데
    // 이를 위해서 이름이 매우 긴 메소드를 일일이 명세해서 사용해야 하는가
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getNotDeleted()
                .stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .toList();
        if(comments.isEmpty()) {
            return ResponseEntity.noContent().build(); // 404
        }

        return ResponseEntity.ok(comments); // 200 ok
    }

}
