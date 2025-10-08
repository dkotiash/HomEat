import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoEntryController {

    @GetMapping("/todos")
    public String getTodos(){
        return "Helloo World!";

    }
}
