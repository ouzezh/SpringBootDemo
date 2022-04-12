package com.ozz.springboot.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.google.common.collect.Lists;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class MyProjectUpgrade {
    public static void main(String[] args) throws IOException {
        System.out.println("--start--");
        Set<String> ps = new TreeSet<>();

        upgradeProject(ps, MyProjectUpgrade::upgradeGradle);

        ps.stream().forEach(System.out::println);
        System.out.println("--end--");
    }

    private static void upgradeProject(Set<String> ps, Function<Path, Boolean> fun) throws IOException {
        Path start = Paths.get(getProjectPath()).getParent();
        Files.walk(start, 2).filter(Files::isDirectory).forEach(p -> {
            Boolean flag = fun.apply(p);
            if (flag) {
                ps.add(p.toString());
            }
        });
    }

    private static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    private static boolean upgradeGradle(Path path) {
        List<String> list = Lists.newArrayList("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.jar");
        String root = getProjectPath();

        boolean flag = false;
        for (String s : list) {
            Path src = Paths.get(root, s);
            Path dest = Paths.get(path.toString(), s);
            if (Files.exists(src) && Files.exists(dest) && !digest(src).equals(digest(dest))) {
                flag = true;
                try (InputStream in = Files.newInputStream(src); OutputStream out = Files.newOutputStream(dest)) {
                    IOUtils.copy(in, out);
                } catch (Exception e) {
                    ExceptionUtil.wrapRuntime(e);
                }
            }
        }
        return flag;
    }

    public static String digest(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            return DigestUtils.md5DigestAsHex(input);
        } catch (Exception e) {
            ExceptionUtil.wrapRuntime(e);
        }
        return null;
    }

}
