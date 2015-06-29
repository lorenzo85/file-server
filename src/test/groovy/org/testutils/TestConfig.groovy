package org.testutils
import org.fileserver.repository.FileSystemRepository
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@ComponentScan(basePackages = "org.fileserver")
class TestConfig {

    @Bean
    @Primary
    def FileSystemRepository getFileSystemRepository() {
        return Mockito.mock(FileSystemRepository.class)
    }
}
