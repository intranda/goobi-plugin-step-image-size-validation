package org.goobi.api.rest;

import de.sub.goobi.helper.exceptions.SwapException;
import de.sub.goobi.persistence.managers.ProcessManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.goobi.beans.Process;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Path("/plugins/isv")
public class SizeValidationPluginApi {
    @GET
    @Path("/process/{processid}/media/list")
    public List<URI> mediaList(@PathParam("processid") int processid, @Context UriInfo uriInfo) throws SwapException, IOException {
        Process p = ProcessManager.getProcessById(processid);
        java.nio.file.Path mediaFolder = Paths.get(p.getImagesTifDirectory(false));

//        String baseUrl = request.getMethod().substring(0, request.getRequestURI().indexOf("plugins/")) + "api/image/" + p.getId() + "/media/";
        String baseUrl = uriInfo.getPath().substring(0, uriInfo.getPath().indexOf("plugins/")) + "api/image/" + p.getId() + "/media/";

        try (Stream<java.nio.file.Path> imagePaths = Files.list(mediaFolder)) {
            List<URI> images = imagePaths.map(path -> URI.create(baseUrl + path.getFileName().toString()))
                    .sorted()
                    .collect(Collectors.toList());

            return images;
        }
    }
}
