package org.fileserver.repository
import org.fileserver.domain.File
import org.fileserver.domain.Metadata
import org.springframework.http.MediaType
import spock.lang.Specification

class FileSystemRepositoryIOExceptionTest extends Specification {

    def repository
    def fileSystemInputOutputMock
    def folder = '/tmp/fileserver/tests-documents-spock'
    def aTestFile
    def aTestFileContent = "A nice test file content"

    void setup () {
        fileSystemInputOutputMock = Mock(FileSystemIO)
        repository = new FileSystemRepositoryImpl(folder, fileSystemInputOutputMock)

        def givenFileName = "my_document_1.txt"
        aTestFile = new File()
        aTestFile.id = "402880834e3005a5014e3005b16d0000"
        aTestFile.bytes = aTestFileContent.getBytes()

        def metadata = new Metadata()
        metadata.fileName = givenFileName
        metadata.mediaType = MediaType.APPLICATION_ATOM_XML
        aTestFile.metadata = metadata
    }

    def "Should create base folder directory" () {
        when:
        repository.init()

        then:
        1 * fileSystemInputOutputMock.createDirectory(folder)
    }

    def "Should throw IOException when save file" () {
        when:
        repository.save(aTestFile)

        then:
        1 * fileSystemInputOutputMock.save(aTestFileContent.getBytes(), folder, aTestFile.id) >> { throw new IOException() }
        thrown(IOException)
    }

    def "Should throw IOException when load file" () {
        given:
        File fileToLoad = new File()
        fileToLoad.id = aTestFile.id

        when:
        repository.load(fileToLoad)

        then:
        1 * fileSystemInputOutputMock.read(folder, fileToLoad.id) >> { throw new IOException() }
        thrown(IOException)
    }

    def "Should throw IOException when delete file" () {
        given:
        File fileToDelete = new File()
        fileToDelete.id = aTestFile.id

        when:
        repository.delete(fileToDelete)

        then:
        1 * fileSystemInputOutputMock.delete(folder, fileToDelete.id) >> { throw new IOException() }
        thrown(IOException)
    }

}
