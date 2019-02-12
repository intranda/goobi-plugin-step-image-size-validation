package de.intranda.goobi.plugins.size_validation;

import com.google.gson.Gson;

import spark.Service;

public class Routes {
    private static Gson gson = new Gson();

    public static void initRoutes(Service http) {
        http.path("/isv", () -> {
            http.get("/process/:processid/media/list", Handlers.mediaList, gson::toJson);
            http.get("/healthcheck", Handlers.healthcheck);
        });
    }
}
