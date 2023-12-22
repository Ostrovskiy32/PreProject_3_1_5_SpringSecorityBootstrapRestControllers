package ru.demo.serialization;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ru.demo.model.Role;
import ru.demo.model.User;

import java.io.IOException;

public class CustomUserSerializer extends StdSerializer<User> {

    public CustomUserSerializer() {
        super(User.class);
    }


    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("userId", user.getUserId());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeStringField("surname", user.getSurname());
        jsonGenerator.writeNumberField("age", user.getAge());
        jsonGenerator.writeStringField("citizenship", user.getCitizenship());
        jsonGenerator.writeStringField("username", user.getUsername());

        jsonGenerator.writeFieldName("roles");
        if (user.getRoles() != null) {
            jsonGenerator.writeStartArray();

            for (Role role : user.getRoles()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("roleName", role.getRoleName());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndObject();
    }
}
