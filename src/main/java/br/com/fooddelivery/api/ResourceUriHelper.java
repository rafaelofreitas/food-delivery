package br.com.fooddelivery.api;

import lombok.experimental.UtilityClass;
import org.apache.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

@UtilityClass
public class ResourceUriHelper {
    public void addUriInResponseHeader(Object resourceId) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        if (response != null) {
            response.setHeader(HttpHeaders.LOCATION, uri.toString());
        }
    }
}
