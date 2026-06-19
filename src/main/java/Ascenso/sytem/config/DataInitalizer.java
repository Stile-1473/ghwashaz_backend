package Ascenso.sytem.config;

import Ascenso.sytem.user.service.DataIntializerServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitalizer implements CommandLineRunner {

    public  final DataIntializerServiceContract initalizerService;

    @Override
    public void run(String... args) throws Exception {
         initalizerService.intialize();
    }
}
