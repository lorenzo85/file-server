package org.fileserver

import org.fileserver.mediatypes.ApplicationMediaTypes
import org.fileserver.mediatypes.MediaTypeFileExtensionResolver
import org.springframework.http.MediaType
import spock.lang.Specification


class MediaTypeFileExtensionResolverTest extends Specification {

    def mapper = new MediaTypeFileExtensionResolver()

    def "Should return correct media type for powerpoint"() {
        given:
        def fileName = "myPresentation.ppt"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = mapper.resolveMediaType(givenMediaType, fileName)

        then:
        value == ApplicationMediaTypes.MICROSOFT_POWERPOINT
    }

    def "Should return given media type for not found media type"() {
        given:
        def fileName = "myPresentation.anExtension"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = mapper.resolveMediaType(givenMediaType, fileName)

        then:
        value == MediaType.APPLICATION_OCTET_STREAM
    }

    def "Should return given media type for file without extension"() {
        given:
        def fileName = "aFileWithoutExtension"
        def givenMediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        when:
        def value = mapper.resolveMediaType(givenMediaType, fileName)

        then:
        value == MediaType.APPLICATION_OCTET_STREAM
    }
}
