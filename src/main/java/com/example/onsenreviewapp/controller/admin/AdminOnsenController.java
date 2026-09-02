package com.example.onsenreviewapp.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.form.OnsenForm;
import com.example.onsenreviewapp.service.OnsenService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/onsens")
public class AdminOnsenController {
	
	@Autowired
	private OnsenService onsenService;
	
	@GetMapping("")
	public String showAllOnsens(Model model) {
		
		List<Onsen> onsens = onsenService.getAllOnsens();
		
		model.addAttribute("onsens", onsens);
		
		return "admin/onsens/index";
	}
	
	@GetMapping("/new")
	public String showCreateForm(
			Model model) {
		
		OnsenForm onsenForm = new OnsenForm();
		
		model.addAttribute("onsenForm", onsenForm);
		
		return "admin/onsens/form";
	}
	
	@PostMapping("/new")
	public String createOnsen(
			@Valid OnsenForm onsenForm,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			return "admin/onsens/form";
		}
		
		onsenService.createOnsen(onsenForm);
		
		return "redirect:/admin/onsens";
		
	}
	
	@GetMapping("/{id}/edit")
	public String showEditForm(
			@PathVariable Long id,
			Model model) {
		
		Optional<Onsen> optionalOnsen = onsenService.getOnsenById(id);
		
		if (optionalOnsen.isEmpty()) {
			return "redirect:/admin/onsens";
		}
		
		Onsen onsen = optionalOnsen.get();
		
		OnsenForm onsenForm = new OnsenForm();

	    onsenForm.setName(onsen.getName());
	    onsenForm.setImageUrl(onsen.getImageUrl());
	    onsenForm.setAddress(onsen.getAddress());
	    onsenForm.setPhone(onsen.getPhone());
	    onsenForm.setBusinessHours(onsen.getBusinessHours());
	    onsenForm.setHoliday(onsen.getHoliday());
	    onsenForm.setAdultPrice(onsen.getAdultPrice());
	    onsenForm.setChildPrice(onsen.getChildPrice());
	    onsenForm.setNearestStation(onsen.getNearestStation());
	    onsenForm.setStationTime(onsen.getStationTime());
	    onsenForm.setPrefecture(onsen.getPrefecture());

	    onsenForm.setFamilyBath(onsen.getFamilyBath());
	    onsenForm.setSauna(onsen.getSauna());
	    onsenForm.setOpenAirBath(onsen.getOpenAirBath());
	    onsenForm.setRestaurant(onsen.getRestaurant());
	    onsenForm.setAlcohol(onsen.getAlcohol());
	    onsenForm.setTowelRental(onsen.getTowelRental());
	    onsenForm.setParking(onsen.getParking());

	    onsenForm.setDescription(onsen.getDescription());
	
		model.addAttribute("onsenForm", onsenForm);
		
		model.addAttribute("onsenId", id);
		
		return "admin/onsens/form";
	}
	
	@PostMapping("/{onsenId}/edit")
	public String updateOnsen(
			@PathVariable Long onsenId,
			@Valid OnsenForm onsenForm,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			
			model.addAttribute("onsenId", onsenId);
			
			return "admin/onsens/form";
		}
		
		boolean updated = onsenService.updateOnsen(onsenId, onsenForm);
		
		if (updated == false) {
			return "redirect:/admin/onsens";
		}
		
		return "redirect:/admin/onsens";
		
	}
	
	@PostMapping("/{onsenId}/delete")
	public String deleteOnsen(
			@PathVariable Long onsenId) {
		
		boolean deleted = onsenService.deleteOnsen(onsenId);
		
		if (deleted) {
			return "redirect:/admin/onsens";
		}
		
		return "redirect:/admin/onsens?deleteError";
		
	}
}
