package me.modernpage.restapitutorial.messenger.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import me.modernpage.restapitutorial.messenger.model.Message;
import me.modernpage.restapitutorial.messenger.resources.beans.MessageFIlterBean;
import me.modernpage.restapitutorial.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {
	private MessageService service = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public GenericEntity<List<Message>> getMessages(@BeanParam MessageFIlterBean filter) {
		if(filter.getYear()>0) {
			GenericEntity<List<Message>> entity = new GenericEntity<List<Message>>(service.getAllMessagesforYear(filter.getYear())){};
			return entity;
		}
		if(filter.getStart() > 0 && filter.getSize() > 0) {
			GenericEntity<List<Message>> entity = new GenericEntity<List<Message>>(service.getAllMessagePeginated(filter.getStart()-1, filter.getSize())){};
			return entity;
		}
		
		GenericEntity<List<Message>> entity = new GenericEntity<List<Message>>(service.getAllMessages()){};
		return entity;
	}

	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long messageId) {
		return service.getMessage(messageId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message) {
		return service.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return service.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") long messageId) {
		service.removeMessage(messageId);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
