package setup;

import java.net.URL;

public class FileUtil {

    public static String getPathRelativeTo(String filename, Class<?> clazz) {
        String path = clazz.getPackage().getName().replace('.', '/') + "/" + filename;
        URL url = clazz.getClassLoader().getResource(path);
        return url.getFile();
    }

}
