package org.fileserver

import org.apache.tomcat.util.http.fileupload.FileUtils
import org.fileserver.domain.Document
import org.fileserver.domain.DocumentMetadata
import org.fileserver.mediatypes.ApplicationMediaTypes
import org.fileserver.repository.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import static org.fileserver.domain.DocumentMetadata.METADATA_EXT
import static java.io.File.separator

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
        FileUtils.deleteDirectory(dirToBeDeleted)
    }

    def "Should correctly save the document file with the correct content"() {
        given:
        def givenDocumentContent = "A nice document content 1"
        def givenFileName = "my_document_1.txt"
        def metadata = new DocumentMetadata(givenFileName, ApplicationMediaTypes.APPLICATION_OCTET_STREAM)
        def givenDocument = new Document(givenDocumentContent.bytes, metadata)

        when:
        def savedDocument = repository.save(givenDocument)
        def savedDocumentPath = fileFolder.concat(separator).concat(savedDocument.getIdentifier())
        def savedDocumentContent = new File(savedDocumentPath).text

        then:
        savedDocumentContent.equals(givenDocumentContent)
    }

    def "Should correctly save the metadata file"() {
        given:
        def givenDocumentContent = "A nice document content 2"
        def givenFileName = "my_document_2.txt"
        def metadata = new DocumentMetadata(givenFileName, ApplicationMediaTypes.APPLICATION_OCTET_STREAM)
        def givenDocument = new Document(givenDocumentContent.bytes, metadata)

        when:
        def savedDocument = repository.save(givenDocument)
        def savedDocumentMetadataPath = fileFolder.concat(separator).concat(savedDocument.getIdentifier()).concat(METADATA_EXT)
        def savedDocumentMetadata = new File(savedDocumentMetadataPath)

        then:
        savedDocumentMetadata.exists()
    }

    def "Should correctly find all metadata files"() {
        given:
        def givenFileName3 = "my_document_3.txt"
        def givenFileName4 = "my_document_4.txt"
        def metadata3 = new DocumentMetadata(givenFileName3, ApplicationMediaTypes.APPLICATION_OCTET_STREAM)
        def metadata4 = new DocumentMetadata(givenFileName4, ApplicationMediaTypes.APPLICATION_OCTET_STREAM)
        repository.save(new Document("Document 3".bytes, metadata3))
        repository.save(new Document("Document 4".bytes, metadata4))

        when:
        def documents = repository.listAll()

        then:
        documents.size() == 2
        findOne(documents, givenFileName3)
        findOne(documents, givenFileName4)
    }

    def "Should correctly delete the document and metadata"() {
        given:
        def fileName = "my_document_5.txt"
        def metadata = new DocumentMetadata(fileName, ApplicationMediaTypes.APPLICATION_OCTET_STREAM)
        Document document = new Document("Document 5".bytes, metadata)
        def savedDocument = repository.save(document)

        when:
        repository.delete(savedDocument.identifier)

        then:
        fileNotFound(fileName)
        fileNotFound(fileName + METADATA_EXT)
    }

    def boolean findOne(List<Document> documents, givenFileName4) {
        documents.findAll { document -> document.metadata.fileName.equals(givenFileName4) }.size() == 1
    }

    def fileNotFound(fileName) {
        return new File(fileFolder)
                .list({dir, name -> name.equals(fileName) as FilenameFilter})
                .size() == 0;
    }
}
