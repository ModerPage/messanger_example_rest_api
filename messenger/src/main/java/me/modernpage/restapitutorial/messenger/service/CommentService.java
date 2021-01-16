package me.modernpage.restapitutorial.messenger.service;

import java.util.List;
import java.util.Map;

import me.modernpage.restapitutorial.messenger.database.DatabaseClass;
import me.modernpage.restapitutorial.messenger.model.Comment;
import me.modernpage.restapitutorial.messenger.model.Message;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		return (List<Comment>) messages.get(messageId).getComments().values();
	}
	
	public Comment getComment(long messageId, long commentId) {
		return messages.get(messageId).getComments().get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
