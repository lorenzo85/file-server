package org.fileserver.repository
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.fileserver.BaseSpecification
import org.fileserver.domain.File
import org.fileserver.domain.Metadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType

import static java.io.File.separator

class FileSystemRepositoryTest extends BaseSpecification {

    @Autowired FileSystemRepository repository
    @Value('${file.folder}') String fileFolder

    def aTestFile
    def aTestFileContent = "A nice file content"

    void setup() {
        new java.io.File(fileFolder).mkdirs()

        def givenFileName = "my_document_1.txt"
        aTestFile = new File()
        aTestFile.id = "402880834e3005a5014e3005b16d0000"
        aTestFile.bytes = aTestFileContent.getBytes()

        def metadata = new Metadata()
        metadata.fileName = givenFileName
        metadata.mediaType = MediaType.APPLICATION_ATOM_XML
        aTestFile.metadata = metadata
    }

    void cleanup() {
        java.io.File dirToBeDeleted = new java.io.File(fileFolder)
        FileUtils.deleteDirectory(dirToBeDeleted)
    }

    def "Should correctly save the file with the correct content" () {
        when:
        repository.save(aTestFile)
        def savedFilePath = fileFolder.concat(separator).concat(aTestFile.id)
        def savedFileContent = new java.io.File(savedFilePath).text

        then:
        savedFileContent.equals(aTestFileContent)
    }

    def "Should correctly delete the file" () {
        given:
        repository.save(aTestFile)

        when:
        repository.delete(aTestFile)

        then:
        fileNotFound(aTestFile.id)
    }

    def "Should correctly load file data" () {
        given:
        repository.save(aTestFile)
        File fileToLoad = new File()
        fileToLoad.id = aTestFile.id

        when:
        repository.load(fileToLoad)

        then:
        fileToLoad.bytes == aTestFileContent.getBytes()
    }

    def fileNotFound(fileName) {
        return new java.io.File(fileFolder)
                .list({dir, name -> name.equals(fileName) as FilenameFilter})
                .size() == 0;
    }
}
