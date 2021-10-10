package org.oaiqiy.miraifileupload.web;


import lombok.extern.slf4j.Slf4j;
import org.oaiqiy.miraifileupload.storage.StorageFileNotFoundException;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		String root = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"serveFile","").scheme("https").toUriString();

//
//		model.addAttribute("files", storageService.loadAll().map(
//						path -> root+path.getFileName().toString())
//				.collect(Collectors.toList()));
//		model.addAttribute("root", root);

//		model.addAttribute("files", storageService.loadAll().collect(Collectors.toList()));
//		model.addAttribute("names",storageService.loadAllNames().collect(Collectors.toList()));
		model.addAttribute("files",storageService.loadAllURL().collect(Collectors.toList()));


		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try{
			storageService.store(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			log.info("uploaded " + file.getOriginalFilename() );
		}catch (Exception e){
			e.printStackTrace();
		}


		return "redirect:/";
	}

	@PostMapping("/files/{filename:.+}")
	public String deleteFile(@PathVariable String filename, RedirectAttributes redirectAttributes){
		storageService.delete(filename);
		redirectAttributes.addFlashAttribute("message",
				"You successfully delete " + filename + "!");

		log.info("delete " + filename);
		return "redirect:/";
	}





	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.badRequest().build();
	}



}
