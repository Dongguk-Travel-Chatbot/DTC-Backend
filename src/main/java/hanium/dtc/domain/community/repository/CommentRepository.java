package hanium.dtc.domain.community.repository;

import hanium.dtc.domain.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPostId(Long postId);
}
