package com.revature.rideforce.user.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.services.ContactInfoService;
import com.revature.rideforce.user.services.ContactTypeService;
import com.revature.rideforce.user.services.UserService;


@Lazy(true)
@RestController
@RequestMapping("/contact-info")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class ContactInfoController extends CrudController<ContactInfo> {
	@Autowired
	ContactInfoService cService;
	@Autowired
	ContactTypeService ctService;
	@Autowired
	UserService uService;
	@Autowired
	UserRepository uRepo;
	
	@Autowired
	public ContactInfoController(ContactInfoService contactInfoService) {
		super(contactInfoService);
		this.cService=contactInfoService;
	}
	
//	@RequestMapping(value="/getcontact", method=RequestMethod.POST, 
//			consumes=MediaType.APPLICATION_JSON_VALUE,
//			produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Object> getContact(@RequestBody User user){
//		
//		List<ContactInfo> cinfos = cService.findByUserid(user.getId());
//		
//		return new ResponseEntity<Object>(cinfos, HttpStatus.OK);
//	}
	
	@GetMapping(value="/c/{testid}", 
			produces=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<Object> getContact(@PathVariable int testid){//send id as int 
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		List<ContactInfo> cinfos = cService.findByUserid(testid);
		
		//log.info(cinfos.toString());
		return new ResponseEntity<Object>(cinfos, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping(value="/addcinfo",  consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addContact(@RequestBody String cinfo) throws IOException, EntityConflictException, PermissionDeniedException {
		log.info("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		log.info(cinfo);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonnode = mapper.readTree(cinfo);
		int uid = jsonnode.get("id").asInt();
		int tid = jsonnode.get("type").asInt();
		String info = jsonnode.get("info").asText();
		
		
		ContactType type= ctService.findById(tid);
		User user = uService.findById(uid);
		//log.info(user.toString());
		log.info(type.toString());
		log.info(info);
		
		/*public void makeclass() {
			class tempContact() {
			tempContact(){};
			}
		}*/
		
		ContactInfo newCinfo = new ContactInfo(user, type, info);
		//Set<ContactInfo> userContacts = user.getContactInfo();
		//userContacts.add(newCinfo);
		//log.info(userContacts+"");
		//log.info(userContacts.toString());
		//user.setContactInfo(userContacts);
		log.info(newCinfo+"");
		cService.save(newCinfo);
		//uService.save(user);
		
		//cService.add(newCinfo);
		//log.info(newCinfo.toString());
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}
	
}
