package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value = "/phone")
public class PhoneController {

	// 필드
	
	//제어역전/의존관계 주입 - new연산자를 쓰지않고(메모리를 활용) 선언만하면, DispatcherServlet에서 활용할 수 있도록 해줌
	@Autowired
	private PhoneDao phoneDao;
	// 생성자 - 스프링이 접근하므로 기본생성자를 활용

	// 메소드 getter/setter

	/** 메소드 일반**** 메소드 마다 기능 1개씩 -> 기능마다 url 부여 **/

	// 리스트
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		//dao를 통해 리스트를 가져옴
		List<PersonVo> personList = phoneDao.getPersonList();
		//System.out.println(personList.toString());
		
		//model -> date를 보내는 방법 -> 담아 놓으면 된다.
		model.addAttribute("pList", personList);

		return "list";
	}
	
	// 등록폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("writeForm");

		return "writeForm";
	}
	
	// 등록
	@RequestMapping(value ="/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name, @RequestParam("hp") String hp, @RequestParam("company") String company) {
		System.out.println("write");

		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());
		
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	}
	
	// 삭제 -> delete(@RequestMapping 약식 표현) : @PathVariable 활용
	@RequestMapping(value="/delete/{personId}")
	public String delete(@PathVariable("personId") int personId) {
		System.out.println("delete");
		
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
	// 수정폼 -> modifyForm
	@RequestMapping(value="/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("personId") int id) {
		System.out.println("modifyForm");
		
		PersonVo personVo = phoneDao.getPerson(id);
		model.addAttribute("pVo", personVo);
		
		return "updateForm";
	}
	/*

	// 수정 -> modify : @ModelAttribute 활용
	@RequestMapping(value="/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute PersonVo personVo) {
		System.out.println("modify");
		
		phoneDao.personUpdate(personVo);
			
		return "redirect:/phone/list";
	}
	
	//수정 -> modify : @RequestParam 사용 (비교용 원본)
	@RequestMapping(value="/modify2", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify2(@RequestParam("personId") int personId, @RequestParam("name") String name, @RequestParam("hp") String hp, @RequestParam("company") String company) {
		System.out.println("modify");
		
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		phoneDao.personUpdate(personVo);
			
		return "redirect:/phone/list";
	}

	// 삭제 -> delete(@RequestMapping 약식 표현) : @RequestParam 사용 (비교용 원본)
	@RequestMapping(value="/delete")
	public String delete2(@RequestParam("personId") int personId) {
		System.out.println("delete");
		
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
	
	*/
}
