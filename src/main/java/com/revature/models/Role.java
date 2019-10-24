package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@SequenceGenerator(name = "role_id_maker", sequenceName = "role_id_maker", allocationSize = 1)
	@GeneratedValue(generator = "role_id_maker", strategy = GenerationType.SEQUENCE)
	@Column(name = "RID")
	private Integer id;
	@Column(name = "rname")
	private String rname;
}
