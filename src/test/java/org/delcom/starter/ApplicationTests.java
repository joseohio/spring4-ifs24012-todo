package org.delcom.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

class ApplicationTest {

    @Test
    void mainMethod_ShouldRunSpringApplication() {
        // Buat SpringApplication tanpa web environment supaya tidak start server
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);

        ConfigurableApplicationContext ctx = null;
        try {
            ctx = app.run();               // start (non-blocking)
            // jika mau: assert bahwa context aktif
            assert ctx.isActive();
        } finally {
            if (ctx != null) {
                SpringApplication.exit(ctx); // shutdown segera
            }
        }
    }
}