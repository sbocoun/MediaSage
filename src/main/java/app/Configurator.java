package app;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Loads the relevant api keys from file.
 */
public class Configurator {
    private final String tasteDiveApiKey;
    private final String tmdbApiKey;

    public Configurator() {
        final LoaderOptions options = new LoaderOptions();
        final Yaml yaml = new Yaml(options);
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("apikeys.yaml");
        final Map<String, String> apikeys = yaml.loadAs(inputStream, Map.class);
        this.tasteDiveApiKey = apikeys.get("tastedive");
        this.tmdbApiKey = apikeys.get("tmdb");
    }

    public String getTasteDiveApiKey() {
        return tasteDiveApiKey;
    }

    public String getTmdbApiKey() {
        return tmdbApiKey;
    }
}
