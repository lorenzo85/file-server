package org.fileserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/")
public class FileController {

	@Autowired
	private FileService service;
	@Autowired
	private FileRepository repository;

	@RequestMapping(value = "/upload", method = POST)
	@ResponseBody
	public DocumentMetadata handleFileUpload(
			@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
		Document document = new Document(file.getBytes(), file.getOriginalFilename());
		service.save(document);
		return document.getMetadata();
	}

	@RequestMapping(value = "/all", method = GET)
	@ResponseBody
	public List<DocumentMetadata> findAll() throws IOException {
		return service.findAll();
	}

}