package de.intranda.goobi.plugins.size_validation;

import com.google.gson.Gson;

import de.intranda.goobi.plugins.SizeValidationPlugin;
import lombok.extern.log4j.Log4j;
import spark.Route;

@Log4j
public class Handlers {
    private static String title = SizeValidationPlugin.TITLE;
    private static Gson gson = new Gson();

    public static Route save = (req, res) -> {
    	return "";
    };
}
