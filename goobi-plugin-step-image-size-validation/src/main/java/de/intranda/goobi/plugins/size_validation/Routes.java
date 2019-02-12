package de.intranda.goobi.plugins.size_validation;

import com.google.gson.Gson;

import spark.Service;

public class Routes {
    private static Gson gson = new Gson();

    public static void initRoutes(Service http) {
        http.path("/image-size-validation", () -> {
            http.post("/process/:processid/save", Handlers.save);
        });
    }
}
