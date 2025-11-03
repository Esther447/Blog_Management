package com.example.managementblog.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Blog Management API",
                description = "API allows users to make blog posts and comment on them.",
                contact = @Contact(
                        name = "Esther447",
                        url = "https://github.com/Esther447/Blog_Management"
                ),
                version = "1.0.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/"
                )
        }
)
public class OpenApiConfig {}
