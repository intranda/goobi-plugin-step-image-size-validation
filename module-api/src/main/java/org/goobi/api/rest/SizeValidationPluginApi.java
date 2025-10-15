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

        // Get the full base URI including scheme, host, and port
        String baseUri = uriInfo.getBaseUri().toString();
        // Construct the full URL with the correct path: /api/process/image/{processId}/media/
        String baseUrl = baseUri + "process/image/" + p.getId() + "/media/";

        try (Stream<java.nio.file.Path> imagePaths = Files.list(mediaFolder)) {
            List<URI> images = imagePaths.map(path -> {
                String fileName = path.getFileName().toString();
                // Remove file extension for IIIF compatibility
                String fileNameWithoutExt = fileName.contains(".") ?
                    fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
                return URI.create(baseUrl + fileNameWithoutExt);
            })
                    .sorted()
                    .collect(Collectors.toList());

            return images;
        }
    }
}
