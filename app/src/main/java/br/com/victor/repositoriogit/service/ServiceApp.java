package br.com.victor.repositoriogit.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import br.com.victor.repositoriogit.model.Repository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Victor Oliveira on 20/08/18.
 */
public class ServiceApp {

    private static ServiceApp serviceApp;

    private static ObjectMapper objectMapper = null;
    // URL base dos serviços.
    private String URL_BASE = "https://api.github.com/users/%s/repos";

    public static ServiceApp getInstance() {
        if (serviceApp == null) {
            serviceApp = new ServiceApp();
        }

        return serviceApp;
    }

    public List<Repository> getListRepository(String userName) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = URL_BASE.replace("%s", userName);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            JavaType type = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Repository.class);
            return getObjectMapperInstance().readValue(response.body().string(), type);
        } else {
            throw new Exception(mistake(response.code()));
        }
    }

    private String mistake(int code) {
        switch (code) {
            case 404:
                return "User not found. Please enter another name";
            default:
                return "A network error has occurred. Check your Internet connection and try again later.";
        }
    }

    private static ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }
}
