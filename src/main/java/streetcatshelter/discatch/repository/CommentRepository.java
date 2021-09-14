package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countAllByCommunityId(Long communityId);
}
