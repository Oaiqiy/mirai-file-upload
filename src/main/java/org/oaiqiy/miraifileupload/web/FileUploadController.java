package org.oaiqiy.miraifileupload.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oaiqiy.miraifileupload.storage.StorageData;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@AllArgsConstructor
public class FileUploadController {

	private final StorageService storageService;
	private final StorageData storageData;


	/**
	 * 主界面
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String listUploadedFiles(Model model)  {

		model.addAttribute("files",storageData.getData());
		model.addAttribute("texts",storageData.getText());
		return "uploadForm";
	}

	/**
	 * 上传文件接口
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
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

	/**
	 * 删除文件接口
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/files/{id}")
	public String deleteFile(@PathVariable String id, RedirectAttributes redirectAttributes){
		storageService.delete('/'+id);
		redirectAttributes.addFlashAttribute("message",
				"You successfully delete " + id + "!");

		log.info("delete " + id);
		return "redirect:/";
	}

	/**
	 * 刷新接口
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/refresh")
	public String refresh(RedirectAttributes redirectAttributes){
		storageService.loadAll();
		redirectAttributes.addFlashAttribute("message", "refresh successfully!");
		return "redirect:/";
	}






}
