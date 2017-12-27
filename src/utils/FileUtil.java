package utils;
import java.io.*;


public class FileUtil {

    public static boolean createFolder(String path) {
        try {
            File dir = new File(path);
            return dir.mkdirs();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean createUserFolder(String username) {
        return FileUtil.createFolder("users/" + username + "/files");
    }

    public static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
