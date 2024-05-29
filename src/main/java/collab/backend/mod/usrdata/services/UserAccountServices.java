package collab.backend.mod.usrdata.services;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import collab.backend.mod.usrdata.model.Url;
import collab.backend.mod.usrdata.model.UserAccount;
import collab.backend.mod.usrdata.repository.UrlRepository;
import collab.backend.mod.usrdata.repository.UserAccountRepository;

@Service
public class UserAccountServices {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UrlRepository urlRepository;

    public String getRankByUsername(String username) {
        Optional<String> rank = userAccountRepository.getRankByUsername(username);

        if (rank.isEmpty())
            throw new UsernameNotFoundException("Username not found: "+ username);
        
        return rank.get();
    }

    public String getPointsByUsername(String username) {
        Optional<String> points = userAccountRepository.getPointsByUsername(username);

        if (points.isEmpty())
            throw new RuntimeException(username+" hasn't points.");

        return points.get();
    }

    public String getAllDataUser(String username) {
        UserAccount user = userAccountRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));;

        String rank = userAccountRepository.getRankByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No rank found for username: " + username));;

        StringBuilder strBuilder = new StringBuilder();

        String[] urls = urlRepository.findLinksById(user.getId());

        // function foreach is a void
        String result = Arrays.stream(urls)
        .collect(Collectors.joining(","));

        strBuilder.append(
            user.getBio()+","+result+","+rank+","+user.getPoints()
        );

        return strBuilder.toString();
    }

    public void editBio(String bio, String username) {
        UserAccount user = userAccountRepository.findByUsername(username).get();

        userAccountRepository.updateBio(bio, user.getId());
    }

    public void addUrls(String link, String username) {
        UserAccount user = userAccountRepository.findByUsername(username).get();
        
        int id = user.getId();

        urlRepository.addNewUrl(link, id);
    }

    public String allUsers() {
        String[] users = userAccountRepository.allUsers();

        return Arrays.stream(users).collect(Collectors.joining(","));
    }
}
