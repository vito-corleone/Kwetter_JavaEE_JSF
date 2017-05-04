/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Exception.UserWebServiceException;
import Model.Anon_Posting;
import Model.Comment;
import Model.Posting;
import Model.User;
import com.google.common.hash.Hashing;
import dto.CommentDTO;
import dto.PostingDTO;
import dto.UserDTO;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
import service.JMSService;
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
    
    @Inject
    private JMSService jmsService;

    @Context
    private UriInfo context;


    /**
     * Creates a new instance of GenericResource
     */
    public KwetterREST() {
    }

    // ********************************************** PUBLIC METHODS - NO AUTHORIZATION *************************************************************
    @GET
    @Path("/login/{emailAddress}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public User login(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password) {
        // implement error handling
        User foundUser = userService.find(emailAddress);
        String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        if (foundUser.getPassword().equals(passwordHash)) {
            foundUser.setThePeopleThatFollowMe(new ArrayList<User>());
            foundUser.setThePeopleThatIFollow(new ArrayList<User>());
            return foundUser;
        }
        return null;
    }

    // get public JMS-Messages
    @GET
    @Path("/getJmsMessages")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getJmsMessages() {
        List<String> messages = new ArrayList<>();
        List<Anon_Posting> postings = jmsService.getAllAnonPostings();
        Collections.sort(postings, Collections.reverseOrder());
        int i = 0;
        for(Anon_Posting posting : postings){
            messages.add(posting.getAuthor() + ": " + posting.getContent() + " | ");
            i++;
            if(i >= 10){
                break;
            }
        }
        return messages;
    }

    // CREATE NEW USER
    @GET
    @Path("/createUser/{name}/{emailAddress}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO createUser(@PathParam("name") String name, @PathParam("emailAddress") String emailAddress, @PathParam("password") String password) {
        // implement error handling
        User newUser = new User(name, emailAddress, password);
        userService.create(newUser);
        return convertUserToDto(newUser);
    }

    // GET PUBLIC USER INFORMATION
    @GET
    @Path("/getPublicUserInfo/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getPublicUserInfo(@PathParam("emailAddress") String emailAddress) {
        return convertUserToDto(userService.find(emailAddress));
    }

    // GET THE PEOPLE THE USER FOLLOWS
    @GET
    @Path("/getThePeopleThatIFollow/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getThePeopleThatIFollow(@PathParam("emailAddress") String emailAddress) {
        // implement error handling     
        User foundUser = userService.find(emailAddress);
        List<UserDTO> userDTO = new ArrayList<UserDTO>();
        for (User user : foundUser.getThePeopleThatIFollow()) {
            userDTO.add(convertUserToDto(user));
        }
        return userDTO;
    }

    // GET THE PEOPLE THAT FOLLOW THE USER
    @GET
    @Path("/getThePeopleThatFollowMe/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getThePeopleThatFollowMe(@PathParam("emailAddress") String emailAddress) {
        // implement error handling     
        User foundUser = userService.find(emailAddress);
        List<UserDTO> userDTO = new ArrayList<UserDTO>();
        for (User user : foundUser.getThePeopleThatFollowMe()) {
            userDTO.add(convertUserToDto(user));
        }
        return userDTO;
    }

    // GET THE PUBLIC TIMELINE OF USER FOR THE PROFILEPAGE
    @GET
    @Path("/getAllPostings/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PostingDTO> getAllPostings(@PathParam("emailAddress") String emailAddress) {
        List<Posting> postings = postingService.find(emailAddress);
        Collections.sort(postings, Collections.reverseOrder());
        List<PostingDTO> postingsDTO = new ArrayList<>();
        // only the last 10 posts
        int i = 1;
        for (Posting post : postings) {
            postingsDTO.add(convertPostingToDto(post));
            if (i >= 10) {
                break;
            }
            i++;
        }
        return postingsDTO;
    }

    // ********************************************** PRIVATE METHODS - USER ROLE AUTHORIZATION *************************************************************
    // GET THE POSTINGS THAT CONTAIN THE KEYWORD
    @GET
    @Path("/getSearchResult/{searchKeyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PostingDTO> getSearchResult(@PathParam("searchKeyword") String searchKeyword) {
        List<Posting> postings = postingService.searchPosting(searchKeyword);
        Collections.sort(postings, Collections.reverseOrder());
        List<PostingDTO> postingsDTO = new ArrayList<>();
        for (Posting post : postings) {
            postingsDTO.add(convertPostingToDto(post));
        }
        return postingsDTO;
    }

    @GET
    @Path("/getTimelinePostings/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PostingDTO> getTimelinePostings(@PathParam("emailAddress") String emailAddress) {

        User foundUser = userService.find(emailAddress);
        List<Posting> postings = postingService.find(foundUser.getEmailAddress());
        for (User user : foundUser.getThePeopleThatIFollow()) {
            postings.addAll(postingService.find(user.getEmailAddress()));
        }
        Collections.sort(postings, Collections.reverseOrder());
        List<PostingDTO> postingsDTO = new ArrayList<>();
        for (Posting post : postings) {
            postingsDTO.add(convertPostingToDto(post));
        }
        return postingsDTO;
    }

    @GET
    @Path("/updateUserInfo/{emailAddress}/{name}/{bio}/{location}/{website}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUpdateUserInfo(@PathParam("emailAddress") String emailAddress, @PathParam("name") String name, @PathParam("bio") String bio, @PathParam("location") String location, @PathParam("website") String website) {
        User user = userService.find(emailAddress);
        user.setName(name);
        user.setBio(bio);
        user.setLocation(location);
        user.setWebsite(website);
        userService.edit(user);
        UserDTO edittedUser = convertUserToDto(userService.find(emailAddress));
        return edittedUser;
    }

    // /user/*
    @GET
    @Path("/user/addToFollow/{userId}/{friendsEmailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addToFollow(@PathParam("userId") Long userId, @PathParam("friendsEmailAddress") String friendsEmailAddress) throws WebApplicationException {
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

    // ********************************************** PRIVATE METHODS - MODERATOR ROLE AUTHORIZATION *************************************************************
    @GET
    @Path("/getPrivateUserInfo/{emailAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getPrivateUserInfo(@PathParam("emailAddress") String emailAddress) {
        User foundUser = userService.find(emailAddress);
        foundUser.setThePeopleThatFollowMe(new ArrayList<User>());
        foundUser.setThePeopleThatIFollow(new ArrayList<User>());
        return foundUser;
    }

    @GET
    @Path("/editUser/{emailAddress}/{name}/{bio}/{location}/{website}/{userrole}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editUser(@PathParam("emailAddress") String emailAddress, @PathParam("name") String name, @PathParam("bio") String bio, @PathParam("location") String location, @PathParam("website") String website, @PathParam("userrole") String userrole) {
        User foundUser = userService.find(emailAddress);
        foundUser.setName(name);
        foundUser.setBio(bio);
        foundUser.setLocation(location);
        foundUser.setWebsite(website);
        foundUser.setUserRole(userrole);
        userService.edit(foundUser);
        return "{\"Result\":\"Succes\"}";
    }

    @GET
    @Path("/removePost/{postID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removePost(@PathParam("postID") String postID) {
        postingService.remove(Long.valueOf(postID));
        return "{\"Result\":\"Succes\"}";
    }

    @GET
    @Path("/removeUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeUser(@PathParam("userId") String userId) {
        userService.remove(Long.valueOf(userId));
        return "{\"Result\":\"Succes\"}";
    }

    @GET
    @Path("/getAllUsers/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserDTO> allUsersDTO = new ArrayList<>();
        for (User user : allUsers) {
            allUsersDTO.add(convertUserToDto(user));
        }
        return allUsersDTO;
    }

    @GET
    @Path("/getAllKweets/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Posting> getAllKweets() {
        return postingService.getAllPostings();
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

    private Response catchException(String message) {
        return Response.status(Response.Status.CONFLICT).entity(message).build();
    }

}
