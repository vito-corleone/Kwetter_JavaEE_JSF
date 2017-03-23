///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Exception;
//
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//import javax.ws.rs.core.MediaType;
//
//
///**
// *
// * @author Vito Corleone
// */
//@Provider
//public class UserWebServiceExceptionHandler implements ExceptionMapper<UserWebServiceException> {
//
//    @Override
//    public Response toResponse(final UserWebServiceException exception) {
//        return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
//    }
//}
