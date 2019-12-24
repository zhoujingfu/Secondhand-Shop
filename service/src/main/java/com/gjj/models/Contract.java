package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "goods")
public class Contract {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", length = 36)
	public String id;

	/**
	 * 姓名
	 */
	@Column(name = "user_id", length = 50)
	public String user_id;

	/**
	 * 姓别
	 */
	@Column(name = "type", length = 50)
	public String type;

	/**
	 * 手机号
	 */
	@Column(name = "goods_name", length = 50)
	public String goods_name;

	/**
	 * 身份证号
	 */
	@Column(name = "spec", length = 50)
	public String spec;

	/**
	 * 住址
	 */
	@Column(name = "price", length = 50)
	public String price;

	/**
	 * 省略 get  set
	 */

}
