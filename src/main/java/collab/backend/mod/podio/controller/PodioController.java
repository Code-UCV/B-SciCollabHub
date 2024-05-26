package collab.backend.mod.podio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.podio.services.PodioServices;

@RestController
@RequestMapping("/q")
public class PodioController {
    @Autowired
    private PodioServices podioServices;

    @GetMapping("/rankingpositions")
    public ResponseEntity<String> sendRanks() {
        String str = podioServices.sendRankingUsers();
        
        if (str.isEmpty()) {
            return ResponseEntity.badRequest().body("BLANK");
        }

        return ResponseEntity.ok(str);
    }

}
