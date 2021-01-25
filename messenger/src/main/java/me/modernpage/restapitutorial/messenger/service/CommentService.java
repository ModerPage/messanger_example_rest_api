package me.modernpage.restapitutorial.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import me.modernpage.restapitutorial.messenger.database.DatabaseClass;
import me.modernpage.restapitutorial.messenger.model.Comment;
import me.modernpage.restapitutorial.messenger.model.ErrorMessage;
import me.modernpage.restapitutorial.messenger.model.Message;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	public CommentService() {
	}
	
	public List<Comment> getAllComments(long messageId) {
		System.out.println("getAllComments called");
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		System.out.println("comments size: " + comments.size());
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		if(message == null) {
			ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "https://modernpage.me");
			Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(errorMessage)
					.type("application/json")
					.build();
			throw new WebApplicationException(response);
		}
		Comment comment = message.getComments().get(commentId);
		
		if(comment == null) {
			throw new NotFoundException();
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		System.out.println("addComment called");
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		System.out.println("messageId: " + messageId + " , size of comments: " + comments.size());
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
