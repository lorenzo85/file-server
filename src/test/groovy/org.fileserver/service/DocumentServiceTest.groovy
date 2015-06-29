package org.fileserver.service
import org.fileserver.domain.File
import org.fileserver.dto.DocumentDto
import org.fileserver.repository.FileDatabaseRepository
import org.fileserver.repository.FileSystemRepository
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.testutils.TestConfig
import spock.lang.Specification

import static org.mockito.Matchers.any
import static org.mockito.Matchers.argThat
import static org.mockito.Mockito.doThrow
import static org.mockito.Mockito.verify

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [TestConfig])
@TestPropertySource("classpath:test.properties")
class DocumentServiceTest extends Specification {

    @Autowired DocumentService documentService
    @Autowired FileDatabaseRepository databaseRepository
    @Autowired FileSystemRepository fileSystemRepository

    def documentDto

    void setup() {
        documentDto = createDocumentDto("A document", "document.txt")
    }

    void cleanup() {
        databaseRepository.deleteAll()
    }

    def "Should save document in database and in file system" () {
        when:
        def persistedDocument = documentService.save(documentDto)

        and:
        def foundDocument = databaseRepository.findOne(persistedDocument.id)

        then:
        !foundDocument.deleted
        foundDocument.id == persistedDocument.id
        foundDocument.metadata.fileName == documentDto.fileName
        foundDocument.metadata.mediaType == documentDto.mediaType

        and:
        verify(fileSystemRepository).save(any()) || true
    }

    def "Should not save document in database when file system throws an exception" () {
        given:
        def documentContent = "A document content which should throw IOException"
        def documentDto = createDocumentDto(documentContent, "aDocument.txt")
        doThrow(new IOException())
                .when(fileSystemRepository)
                .save(argThat(hasContent(documentContent.bytes)))

        when:
        documentService.save(documentDto)

        then:
        thrown(IOException)

        when:
        def files = databaseRepository.findAll()

        then:
        files.isEmpty()
    }

    def "Should list all documents" () {
        given:
        def document1 = createDocumentDto("A document 1", "document1.txt")
        def document2 = createDocumentDto("A document 2", "document2.txt")
        documentService.save(document1)
        documentService.save(document2)

        when:
        def documents = documentService.listAll()

        then:
        documents.size() == 2
    }

    def "Should load document" () {
        given:
        def documentContent = "A document to be loaded"
        def document = createDocumentDto(documentContent,  "document.txt")
        def persistedDocument = documentService.save(document)

        when:
        documentService.load(persistedDocument.id)

        then:
        verify(fileSystemRepository).load(argThat(hasId(persistedDocument.id)))
    }

    def "Should delete document" () {
        given:
        def document = createDocumentDto("A content", "document.txt")
        def persistedDocument = documentService.save(document)

        when:
        documentService.delete(persistedDocument.id)

        then:
        documentService.listAll().size() == 0
    }

    def createDocumentDto(content, fileName) {
        return new DocumentDto(content.bytes, fileName, MediaType.TEXT_PLAIN);
    }

    private static Matcher<File> hasContent(final byte[] bytes) {
        return new TypeSafeMatcher<File>() {
            @Override
            protected boolean matchesSafely(File item) {
                return item.bytes == bytes
            }

            @Override
            void describeTo(Description description) {
                description.appendText("file bytes should equals to ").appendValue(bytes)
            }
        }
    }

    private static Matcher<File> hasId(final String id) {
        return new TypeSafeMatcher<File>() {
            @Override
            protected boolean matchesSafely(File item) {
                return item.id == id;
            }

            @Override
            void describeTo(Description description) {
                description.appendText("file id should equals to ").appendValue(id)
            }
        }
    }
}
