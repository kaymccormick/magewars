#!/usr/bin/groovy
import java.nio.file.Files
import java.nio.file.Paths
import groovy.io.FileType

dir = Paths.get(this.args[0])
outfile = Paths.get(this.args[1]).toAbsolutePath()
title = this.args[2]

new File(outfile.toString()).withWriter('utf-8') { writer ->
    writer.writeLine "<html><head><title>$title</title></head><body><h1>$title</h1>"
    writer.writeLine "<table>"

    dir.eachFileRecurse(FileType.FILES) { file ->
        rel = dir.relativize(file)

        if (file.compareTo(outfile) != 0) {
            size = Files.size(file)
            writer.writeLine "<tr><td><a href='$rel'>$rel</a></td><td>$size</td></tr>"
        }
    }
    writer.writeLine "</table></body></html>"
}
