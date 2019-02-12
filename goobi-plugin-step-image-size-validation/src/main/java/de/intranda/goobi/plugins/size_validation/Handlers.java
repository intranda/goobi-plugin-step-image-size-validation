package de.intranda.goobi.plugins.size_validation;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.goobi.beans.Process;

import com.google.gson.Gson;

import de.intranda.goobi.plugins.SizeValidationPlugin;
import de.sub.goobi.persistence.managers.ProcessManager;
import lombok.extern.log4j.Log4j;
import spark.Route;

@Log4j
public class Handlers {
    private static String title = SizeValidationPlugin.TITLE;
    private static Gson gson = new Gson();

    public static Route mediaList = (req, res) -> {
    	Process p = ProcessManager.getProcessById(Integer.parseInt(req.params("processid")));
        Path mediaFolder = Paths.get(p.getImagesTifDirectory(false));
        
        HttpServletRequest request = req.raw();
        String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().indexOf("plugins/")) +  "api/image/" + p.getId() + "/media/";
        
        try(Stream<Path> imagePaths = Files.list(mediaFolder)) {
    		List<URI> images = imagePaths.map(path -> URI.create(baseUrl + path.getFileName().toString()))
    		.sorted().collect(Collectors.toList());
    		
    		String json = "[" + StringUtils.join(images, ",") + "]";
    		res.header("content-type", MediaType.APPLICATION_JSON);
//    		OutputStream os = res.raw().getOutputStream();
//    		os.write(json.getBytes(Charset.forName("utf-8")));
    		return images;
    	}

//        return "";
    };
    
    public static Route healthcheck = (req, res) -> {
    	return "Plugin api is working";
    };

}
