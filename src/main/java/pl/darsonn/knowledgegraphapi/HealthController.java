package pl.darsonn.knowledgegraphapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
class HealthController {
    @GetMapping()
    HttpStatus returnHealthy() {
        return HttpStatus.OK;
    }
}
