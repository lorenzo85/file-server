package org.fileserver.rest;

import org.apache.log4j.Logger;
import org.fileserver.domain.Document;
import org.fileserver.domain.DocumentMetadata;
import org.fileserver.repository.FileRepository;
import org.fileserver.repository.FileRepositoryImpl;
import org.fileserver.mediatypes.MediaTypeFileExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/")
public class FileController {

	private static final Logger LOGGER = Logger.getLogger(FileRepositoryImpl.class);

	@Autowired
	private FileRepository repository;

	@RequestMapping(value = "upload", method = POST)
	@ResponseBody
	public Document handleFileUpload(@RequestParam(value = "file_data", required = true) MultipartFile file)
			throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("The file is empty.");
		}

		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		MediaType mediaType = new MediaTypeFileExtensionResolver().resolveMediaType(contentType, fileName);
		DocumentMetadata metadata = new DocumentMetadata(fileName, mediaType);
		Document document = new Document(file.getBytes(), metadata);
		return repository.save(document);
	}

	@RequestMapping(value = "all", method = GET)
	@ResponseBody
	public List<Document> findAll() throws IOException {
		return repository.listAll();
	}

	@RequestMapping(value = "load/{uuid}", method = GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> loadFile(@PathVariable("uuid") String uuid) throws IOException {
		Document document = repository.load(uuid);
		return buildResponseEntity(document);
	}

	@RequestMapping(value = "delete/{uuid}", method = GET)
	@ResponseBody
	public void deleteFile(@PathVariable("uuid") String uuid) throws IOException {
		repository.delete(uuid);
	}

	private ResponseEntity<InputStreamResource> buildResponseEntity(Document document) {
		return ResponseEntity
				.ok()
				.headers(buildHeaders(document))
				.body(new InputStreamResource(document.asInputStream()));
	}

	private HttpHeaders buildHeaders(Document document) {
		String contentDisposition = format("attachment;filename=\"%s\"", document.getMetadata().getFileName());
		String mediaType = document.getMetadata().getMediaType().toString();
		String length = Long.toString(document.length());
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_DISPOSITION, contentDisposition);
		headers.add(CONTENT_TYPE, mediaType);
		headers.add(CONTENT_LENGTH, length);
		return headers;
	}
}