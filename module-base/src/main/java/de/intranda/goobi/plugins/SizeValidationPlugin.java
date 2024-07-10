package de.intranda.goobi.plugins;


import de.intranda.goobi.plugins.size_validation.Routes;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.goobi.beans.Step;
import org.goobi.production.enums.PluginGuiType;
import org.goobi.production.enums.PluginType;
import org.goobi.production.enums.StepReturnValue;
import org.goobi.production.plugin.interfaces.IGuiPlugin;
import org.goobi.production.plugin.interfaces.IRestPlugin;
import org.goobi.production.plugin.interfaces.IStepPlugin;
import spark.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

@Data
@PluginImplementation
@Log4j
public class SizeValidationPlugin implements IGuiPlugin, IStepPlugin, IRestPlugin {
    private Step step;
    private String returnPath;
    public static String TITLE = "intranda_step_image-size-validation";

    @Override
    public String cancel() {
        return "/uii/" + returnPath;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public String finish() {
        return "/uii/" + returnPath;
    }

    @Override
    public String getPagePath() {
        return "/uii/guiPluginNew.xhtml";
    }

    @Override
    public PluginGuiType getPluginGuiType() {
        // TODO Auto-generated method stub
        return PluginGuiType.FULL;
    }

    @Override
    public void initialize(Step step, String returnPath) {
        log.info(returnPath);
        this.step = step;
        this.returnPath = returnPath;

    }

    @Override
    public HashMap<String, StepReturnValue> validate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginType getType() {
        return PluginType.Step;
    }

    @Override
    public String[] getJsPaths() {
        return new String[] { "riot.min.js", "q.js", "tags.js", "app.js"};
    }

    @Override
    public void initRoutes(Service http) {
        Routes.initRoutes(http);
    }
    
    @Override
    public String getTitle() {
    	return TITLE;
    }

}
