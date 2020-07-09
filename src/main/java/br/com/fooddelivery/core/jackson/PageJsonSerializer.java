package br.com.fooddelivery.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(
            Page page,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectField("content", page.getContent());
        jsonGenerator.writeNumberField("previous_page", page.hasPrevious() ? page.getNumber() - 1 : -1);
        jsonGenerator.writeNumberField("page_current", page.getNumber());
        jsonGenerator.writeNumberField("next_page", page.hasNext() ? page.getNumber() + 1 : -1);
        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("total_pages", page.getTotalPages());
        jsonGenerator.writeNumberField("total_elements", page.getTotalElements());

        jsonGenerator.writeEndObject();
    }
}
