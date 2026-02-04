package pl.darsonn.knowledgegraphapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/health")
class HealthController {
    @GetMapping
    ResponseEntity<Void> returnHealthy() {
        return ResponseEntity.ok().build();
    }
}