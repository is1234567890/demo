package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GetRecords;
import com.example.demo.mapper.GetRecordsMapper;

/**
 * レコード取得API用Repository.<br/>
 * 
 * @author SI
 * @since 2024/04/15
 * @version 1.0
 */
@Repository
public class GetRecordsRepository {

	@Autowired
	private GetRecordsMapper mapper;
	
	/**
	 * レコード取得.<br/>
	 * 
	 * @param employee
	 * @return 取得レコード格納DTO
	 */
	public List<GetRecords> getRecords(String employee) {
		return mapper.getRecords(employee);
	}
}
