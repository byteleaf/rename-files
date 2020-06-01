package de.byteleaf.renamefiles.control.service

import org.springframework.stereotype.Service

@Service
class PathLocationService {

    /**
     * Is returning the base path
     * Java project root in development and the folder of the jar file in production
     */
    public fun getBasePath(): String {
        var path: String = PathLocationService::class.java.getProtectionDomain().getCodeSource().getLocation().getPath()
        path = path.replace("%20".toRegex(), " ")
        if (path.endsWith(".jar")) {
            var index = path.lastIndexOf("/")
            if (index == -1) {
                index = path.lastIndexOf("\\")
            }
            if (index == -1) {
                //throw GeneralException("Can't find application base path [$path]")
            }
            path = path.substring(0, index + 1)
        } else if (path.endsWith("/bin/")) {
            path = path.substring(0, path.length - 4)
        }
        return path
    }
}