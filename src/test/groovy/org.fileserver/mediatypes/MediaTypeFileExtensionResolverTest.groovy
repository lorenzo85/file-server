package org.fileserver.mediatypes

import org.springframework.http.MediaType
import spock.lang.Specification

class MediaTypeFileExtensionResolverTest extends Specification {

    def "Should return correct media type for powerpoint"() {
        given:
        def fileName = "myPresentation.ppt"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = MediaTypeFileExtensionResolver.resolve(givenMediaType, fileName)

        then:
        value == ApplicationMediaTypes.MICROSOFT_POWERPOINT
    }

    def "Should return given media type for not found media type"() {
        given:
        def fileName = "myPresentation.anExtension"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = MediaTypeFileExtensionResolver.resolve(givenMediaType, fileName)

        then:
        value == MediaType.APPLICATION_OCTET_STREAM
    }

    def "Should return given media type for file without extension"() {
        given:
        def fileName = "aFileWithoutExtension"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = MediaTypeFileExtensionResolver.resolve(givenMediaType, fileName)

        then:
        value == MediaType.APPLICATION_OCTET_STREAM
    }
}
