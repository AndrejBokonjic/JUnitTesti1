package papyrus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class PapyrusApplication {
	public static void main(String[] args) {
		SpringApplication.run(PapyrusApplication.class, args);
	}
}
