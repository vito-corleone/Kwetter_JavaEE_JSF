/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.Posting;
import Model.User;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "userAuthBean")
@SessionScoped
public class UserAuthBean implements Serializable {

    public UserAuthBean() {

    }

    @Inject
    private UserService userService;

    @Inject
    private PostingService postingService;

    private String emailAddress;

    private String newPosting;

    private String searchKeyword;

    // user object
    private User user;
    
    private List<Posting> searchResults;

    HttpSession session;

// METHOD BENEATH IS FOR AUTHENTICATED SESSIONS
    public User getAuthUser() {
        if (user != null) {
            return user;
        }
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            this.user = userService.find(principal.getName()); // Find User by j_username.  
            return user;
        }
        return null;
    }

    // USER INFO
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        if (user != null) {
            return user.getEmailAddress();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getEmailAddress();
            }
        }
        return "No emailaddress found";
    }

    // PROFILE DETAILS
    public String getName() {
        if (user != null) {
            return user.getName();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getName();
            }
        }
        return "No name found";
    }

    public void setName(String name) {
        if (getAuthUser() != null) {
            user.setName(name);
            userService.edit(user);
        }
    }

    public String getPhotoPath() {
        if (user != null) {
            return user.getPhotoPath();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getPhotoPath();
            }
        }
        return "http://www.denieuwereporter.nl/wp-content/uploads/2015/06/twitter.jpg";
    }

    public void setPhotoPath(String path) {
        if (getAuthUser() != null) {
            user.setPhotoPath(path);
            userService.edit(user);
        }
    }

    // USER DETAILS
    public String getUserBio() {
        if (user != null) {
            return user.getBio();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getBio();
            }
        }
        return "No bio found";
    }

    public void setUserBio(String bio) {
        if (getAuthUser() != null) {
            user.setBio(bio);
            userService.edit(user);
        }
    }

    public String getUserLocation() {
        if (user != null) {
            return user.getLocation();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getLocation();
            }
        }
        return "No location found";
    }

    public void setUserLocation(String location) {
        if (getAuthUser() != null) {
            user.setLocation(location);
            userService.edit(user);
        }
    }

    public String getUserWebsite() {
        if (user != null) {
            return user.getWebsite();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getWebsite();
            }
        }
        return "No website found";
    }

    public void setUserWebsite(String website) {
        if (getAuthUser() != null) {
            user.setWebsite(website);
            userService.edit(user);
        }
    }

    public void saveUser() {
        userService.edit(this.user);
    }

    // Followers
    public List<User> getPeopleIFollow() {
        if (user != null) {
            return user.getThePeopleThatIFollow();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getThePeopleThatIFollow();
            }
        }
        return new ArrayList<>();
    }

    public List<User> getPeopleThatFollowMe() {
        if (user != null) {
            return user.getThePeopleThatFollowMe();
        } else if (user == null) {
            if (getAuthUser() != null) {
                return user.getThePeopleThatFollowMe();
            }
        }
        return new ArrayList<>();
    }

    public List<String> getTrends() {
        List<String> trends = new ArrayList<>();
        trends.add("#YoLo");
        trends.add("#Blind");
        trends.add("#NoWAaaay");
        trends.add("#Awesome man");
        trends.add("#Hello Kitty");
        trends.add("#neverever_again");
        trends.add("#earthHour");
        trends.add("#JavaSF");
        trends.add("#StarWars");
        return trends;
    }

    public List<Posting> getStartPagePostings() {
        List<Posting> startpage;
        if (user != null) {
            startpage = postingService.find(user.getEmailAddress());
            for (User user : user.getThePeopleThatIFollow()) {
                for (Posting post : postingService.find(user.getEmailAddress())) {
                    startpage.add(post);
                }
            }
            Collections.sort(startpage, Collections.reverseOrder());
            return startpage;
        } else if (user == null) {
            if (getAuthUser() != null) {
                startpage = postingService.find(user.getEmailAddress());
                for (User user : user.getThePeopleThatIFollow()) {
                    for (Posting post : postingService.find(user.getEmailAddress())) {
                        startpage.add(post);
                    }
                }
                Collections.sort(startpage);
                return startpage;
            }
        }
        return new ArrayList<>();
    }

    public String getNewPosting() {
        return newPosting;
    }

    public void setNewPosting(String newPosting) {
        this.newPosting = newPosting;
    }

    public void createPosting() {
        if (getAuthUser() != null) {
            Posting post = new Posting(this.user.getEmailAddress(), newPosting);
            postingService.create(post);
            this.newPosting = "";
        }
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public List<Posting> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Posting> searchResults) {
        this.searchResults = searchResults;
    }
    

    public List<Posting> searchPosting() {
        if (!this.searchKeyword.isEmpty()) {
            List<Posting> searchResult = postingService.searchPosting(searchKeyword);
            Collections.sort(searchResult, Collections.reverseOrder()); 
            this.searchResults = searchResult;
            return searchResults;
        }
        return new ArrayList<>();
    }
}
