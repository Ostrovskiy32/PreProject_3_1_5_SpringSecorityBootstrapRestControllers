package ru.demo.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import ru.demo.model.Role;
import ru.demo.model.User;
import ru.demo.services.RoleServices;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDeserializer extends JsonDeserializer<User> {

    private final RoleServices roleServices;

    public CustomUserDeserializer(RoleServices roleService) {
        this.roleServices = roleService;
    }


    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        User user = new User();

        JsonNode userIdNode = node.get("userId");
        if (userIdNode != null) {
            user.setUserId(userIdNode.asLong());
        }

        JsonNode nameNode = node.get("name");
        if (nameNode != null) {
            user.setName(nameNode.asText());
        }

        JsonNode surnameNode = node.get("surname");
        if (surnameNode != null) {
            user.setSurname(surnameNode.asText());
        }

        JsonNode ageNode = node.get("age");
        if (ageNode != null) {
            user.setAge((byte)ageNode.asInt());
        }

        JsonNode citizenshipNode = node.get("citizenship");
        if (citizenshipNode != null) {
            user.setCitizenship(citizenshipNode.asText());
        }

        JsonNode usernameNode = node.get("username");
        if (usernameNode != null) {
            user.setUsername(usernameNode.asText());
        }

        JsonNode passwordNode = node.get("password");
        if (passwordNode != null) {
            user.setPassword(passwordNode.asText());
        }

        Set<Role> roleSet = new HashSet<>();
        if (node.has("roles")) {
            for (JsonNode roleNodes : node.get("roles")) {
                Long roleId = roleNodes.asLong();
                Role role = roleServices.getById(roleId);
                if (role != null) {
                    roleSet.add(role);
                }
            }
        }
        user.setRoles(roleSet);

        return user;
    }
}
