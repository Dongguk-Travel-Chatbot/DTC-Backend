package hanium.dtc.community.service;

import hanium.dtc.community.domain.Comment;
import hanium.dtc.community.domain.CommentLike;
import hanium.dtc.community.domain.Post;
import hanium.dtc.community.domain.PostLike;
import hanium.dtc.community.repository.CommentLikeRepository;
import hanium.dtc.community.repository.CommentRepository;
import hanium.dtc.community.repository.PostLikeRepository;
import hanium.dtc.community.repository.PostRepository;
import hanium.dtc.exception.CommonException;
import hanium.dtc.exception.ErrorCode;
import hanium.dtc.user.domain.User;
import hanium.dtc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean togglePostLike(Long postId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if (post.getUser().getId().equals(userId)) {
            throw new CommonException(ErrorCode.SELF_ACTION_NOT_ALLOWED);
        }

        Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            post.decrementLike();
        } else {
            postLikeRepository.save(new PostLike(user, post));
            post.incrementLike();
        }
        postRepository.save(post);
        return existingLike.isEmpty();
    }

    @Transactional
    public boolean toggleCommentLike(Long commentId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if (comment.getUser().getId().equals(userId)) {
            throw new CommonException(ErrorCode.SELF_ACTION_NOT_ALLOWED);
        }

        Optional<CommentLike> existingLike = commentLikeRepository.findByUserAndComment(user, comment);

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.decrementLike();

        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
            comment.incrementLike();
        }

        commentRepository.save(comment);
        return existingLike.isEmpty();
    }
    @Transactional(readOnly = true)
    public int getPostLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        return post.getPostLike();
    }

    @Transactional(readOnly = true)
    public int getCommentLike(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        return comment.getCommentLike();
    }
}


