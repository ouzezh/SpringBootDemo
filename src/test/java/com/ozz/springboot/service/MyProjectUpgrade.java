package com.ozz.springboot.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.log.StaticLog;
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

public class MyProjectUpgrade {
    public static void main(String[] args) throws IOException {
        StaticLog.info("--start--");
        Set<String> ps = new TreeSet<>();

        upgradeProject(ps);

        ps.stream().forEach(System.out::println);
        StaticLog.info("--end--");
    }

    private static void upgradeProject(Set<String> ps) throws IOException {
        Path start = Paths.get(getProjectPath()).getParent();
        Files.walk(start, 2).filter(Files::isDirectory).forEach(p -> {
            Boolean flag = upgradeGradle(p);
            if (flag) {
                ps.add(p.toString());
            }
        });
    }

    private static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    private static boolean upgradeGradle(Path path) {
        List<String> list = ListUtil.toList("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.jar");
        String root = getProjectPath();

        boolean flag = false;
        for (String s : list) {
            Path src = Paths.get(root, s);
            Path dest = Paths.get(path.toString(), s);
            if (Files.exists(src) && Files.exists(dest) && !digest(src).equals(digest(dest))) {
                flag = true;
                try (InputStream in = Files.newInputStream(src); OutputStream out = Files.newOutputStream(dest)) {
                    IoUtil.copy(in, out);
                } catch (Exception e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            }
        }
        return flag;
    }

    public static String digest(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            return DigestUtils.md5DigestAsHex(input);
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return null;
    }

}
