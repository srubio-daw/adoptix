package tk.srubio.adoptix.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.model.Province;
import tk.srubio.adoptix.model.ProvinceRepository;

@Controller
public class ProvinceController {
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@RequestMapping(value = "/provinces", method = RequestMethod.GET)
	public @ResponseBody List<Province> getProvinces() {
		return provinceRepository.findAllByOrderByNameAsc();
	}
}
