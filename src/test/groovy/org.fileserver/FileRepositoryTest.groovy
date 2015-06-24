package org.fileserver

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import static java.io.File.separator
import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory
import static DocumentMetadata.METADATA_EXT

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [Application.class])
@TestPropertySource("classpath:test.properties")
class FileRepositoryTest extends Specification {

    @Autowired FileRepository repository
    @Value('${file.folder}') String fileFolder

    void setup() {
        repository.init();
    }

    void cleanup() {
        File dirToBeDeleted = new File(fileFolder)
        deleteDirectory(dirToBeDeleted)
    }

    def "Should correctly save the document file with the correct content"() {
        given:
        def givenDocumentContent = "A nice document content 1"
        def givenFileName = "my_document_1.txt"
        def givenDocument = new Document(givenDocumentContent.bytes, givenFileName)

        when:
        repository.save(givenDocument)
        def savedDocumentPath = fileFolder.concat(separator).concat(givenFileName)
        def savedDocumentContent = new File(savedDocumentPath).text

        then:
        savedDocumentContent.equals(givenDocumentContent)
    }

    def "Should correctly save the metadata file"() {
        given:
        def givenDocumentContent = "A nice document content 2"
        def givenFileName = "my_document_2.txt"
        def givenDocument = new Document(givenDocumentContent.bytes, givenFileName)

        when:
        repository.save(givenDocument)
        def savedDocumentMetadataPath = fileFolder.concat(separator).concat(givenFileName).concat(METADATA_EXT)
        def savedDocumentMetadata = new File(savedDocumentMetadataPath)

        then:
        savedDocumentMetadata.exists()
    }

    def "Should correctly find all metadata files"() {
        given:
        def givenFileName3 = "my_document_3.txt"
        def givenFileName4 = "my_document_4.txt"
        repository.save(new Document("Document 3".bytes, givenFileName3))
        repository.save(new Document("Document 4".bytes, givenFileName4))

        when:
        def metaDatas = repository.findAll()

        then:
        metaDatas.size() == 2
        findOne(metaDatas, givenFileName3)
        findOne(metaDatas, givenFileName4)
    }

    def boolean findOne(List<DocumentMetadata> metaDatas, givenFileName4) {
        metaDatas.findAll { metaData -> metaData.getName().equals(givenFileName4) }.size() == 1
    }
}
