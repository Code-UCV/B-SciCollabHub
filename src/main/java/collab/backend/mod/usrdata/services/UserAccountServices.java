package collab.backend.mod.usrdata.services;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import collab.backend.mod.login.model.User;
import collab.backend.mod.login.repository.UserRepository;
import collab.backend.mod.usrdata.model.UserAccount;
import collab.backend.mod.usrdata.repository.UrlRepository;
import collab.backend.mod.usrdata.repository.UserAccountRepository;

@Service
public class UserAccountServices {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlRepository urlRepository;

    public String getRankByUsername(String username) {
        Optional<String> rank = userAccountRepository.getRankByUsername(username);

        if (rank.isEmpty())
            throw new UsernameNotFoundException("Username not found: "+ username);
        
        return rank.get();
    }

    public String getPointsByUsername(String username) {
        Optional<Integer> points = userAccountRepository.getPointsByUsername(username);

        if (points.isEmpty())
            throw new RuntimeException(username+" hasn't points.");

        return String.valueOf(points.get());
    }

    public String getAllDataByUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();

        String rank = userAccountRepository.getRankByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No rank found for username: " + username));

        StringBuilder strBuilder = new StringBuilder();

        int points = userAccountRepository.getPointsByUsername(user.getUsername()).get();

        strBuilder.append(
            userAccount.getBio()+"¬"+rank+"¬"+points
        );

        return strBuilder.toString();
    }

    public String showAllUrls(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();

        String[] urls = urlRepository.findLinksById(userAccount.getId());

        String result = Arrays.stream(urls)
        .collect(Collectors.joining(","));

        return result;
        
    }

    public String getPointsAndCategoryByUsername(String username) {
        User user = userRepository.findByUsername(username) 
            .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();

        StringJoiner rows = new StringJoiner("-");

        for(String[] items : userAccountRepository.getPointsAndCategoryById(userAccount.getId())) {
            StringJoiner values = new StringJoiner(",");
            for(String str : items ) {
                values.add(str);
            }
            rows.add(String.valueOf(values));
        }

        return String.valueOf(rows);
    }

    public void editBio(String bio, String username) {
        User user =  userRepository.findByUsername(username).get();
        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();

        userAccountRepository.updateBio(bio, userAccount.getId());
    }

    public void addUrls(String link, String username) {
        User user = userRepository.findByUsername(username).get();
        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();
        
        int id = userAccount.getId();

        urlRepository.addNewUrl(link, id);
    }

    public String allUsers() {
        String[] users = userAccountRepository.allUsers();

        return Arrays.stream(users).collect(Collectors.joining(","));
    }

    public String getExercisesSolvedByUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(
                () -> {
                    throw new RuntimeException("User not found");
                }
            );

        UserAccount userAccount = userAccountRepository.findById(Integer.parseInt(user.getId())).get();
        
        int userId = userAccount.getId();
        
        StringJoiner rows = new StringJoiner("-");

        for (String[] row : userAccountRepository.getExercisesSolvedByUser(userId)) {
            StringJoiner values = new StringJoiner(",");
            for(String item : row ) {
                values.add(item);
            }
            rows.add(String.valueOf(values));
        }

        return String.valueOf(rows);
    }
}
