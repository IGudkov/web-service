package ru.courses.innotech.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.courses.innotech.api.configuration.properties.SwaggerProperties;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

  private final SwaggerProperties properties;

  @Bean
  public OpenAPI openApi() {
    License vtb = new License()
        .name(properties.getLicense());

    SwaggerProperties.SwaggerContactData contactData = properties.getContact();
    Contact contact = new Contact()
        .name(contactData.getName())
        .email(contactData.getEmail())
        .url(contactData.getUrl());

    Info info = new Info()
        .title(properties.getTitle())
        .version(properties.getVersion())
        .description(properties.getDescription())
        .license(vtb)
        .contact(contact);

    List<SwaggerProperties.SwaggerServerData> serversData = properties.getServers();
    List<Server> servers = serversData.stream()
        .map(serverData -> new Server()
            .url(serverData.getUrl())
            .description(serverData.getDescription())
        )
        .toList();

    return new OpenAPI()
        .info(info)
        .servers(servers);
  }

}
