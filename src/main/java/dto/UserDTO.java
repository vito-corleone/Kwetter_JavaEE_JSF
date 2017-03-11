///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package dto;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.Id;
//import model.Posting123;
//import model.User123;
//import model.UserDetails;
//import model.UserRole;
//
///**
// *
// * @author Vito Corleone
// */
//public class UserDTO {
//
//    private List<UserDTO> following;
//    private List<UserDTO> followers;
//    private UserRole userRole;
//    private UserDetailsDTO userDetails;
//    private List<PostingDTO> postings;
//    private String name;
//    private String emailAddress;
//    private String password;
//    private Long id;
//
//    public UserDTO(String name, String emailAddress, String password) {
//        following = new ArrayList<UserDTO>();
//        followers = new ArrayList<UserDTO>();
//        userDetails = new UserDetailsDTO();
//        postings = new ArrayList<PostingDTO>();
//        this.userRole = UserRole.NORMAL_USER;
//        this.name = name;
//        this.emailAddress = emailAddress;
//        this.password = password;
//    }
//
//    public UserDTO() {
//
//    }
//
//    public void setName(String name) {
//        this.name = name;
//
//    }
//
//    public void setEmailAddress(String emailAddress) {
//        this.emailAddress = emailAddress;
//
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//
//    }
//
//    public void setUserRole(UserRole userRole) {
//        this.userRole = userRole;
//
//    }
//
//    public UserRole getUserRole() {
//        return this.userRole;
//    }
//
//    public boolean addToFollow(UserDTO newFriend) {
//        if (!following.contains(newFriend)) {
//            following.add(newFriend);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean addFollower(UserDTO newFriend) {
//        if (!followers.contains(newFriend)) {
//            followers.add(newFriend);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean removeFriend(UserDTO oldFriend) {
//        if (following.contains(oldFriend)) {
//            following.remove(oldFriend);
//            return true;
//        }
//        return false;
//    }
//
//    public void setBio(String bio) {
//        if (userDetails != null) {
//            userDetails.setBio(bio);
//        }
//    }
//
//    public void setWebsite(String website) {
//        if (userDetails != null) {
//            userDetails.setWebsite(website);
//        }
//    }
//
//    public void setLocation(String location) {
//        if (userDetails != null) {
//            userDetails.setLocation(location);
//        }
//    }
//
//    public List<UserDTO> getFollowing() {
//        return this.following;
//    }
//
//    public void setFollowing(List<UserDTO> following) {
//        this.following = following;
//    }
//
//    public List<UserDTO> getFollowers() {
//        return this.followers;
//    }
//
//    public void setFollowers(List<UserDTO> followers) {
//        this.followers = followers;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public String getEmailAddress() {
//        return this.emailAddress;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public PostingDTO editPost(Long postingId, String content) {
//        if (postingId > 0 && !content.isEmpty()) {
//            for (PostingDTO post : postings) {
//                if (post.getId().equals(postingId)) {
//                    post.setContent(content);
//                    return post;
//                }
//            }
//        }
//        return null;
//    }
//
//    public boolean replyPost(Long postingId, String author, String text) {
//        if (postingId > 0 && !text.isEmpty() && !author.isEmpty()) {
//            for (PostingDTO post : postings) {
//                if (post.getId().equals(postingId)) {
//                    post.addComment(author, text);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public List<PostingDTO> getAllPostings() {
//        return this.postings;
//    }
//
//    public void setPostings(List<PostingDTO> postings) {
//        this.postings = postings;
//    }
//
//    public PostingDTO getPost(Long postingId) {
//        if (postingId > 0) {
//            for (PostingDTO post : postings) {
//                if (post.getId() == postingId) {
//                    return post;
//                }
//            }
//        }
//        return null;
//    }
//
//    public boolean deletePost(Long postingId) {
//        if (postingId > 0) {
//            for (PostingDTO post : postings) {
//                if (post.getId() == postingId) {
//                    postings.remove(post);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public UserDetailsDTO getUserDetails() {
//        return this.userDetails;
//    }
//
//    public void setUserDetails(UserDetailsDTO userDetails) {
//        this.userDetails = userDetails;
//    }
//
//}
