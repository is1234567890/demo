package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.GetRecords;

/**
 * レコード取得API用Mapper.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/15
 * @version 1.0
 */
@Mapper
public interface GetRecordsMapper {

	/**
	 * レコード取得.<br/>
	 * 
	 * @param employee
	 * @return 取得レコード格納DTO
	 */
	public List<GetRecords> getRecords(String employee);
}
