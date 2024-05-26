package collab.backend.mod.podio.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collab.backend.mod.podio.repository.PodioRepository;

@Service
public class PodioServices {
    @Autowired
    private PodioRepository cuentaUsuarioRepository;

    public String sendRankingUsers() {
        List<String[]> rankList = cuentaUsuarioRepository.rankingUsers();

        if (rankList.isEmpty()) { 
            throw new RuntimeException("List of ranking is empty");
        };

        String ranks = rankList.stream()
        .map(rank_ -> rank_[0]+"-"+rank_[1])
        .collect(Collectors.joining(","));
        
        return ranks;
    }
}
