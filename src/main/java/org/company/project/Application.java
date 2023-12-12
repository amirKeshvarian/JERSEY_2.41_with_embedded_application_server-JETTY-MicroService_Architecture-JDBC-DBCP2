package org.company.project;

import org.company.project.common.annotation.MicroServiceRunner;
import org.company.project.common.annotation.RestConfiguration;
import org.company.project.common.config.MicroService;

@MicroServiceRunner(basePackage = "org.company.project", port = 9090)
@RestConfiguration(prefix = "/api")
public class Application {
    public static void main(String[] args) {
        MicroService.run(Application.class);
    }
}
