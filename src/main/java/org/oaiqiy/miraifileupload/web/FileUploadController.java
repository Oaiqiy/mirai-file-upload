package org.oaiqiy.miraifileupload.web;


import lombok.extern.slf4j.Slf4j;
import org.oaiqiy.miraifileupload.storage.StorageFileNotFoundException;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model)  {

		model.addAttribute("files",storageService.loadAll());


		return "uploadForm";
	}


	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try{
			storageService.store(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			log.info("upload " + file.getOriginalFilename() );
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
