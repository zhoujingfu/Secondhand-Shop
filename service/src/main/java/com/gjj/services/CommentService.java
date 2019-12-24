package com.gjj.services;

import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.enums.Read;
import com.gjj.exceptions.BusinessException;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Comment;
import com.gjj.models.SecondComment;
import com.gjj.models.User;
import com.gjj.qModels.QComment;
import com.gjj.repositories.CommentRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gjj on 2018-05-05.
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    public Comment getOneComment(Integer commentId) {
        Comment comment;
        try {
            comment = commentRepository.getOne(commentId);
        } catch (BusinessException e) {
            throw new UnAuthorizedException(ErrorCode.COMMENT_NOT_EXIST, ErrorMessage.COMMENT_NOT_EXIST);
        }
        return comment;
    }

    public List getComments(Integer goodsId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanBuilder secondBooleanBuilder = new BooleanBuilder();
        QComment qComment = QComment.comment;
        QComment secondQComment = QComment.comment;
        if (goodsId != null) {
            booleanBuilder.and(qComment.goodsId.eq(goodsId));
            booleanBuilder.and(qComment.replyCommentId.isNull());
            secondBooleanBuilder.and(secondQComment.goodsId.eq(goodsId));
            secondBooleanBuilder.and(secondQComment.replyCommentId.isNotNull());
        }
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<Comment> list = (List) commentRepository.findAll(booleanBuilder,sort);
        List<Comment> list2 = (List) commentRepository.findAll(secondBooleanBuilder,sort);
        /**
         * 组装评论
         */
        List commentList = new ArrayList();
        for (Comment comment: list) {
            SecondComment secondComment = new SecondComment();
            secondComment.setId(comment.getId());
            secondComment.setContent(comment.getContent());
            secondComment.setGoodsId(comment.getGoodsId());
            secondComment.setUser(comment.getUser());
            secondComment.setCommentDate(comment.getCommentDate());
            secondComment.setReplyUser(comment.getReplyUser());
            secondComment.setReplyCommentId(comment.getReplyCommentId());
            secondComment.setRead(comment.getRead());
            for (Comment comment2 : list2) {
                if (secondComment.getList() == null) {
                    secondComment.setList(new ArrayList<Comment>());
                }
                for (Comment comment3 : secondComment.getList()) {
                    if (comment3.getId().equals(comment2.getReplyCommentId())) {
                        comment2.setReplyCommentId(secondComment.getId());
                        continue;
                    }
                }
                if (secondComment.getId().equals(comment2.getReplyCommentId())) {
                    secondComment.getList().add(comment2);
                }

            }
            commentList.add(secondComment);
        }
        return commentList;
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        if (comment.getReplyCommentId() == null) {
            /**
             * 删除一级评论
             */
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            QComment qComment = QComment.comment;
            if (true) {
                booleanBuilder.and(qComment.replyCommentId.eq(comment.getId()));
            }
            List<Comment> list =  (List) commentRepository.findAll(booleanBuilder);
            if (list != null) {
                commentRepository.delete(list);
            }
        }
        commentRepository.delete(comment);
    }

    public Long getUnreadCount(Integer userId) {
        BooleanBuilder booleanBuilder = booleanBuilder(userId,"");
        Long count = commentRepository.count(booleanBuilder);
        return count;
    }

    public List getUnreadComment(Integer userId) {
        BooleanBuilder booleanBuilder = booleanBuilder(userId,"");
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        List<Comment> list =  (List) commentRepository.findAll(booleanBuilder,sort);
        return list;
    }

    private BooleanBuilder booleanBuilder(Integer userId,String type) {
        User user = authenticationUserService.getUser(userId);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QComment qComment = QComment.comment;
        if (userId != null) {
            booleanBuilder.and(qComment.replyUser.eq(user));
            if (!type.equals("all")) {
                booleanBuilder.and(qComment.read.eq(Read.UNREAD.getRead()));
            }
        }
        return booleanBuilder;
    }

    public void commentIsRead(Integer commentId) {
//        List<Comment> list = getUnreadComment(userId);
//        for (Comment comment : list) {
//            comment.setRead(Read.ALREADYREAD.getRead());
//            commentRepository.save(comment);
//        }

            Comment comment = this.getOneComment(commentId);
            comment.setRead(Read.ALREADYREAD.getRead());
            commentRepository.save(comment);

    }

    public List getUserComment(Integer userId) {
        BooleanBuilder booleanBuilder = booleanBuilder(userId,"all");
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        List<Comment> list =  (List) commentRepository.findAll(booleanBuilder,sort);
        return list;
    }

}
