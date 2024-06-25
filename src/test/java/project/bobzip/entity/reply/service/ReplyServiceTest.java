package project.bobzip.entity.reply.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.repository.ReplyRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;


    @Test
    void findAllTest() {
        // given
        List<Reply> testReplies = Arrays.asList(
                Reply.builder()
                        .comment("정말 맛있는 요리입니다.")
                        .build(),
                Reply.builder()
                        .comment("조리법 대로 요리하니 간이 좀 맞지 않네요...")
                        .build()
        );
        replyRepository.saveAll(testReplies);
        PageRequest pageable = PageRequest.of(0, 1);

        // when
        Page<Reply> pagingReply = replyService.findAll(pageable);

        // then
        List<Reply> content = pagingReply.getContent();
        assertThat(pagingReply.getTotalPages()).isEqualTo(2);
        assertThat(content.size()).isEqualTo(1);

    }
}
