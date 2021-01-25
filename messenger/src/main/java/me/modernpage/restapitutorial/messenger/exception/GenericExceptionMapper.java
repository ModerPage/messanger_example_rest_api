package me.modernpage.restapitutorial.messenger.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import me.modernpage.restapitutorial.messenger.model.ErrorMessage;


public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	
	@Context 
	private HttpHeaders headers;
	
	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "https://modernpage.me");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.type(headers.getMediaType())
				.build();
	}
}
