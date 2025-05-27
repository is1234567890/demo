package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レコード取得APIの結果格納用DTO.<br/>
 * 
 * @author SI
 * @since 2024/04/15
 * @version 1.0
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRecordsDto {
	/** 社員ID */
	private String employeeId;
	/** 社員氏名 */
	private String employeeName;
	/** 年齢 */
	private int age;
	/** 性別 */
	private int gender;
	/** 郵便番号 */
	private int postNo;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String tel;
	/** 部署ID */
	private String deptId;
	/** 役職ID */
	private String roleId;
	/** AD登録有無 */
	private int adRegistFlg;
	/** 備考 */
	private String note;
	/** 部署名 */
	private String deptName;
	/** 役職名 */
	private String roleName;
	/** 言語ID */
	private String langId;
	/** 言語名 */
	private String langName;
}
