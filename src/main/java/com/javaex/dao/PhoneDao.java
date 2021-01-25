package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {

	@Autowired
	private SqlSession sqlSession;

	// 전체리스트 가져오기
	public List<PersonVo> getPersonList() {
		System.out.println("dao : getPersonList");

		List<PersonVo> personList = sqlSession.selectList("phonebook.selectlist2");

		System.out.println(personList);
		return personList;
	}

	// 전화번호 저장
	public void personInsert(PersonVo personVo) {
		System.out.println(personVo);
		sqlSession.insert("phonebook.insert", personVo);
	}

	//전화번호 삭제
	public void personDelete(int personId) {
		System.out.println("dao : personDelete(" +personId+ ")");
		int count = sqlSession.delete("phonebook.delete", personId);
		System.out.println(count);
	}
	
	// 한명 데이터 가져오기
	public PersonVo getPerson(int personId) {
		System.out.println("dao : getPerson(" +personId+ ")");
		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", personId);
		System.out.println(personVo.toString());
		return personVo;
	}
	
	// 한명 데이터 가져오기2 Map
	public Map<String, Object> getPerson2(int personId) {
		System.out.println("dao : getPerson(" +personId+ ")");
		Map<String, Object> personMap = sqlSession.selectOne("phonebook.selectOne2", personId);
		System.out.println(personMap.toString());
		
		String name = (String)personMap.get("NAME");
		System.out.println(name);
		
		int id = Integer.parseInt(String.valueOf(personMap.get("PERSONID")));
		System.out.println(id);
		return personMap;
	}
	
	//전화번호 수정
	public int personUpdate(PersonVo personVo) {
		System.out.println("dao : personUpdate(" +personVo+ ")");
		
		int count = sqlSession.update("phonebook.update", personVo);
		System.out.println(count);
		
		return count;	
	}
	
	//전화번호 수정2 Map
	public int personUpdate2(int personId, String name, String hp, String company) {
		System.out.println("dao : personUpdate(" +personId+ ", " +name+ ", " +hp+ ", " +company+")");
		
		//vo 대신 map 사용
		//PersonVo personVo = new PersonVo(personId, name, hp, company);
		Map<String, Object> personMap = new HashMap<String, Object>();
		personMap.put("personId", personId);
		personMap.put("name", name);
		personMap.put("hp", hp);
		personMap.put("company", company);
		
		System.out.println(personMap.toString());
		
		int count = sqlSession.update("phonebook.update2", personMap);
		System.out.println(count);
		
		return 0;	
	}
}
