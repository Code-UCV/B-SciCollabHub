package collab.backend.mod.usrdata.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import collab.backend.mod.usrdata.repository.UserAccountRepository;

@Service
public class UserAccountServices {
    @Autowired
    private UserAccountRepository userAccountRepository;

    public String getRankByUsername(String username) {
        Optional<String> rank = userAccountRepository.getRankByUsername(username);

        if (rank.isEmpty())
            throw new UsernameNotFoundException("Username not found: "+ username);
        return rank.get();
    }
}
