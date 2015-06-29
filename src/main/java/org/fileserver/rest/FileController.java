package org.fileserver.rest;

import org.fileserver.dto.DocumentDto;
import org.fileserver.mediatypes.MediaTypeFileExtensionResolver;
import org.fileserver.service.DocumentService;
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

	@Autowired
	private DocumentService service;

	@RequestMapping(value = "upload", method = POST)
	@ResponseBody
	public DocumentDto handleFileUpload(@RequestParam(value = "file_data", required = true) MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("The file is empty.");
		}
		String fileName = file.getOriginalFilename();
		MediaType mediaType = MediaTypeFileExtensionResolver.resolve(file.getContentType(), fileName);
		DocumentDto dto = new DocumentDto(file.getBytes(), fileName, mediaType);
		return service.save(dto);
	}

	@RequestMapping(value = "all", method = GET)
	@ResponseBody
	public List<DocumentDto> findAll() throws IOException {
		return service.listAll();
	}

	@RequestMapping(value = "load/{fileId}", method = GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> loadFile(@PathVariable("fileId") String fileId) throws IOException {
		DocumentDto documentDto = service.load(fileId);
		return buildResponseEntity(documentDto);
	}

	@RequestMapping(value = "delete/{fileId}", method = GET)
	@ResponseBody
	public void deleteFile(@PathVariable("fileId") String fileId) throws IOException {
		service.delete(fileId);
	}

	private ResponseEntity<InputStreamResource> buildResponseEntity(DocumentDto dto) {
		return ResponseEntity
				.ok()
				.headers(buildHeaders(dto))
				.body(new InputStreamResource(dto.asInputStream()));
	}

	private HttpHeaders buildHeaders(DocumentDto dto) {
		String contentDisposition = format("attachment;filename=\"%s\"", dto.getFileName());
		String mediaType = dto.getMediaType().toString();
		String length = Long.toString(dto.length());
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_DISPOSITION, contentDisposition);
		headers.add(CONTENT_TYPE, mediaType);
		headers.add(CONTENT_LENGTH, length);
		return headers;
	}
}