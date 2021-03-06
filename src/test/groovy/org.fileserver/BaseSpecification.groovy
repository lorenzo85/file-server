package org.fileserver

import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification


@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [Application.class])
@TestPropertySource("classpath:test.properties")
class BaseSpecification extends Specification {
}
