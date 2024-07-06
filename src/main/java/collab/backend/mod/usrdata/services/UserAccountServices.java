package collab.backend.mod.usrdata.services;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        String rank = userAccountRepository.getRankByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No rank found for username: " + username));

        StringBuilder strBuilder = new StringBuilder();


        strBuilder.append(
            user.getBio()+"¬"+rank+"¬"+user.getPoints()
        );

        return strBuilder.toString();
    }

    public String showAllUrls(String username) {
        UserAccount user = userAccountRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        String[] urls = urlRepository.findLinksById(user.getId());

        String result = Arrays.stream(urls)
        .collect(Collectors.joining(","));

        return result;
        
    }

    public String getPointsAndCategoryByUsername(String username) {
        UserAccount user = userAccountRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user found with username: " + username));

        StringJoiner rows = new StringJoiner("-");

        for(String[] items : userAccountRepository.getPointsAndCategoryById(user.getId())) {
            StringJoiner values = new StringJoiner(",");
            for(String str : items ) {
                values.add(str);
            }
            rows.add(String.valueOf(values));
        }

        return String.valueOf(rows);
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

    public String getExercisesSolvedByUser(String username) {
        UserAccount user = userAccountRepository.findByUsername(username)
            .orElseThrow(
                () -> {
                    throw new RuntimeException("User not found");
                }
            );
        
        int userId = user.getId();
        
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
