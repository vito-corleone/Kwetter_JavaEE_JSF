/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Exception.UserWebServiceException;
import Model.Comment;
import Model.Posting;
import Model.User;
import dto.CommentDTO;
import dto.PostingDTO;
import dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import service.PostingService;
import service.UserService;

/**
 * REST Web Service
 *
 * @author Vito Corleone
 */
@Path("/rest")
public class KwetterREST {

    @Inject
    private UserService userService;

    @Inject
    private PostingService postingService;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public KwetterREST() {
    }

    // PUBLIC METHODS - NO AUTHORIZATION
    @GET
    @Path("/createUser/{name}/{emailAddress}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO createUser(@PathParam("name") String name, @PathParam("emailAddress") String emailAddress, @PathParam("password") String password) {
        // implement error handling
        User newUser = new User(name, emailAddress, password);
        userService.create(newUser);        
        return convertUserToDto(newUser);
    }

    // PUBLIC METHODS - AUTHORIZATION -> USER
    // /user/*
    @GET
    @Path("/user/addToFollow/{userId}/{friendsEmailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addToFollow(@PathParam("userId") Long userId, @PathParam("friendsEmailAddress") String friendsEmailAddress) throws WebApplicationException{
        User user = userService.find(userId);
        if (user != null) {
            User friend = userService.find(friendsEmailAddress);
            if (friend != null) {
                user.addUserThatIWantToFollow(friend);
                userService.edit(user);
                userService.edit(friend);
                return "Succes";
            }
            //catchException("Couldn't find friend");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        //catchException("Couldn't find user");
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/user/getPeopleIFollow/{userId}/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getPeopleIFollow(@PathParam("userId") Long userId) throws UserWebServiceException {
        try {
            User foundUser = userService.find(userId);
            if (foundUser != null) {
                List<UserDTO> peopleThatIFollow = new ArrayList<>();
                for (User user : foundUser.getThePeopleThatIFollow()) {
                    UserDTO dto = convertUserToDto(user);
                    peopleThatIFollow.add(dto);
                }
                return peopleThatIFollow;
            } else {
                throw new UserWebServiceException("Cannot find user");
            }
        } catch (Exception e) {
            throw new UserWebServiceException("Oops.. something went wrong");
        }
    }

    @GET
    @Path("/user/getPeopleThatFollowMe/{userId}/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getPeopleThatFollowME(@PathParam("userId") Long userId) throws UserWebServiceException {
        User foundUser = userService.find(userId);
        if (foundUser != null) {
            List<UserDTO> peopleThatFollowMe = new ArrayList<>();
            for (User user : foundUser.getThePeopleThatFollowMe()) {
                UserDTO dto = convertUserToDto(user);
                peopleThatFollowMe.add(dto);
            }
            return peopleThatFollowMe;
        } else {
            throw new UserWebServiceException("Cannot find user");
        }
    }

    // path /user/posting/
    @GET
    @Path("user/posting/createPosting/{userId}/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public PostingDTO createPosting(@PathParam("userId") Long userId, @PathParam("content") String content) {
        User foundUser = userService.find(userId);
        if (foundUser != null) {
            Posting post = new Posting(foundUser.getEmailAddress(), content);
            postingService.create(post);
            return convertPostingToDto(post);
        }
        return null;
    }
    
    
    

    @GET
    @Path("/user/comment/addComment/{userId}/{postingId}/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommentDTO addComment(@PathParam("userId") Long userId, @PathParam("postingId") Long postingId, @PathParam("content") String content) {
        User foundUser = userService.find(userId);
        if (foundUser != null) {
            Posting post = postingService.find(postingId);
            if(post != null){
                Comment comment = post.addComment(foundUser.getName(), content);
                postingService.edit(post);
                return convertCommentToDto(comment);
            }
        }
        return null;
    }

    // lijst van eigen kweets
    // lijst van volgers
    // lijst van mensen die ik volg
    // POSTING
    // nieuwe post aanmaken
    // zoeken in de content van de post
    // comment op post doen
    // PUBLIC METHODS - AUTHORIZATION -> MODERATOR
    @GET
    @Path("/moderator/getUser/{userId}")
    //@RolesAllowed("Moderator")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") Long userId) {
        return userService.find(userId);
    }

    @GET
    @Path("/moderator/removeUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeUser(@PathParam("userId") Long userId
    ) {

        userService.remove(userId);
        return "Succes";
    }

    @GET
    @Path("/moderator/editUserRole/{userId}/{userRole}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editUserRole(@PathParam("userId") Long userId,
            @PathParam("userRole") String userRole
    ) {
        User getUser = userService.find(userId);
        if (getUser != null) {
            getUser.setUserRole("Moderator");
            userService.edit(getUser);
            return "Succes";
        }
        return "Failed";
    }

//
//    @GET
//    @Path("/user/createUser/{name}/{emailAddress}/{password}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public UserDTO createUser(@PathParam("name") String name, @PathParam("emailAddress") String emailAddress, @PathParam("password") String password) {
//        // implement error handling
//        return convertUserToDto(kwetterService.createUser(name, emailAddress, password));
//    }
//
//    @GET
//    @Path("/user/getFollowers/{emailAddress}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<UserDTO> getFollowers(@PathParam("emailAddress") String emailAddress) {
//        // implement error handling
//        UserDTO userDTO = convertUserToDto(kwetterService.getUser(emailAddress));
//        return userDTO.getFollowers();
//    }
//
//    @GET
//    @Path("/user/getFollowing/{emailAddress}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<UserDTO> getFollowing(@PathParam("emailAddress") String emailAddress) {
//        UserDTO userDTO = convertUserToDto(kwetterService.getUser(emailAddress));
//        return userDTO.getFollowing();
//    }
//
//    @GET
//    @Path("/user/addFollower/{emailAddress}/{friendsEmailAddress}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<UserDTO> addFollower(@PathParam("emailAddress") String emailAddress, @PathParam("friendsEmailAddress") String friendsEmailAddress) {
//        boolean result = kwetterService.addFollower(emailAddress, friendsEmailAddress);
//        if (result) {
//            UserDTO userDTO = convertUserToDto(kwetterService.getUser(emailAddress));
//            return userDTO.getFollowers();
//        }
//        return null;
//    }
//
//    @GET
//    @Path("/user/addToFollow/{emailAddress}/{friendsEmailAddress}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<UserDTO> addToFollow(@PathParam("emailAddress") String emailAddress, @PathParam("friendsEmailAddress") String friendsEmailAddress) {
//        boolean result = kwetterService.addToFollow(emailAddress, friendsEmailAddress);
//        if (result) {
//            UserDTO userDTO = convertUserToDto(kwetterService.getUser(emailAddress));
//            // returned alles van de users, ook passwords
//            return userDTO.getFollowing();
//        }
//        return null;
//    }
//
//    @GET
//    @Path("/posting/createPosting/{emailAddress}/{content}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public PostingDTO createPosting(@PathParam("emailAddress") String emailAddress, @PathParam("content") String content) {
//        return convertPostingToDto(kwetterService.createPosting(emailAddress, content));
//    }
//
//    @GET
//    @Path("/posting/editPosting/{emailAddress}/{postingId}/{author}/{content}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public PostingDTO editPosting(@PathParam("emailAddress") String emailAddress, @PathParam("postingId") Long postingId, @PathParam("author") String author, @PathParam("content") String content) {
//        return convertPostingToDto(kwetterService.editPosting(emailAddress, postingId, author, content));
//    }
//
//    @GET
//    @Path("/comment/addComment/{emailAddress}/{postingId}/{author}/{content}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public CommentDTO addComment(@PathParam("emailAddress") String emailAddress, @PathParam("postingId") Long postingId, @PathParam("author") String author, @PathParam("content") String content) {
//        return convertCommentToDto(kwetterService.addComment(emailAddress, postingId, author, content));
//    }
//
//    @GET
//    @Path("/posting/getAllPostings/{emailAddress}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<PostingDTO> getAllPostings(@PathParam("emailAddress") String emailAddress) {
//        UserDTO userDTO = convertUserToDto(kwetterService.getUser(emailAddress));
//        return userDTO.getAllPostings();
//    }
//
//    @GET
//    @Path("/posting/deletePosting/{emailAddress}/{postingId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public boolean deletePosting(@PathParam("emailAddress") String emailAddress, @PathParam("postingId") Long postingId) {
//        return kwetterService.deletePosting(emailAddress, postingId);
//    }
//
//    @GET
//    @Path("/posting/getPosting/{emailAddress}/{postingId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public PostingDTO getPosting(@PathParam("emailAddress") String emailAddress, @PathParam("postingId") Long postingId) {
//        return convertPostingToDto(kwetterService.getPosting(emailAddress, postingId));
//    }
//
//    /**
//     * Retrieves representation of an instance of rest.KwetterREST
//     *
//     * @return an instance of java.lang.String
//     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "<html lang=\"en\"><body><h1>Hello, Vito!!</body></h1></html>";
    }

    /**
     * PUT method for updating or creating an instance of KwetterREST
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }

    private ModelMapper modelMapper = new ModelMapper();

    //  convert entity to Data Transfer Object (DTO)    
    private CommentDTO convertCommentToDto(Comment comment) {
        CommentDTO commentDto = modelMapper.map(comment, CommentDTO.class);
        return commentDto;
    }

    private PostingDTO convertPostingToDto(Posting post) {
        PostingDTO postDto = modelMapper.map(post, PostingDTO.class);
        return postDto;
    }

    private UserDTO convertUserToDto(User user) {
        UserDTO userDto = null;
        if (user != null) {
            userDto = modelMapper.map(user, UserDTO.class);
        }
        return userDto;
    }

    // convert Data Transfer Object (DTO) to entity
    private Comment convertCommentToEntity(CommentDTO comment) {
        Comment commentEntity = modelMapper.map(comment, Comment.class);
        return commentEntity;
    }

    private Posting convertPostingToEntity(PostingDTO post) {
        Posting postEntity = modelMapper.map(post, Posting.class);
        return postEntity;
    }

    private User convertUserToEntity(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);
        return userEntity;
    }
    
    
    private Response catchException(String message){
        return Response.status(Response.Status.CONFLICT).entity(message).build();
    }

}
