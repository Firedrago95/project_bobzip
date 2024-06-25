package project.bobzip.entity.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Page<Reply> findAll(Long id, Pageable pageable) {
        return replyRepository.findByRecipeId(id, pageable);
    }
}
